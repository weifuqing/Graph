package com.wfq.graph.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;


import android.content.Intent;
import android.graphics.Region.Op;
import android.net.Uri;
import android.os.Environment;
import android.util.SparseArray;
import android.webkit.WebView.FindListener;

import com.wfq.graph.app.MyApplication;
import com.wfq.graph.utils.CommonUtil;

/**
 * 用来管理和维护下载任务的逻辑
 * @author Administrator
 *
 */
public class DownloadManager {
	//定义文件的下载目录: /mnt/sdcard/包名/download
	public static String DOWNLOAD_DIR = Environment.getExternalStorageDirectory()+"/"
			+ MyApplication.getContext().getPackageName()+"/download";
	
	//定义6种下载状态常量
	public static final int STATE_NONE = 0;//未下载的状态
	public static final int STATE_WAITING = 1;//等待中的状态，就是任务已经创建并且添加，但是并木有执行run方法
	public static final int STATE_DOWNLOADING = 2;//下载中的状态
	public static final int STATE_PAUSE = 3;//暂停的状态
	public static final int STATE_FINISH = 4;//下载完成的状态
	public static final int STATE_ERROR = 5;//下载出错的状态
	
	//用于存放所有的下载监听器对象,注意：这里只是内存中存储数据，并木有进行持久化保存
	private ArrayList<DownloadObserver> obersverList = new ArrayList<DownloadObserver>();
	//用于存放每个任务的数据信息
	private HashMap<Integer, DownloadInfo> downloadInfoMap = new HashMap<Integer, DownloadInfo>();
	//用于存放每个DownloadTask对象，以便于暂停的时候可以找到对应的task，然后从线程池中移除，及时为缓冲队列的任务腾出系统资源
	private HashMap<Integer, DownloadTask> downloadTaskMap = new HashMap<Integer, DownloadTask>();
	
	//如果你的map的key是整数，则使用sparseArray会有更好的执行效率
	private SparseArray<DownloadInfo> sparseArray;
	
	private static DownloadManager mInstance = new DownloadManager();
	private DownloadManager(){
		//初始化DOWNLOAD_DIR
		File file = new File(DOWNLOAD_DIR);
		if(!file.exists()){
			file.mkdirs();//创建多层目录
		}
	}
	
	public static DownloadManager getInstance(){
		return mInstance;
	}
	
	public DownloadInfo getDownloadInfo(AppInfo appInfo){
		return downloadInfoMap.get(appInfo.getId());
	}
	
