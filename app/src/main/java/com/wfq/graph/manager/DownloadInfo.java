package com.wfq.graph.manager;


/**
 * 用来封装下载任务对应的相关数据
 * @author Administrator
 *
 */
public class DownloadInfo {
	private int id;//下载任务的唯一标识
	private int state;//任务的下载状态
	private long currentLength;//已经下载的长度
	private long size;//总长度
	private String downloadUrl;//下载地址
	private String path;//下载的apk文件的保存的绝对路径
	
	public static DownloadInfo create(AppInfo appInfo){
		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.setId(appInfo.getId());
		downloadInfo.setDownloadUrl(appInfo.getDownloadUrl());
		downloadInfo.setSize(appInfo.getSize());
		downloadInfo.setState(DownloadManager.STATE_NONE);//设置未下载的状态
		downloadInfo.setCurrentLength(0);
		
		//设置下载文件保存的绝对路径：  /mnt/sdcard/包名/download/有缘网.apk
		downloadInfo.setPath(DownloadManager.DOWNLOAD_DIR+"/"+appInfo.getName()+".apk");
		
		return downloadInfo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public long getCurrentLength() {
		return currentLength;
	}
	public void setCurrentLength(long currentLength) {
		this.currentLength = currentLength;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