	/**
	 * 下载的方法
	 */
	public void download(AppInfo appInfo){
		//1.获取DownloadInfo对象，downloadInfo里面存放了下载的state和长度等等
		DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.getId());
		if(downloadInfo==null){
			downloadInfo = DownloadInfo.create(appInfo);//创建DownloadInfo
			downloadInfoMap.put(downloadInfo.getId(), downloadInfo);//将downloadInfo维护起来
		}
		//2.判断当前的state是否能够进行下载，只有3种state才能下载：none，pause,error
		if(downloadInfo.getState()==STATE_NONE || downloadInfo.getState()==STATE_PAUSE
			|| downloadInfo.getState()==STATE_ERROR){
			//可以进行下载了
			DownloadTask downloadTask = new DownloadTask(downloadInfo);
			downloadTaskMap.put(downloadInfo.getId(), downloadTask);//将downloadInfo存放起来
			
			//创建任务需要将任务的state设置等待中
			downloadInfo.setState(STATE_WAITING);//更改等待中的状态
			//通知监听器方法回调
			notifyDownloadStateChange(downloadInfo);
			
			//将下载任务交给线程池管理
			ThreadPoolManager.getInstance().execute(downloadTask);
		}
	}
	/**
	 * 通知所有的监听器下载状态改变了
	 * @param downloadInfo
	 */
	private void notifyDownloadStateChange(final DownloadInfo downloadInfo){
		CommonUtil.runOnUIThread(new Runnable() {
			@Override
			public void run() {
				for (DownloadObserver observer : obersverList) {
					observer.onDownloadStateChange(downloadInfo);
				}
			}
		});
	}
	/**
	 * 通知所有的监听器下载进度改变了
	 * @param downloadInfo
	 */
	private void notifyDownloadProgessChange(final DownloadInfo downloadInfo){
		CommonUtil.runOnUIThread(new Runnable() {
			@Override
			public void run() {
				for (DownloadObserver observer : obersverList) {
					observer.onDownloadProgressChange(downloadInfo);
				}
			}
		});
	}
	
	/**
	 * 下载任务的类，
	 * @author Administrator
	 *
	 */
	class DownloadTask implements Runnable{
		private DownloadInfo downloadInfo;
		public DownloadTask(DownloadInfo downloadInfo) {
			super();
			this.downloadInfo = downloadInfo;
		}
		@Override
		public void run() {
			//3.将state更改为下载中的状态
			downloadInfo.setState(STATE_DOWNLOADING);
			//通知监听器下载状态改变
			notifyDownloadStateChange(downloadInfo);
			
			//4.开始下载操作,2种情况：a.从头下载       b.断点下载
			HttpHelper.HttpResult httpResult = null;
			File file = new File(downloadInfo.getPath());
			if(!file.exists() || file.length()!=downloadInfo.getCurrentLength()){
				//从头下载的情况
				file.delete();//删除错误文件
				downloadInfo.setCurrentLength(0);//重置已经下载的长度
				
				String url = String.format(Url.Download, downloadInfo.getDownloadUrl());
				httpResult = HttpHelper.download(url);
			}else {
				//断点下载的情况
				String url = String.format(Url.Break_Download,downloadInfo.getDownloadUrl(),downloadInfo.getCurrentLength());
				httpResult = HttpHelper.download(url);
			}
			
			//5.获取流对象，进行文件读写
			if(httpResult!=null && httpResult.getInputStream()!=null){
				//说明获取文件数据成功，可以进行文件读写
				InputStream is = httpResult.getInputStream();
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(file,true);//使用追加的方式
					byte[] buffer = new byte[1024*10];//10k的缓冲区
					int len = -1;
					while((len=is.read(buffer))!=-1 && downloadInfo.getState()==STATE_DOWNLOADING){
						fos.write(buffer, 0, len);
						
						//更新currentLength
						downloadInfo.setCurrentLength(downloadInfo.getCurrentLength()+len);
						//需要通知监听器下载进度更新
						notifyDownloadProgessChange(downloadInfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
					//如果出异常，按照下载出错的情况处理
					file.delete();//删除文件
					downloadInfo.setCurrentLength(0);//重置已经下载的长度
					downloadInfo.setState(STATE_ERROR);//将state更改为失败
					notifyDownloadStateChange(downloadInfo);//通知监听器回调
				} finally{
					//关闭流和链接的方法
					httpResult.close();
					try {
						if(fos!=null)fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				//6.while循环结束了，会走到这里,3种情况：a.下载完成   b.暂停   c.出错
				if(file.length()==downloadInfo.getSize() && downloadInfo.getState()==STATE_DOWNLOADING){
					//下载完成的情况
					downloadInfo.setState(STATE_FINISH);//将state更改为完成
					notifyDownloadStateChange(downloadInfo);//通知监听器回调
				}else if (downloadInfo.getState()==STATE_PAUSE) {
					notifyDownloadStateChange(downloadInfo);
				}else if (file.length()!=downloadInfo.getCurrentLength()) {
					//说明下载失败
					file.delete();//删除文件
					downloadInfo.setCurrentLength(0);//重置已经下载的长度
					downloadInfo.setState(STATE_ERROR);//将state更改为失败
					notifyDownloadStateChange(downloadInfo);//通知监听器回调
				}
			}else {
				//说明下载失败
				file.delete();//删除文件
				downloadInfo.setCurrentLength(0);//重置已经下载的长度
				downloadInfo.setState(STATE_ERROR);//将state更改为失败
				notifyDownloadStateChange(downloadInfo);//通知监听器回调
			}
			
			//当run方法结束后，需要将downloadTask从downloadTaskMap中移除
			downloadTaskMap.remove(downloadInfo.getId());
		}
		
	}
	
	/**
	 * 暂停下载任务的方法
	 */
	public void pause(AppInfo appInfo){
		DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.getId());
		if(downloadInfo!=null){
			//将downloadInfo的state设置为pause
			downloadInfo.setState(STATE_PAUSE);
			notifyDownloadStateChange(downloadInfo);
			
			//取出对应的downloadTask对象，及时从线程池中移除
			DownloadTask downloadTask = downloadTaskMap.get(downloadInfo.getId());
			ThreadPoolManager.getInstance().remove(downloadTask);
		}
	}
	/**
	 * 安装apk的方法
	 */
	public void installApk(AppInfo appInfo){
		DownloadInfo downloadInfo = downloadInfoMap.get(appInfo.getId());
		if(downloadInfo!=null){
			 /*<action android:name="android.intent.action.VIEW" />
             <category android:name="android.intent.category.DEFAULT" />
             <data android:scheme="content" />
             <data android:scheme="file" />
             <data android:mimeType="application/vnd.android.package-archive" />*/
			//执行安装操作
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.parse("file://"+downloadInfo.getPath()), "application/vnd.android.package-archive");
			MyApplication.getContext().startActivity(intent);
		}
	}
	
	/**
	 * 注册添加下载监听器对象
	 * @param observer
	 */
	public void registerDownloadObserver(DownloadObserver observer){
		if(!obersverList.contains(observer)){
			obersverList.add(observer);
		}
	}
	
	/**
	 * 取消注册或者移除监听器对象
	 * @param observer
	 */
	public void unregisterDownloadObserver(DownloadObserver observer){
		if(obersverList.contains(observer)){
			obersverList.remove(observer);
		}
	}
	
	
	/**
	 * 下载的监听器
	 * @author Administrator
	 *
	 */
	public interface DownloadObserver{
		/**
		 * 当前下载状态改变的回调
		 */
		void onDownloadStateChange(DownloadInfo downloadInfo);
		/**
		 * 当下载进度更新的回调
		 */
		void onDownloadProgressChange(DownloadInfo downloadInfo);
	}
	
	
}
