package com.wfq.graph.manager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class HttpHelper {
	private static String tag = HttpHelper.class.getSimpleName();
	/**
	 * 执行get请求
	 * @param url
	 * @return
	 */
	public static String get(String url){
		String result = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
//		LogUtil.e(tag, "请求的url: "+url);
		try {
			//获取服务器响应的数据封装体
			HttpResponse httpResponse = httpClient.execute(get);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				//获取响应体的数据封装体
				HttpEntity entity = httpResponse.getEntity();
				//获取服务器的输入流对象
				InputStream is = entity.getContent();
				ByteArrayOutputStream baso = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];//1k的缓冲区
				int len = -1;
				while((len=is.read(buffer))!=-1){
					baso.write(buffer, 0, len);
				}
				//关闭流和链接
				is.close();
				httpClient.getConnectionManager().closeExpiredConnections();//关闭闲置的链接
				
				result = new String(baso.toByteArray(),"UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		LogUtil.e(tag, "响应的数据 result: "+result);
		return result;
	}
	
	/**
	 * 下载文件，返回流对象
	 * 
	 * @param url
	 * @return
	 */
	public static HttpResult download(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		boolean retry = true;
		while (retry) {
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				if(httpResponse!=null){
					return new HttpResult(httpClient, httpGet, httpResponse);
				}
			} catch (Exception e) {
				retry = false;
				e.printStackTrace();
//				LogUtil.e(tag, "download: "+e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Http返回结果的进一步封装
	 * @author Administrator
	 *
	 */
	public static class HttpResult {
		private HttpClient httpClient;
		private HttpGet httpGet;
		private HttpResponse httpResponse;
		private InputStream inputStream;

		public HttpResult(HttpClient httpClient, HttpGet httpGet,
				HttpResponse httpResponse) {
			super();
			this.httpClient = httpClient;
			this.httpGet = httpGet;
			this.httpResponse = httpResponse;
		}

		/**
		 * 获取状态码
		 * @return
		 */
		public int getStatusCode() {
			StatusLine status = httpResponse.getStatusLine();
			return status.getStatusCode();
		}

		/**
		 * 获取输入流
		 * @return
		 */
		public InputStream getInputStream(){
			if(inputStream==null && getStatusCode()<300){
				HttpEntity entity = httpResponse.getEntity();
				try {
					inputStream =  entity.getContent();
				} catch (Exception e) {
					e.printStackTrace();
//					LogUtil.e(this, "getInputStream: "+e.getMessage());
				}
			}
			return inputStream;
		}

		/**
		 * 关闭链接和流对象
		 */
		public void close() {
			if (httpGet != null) {
				httpGet.abort();
			}
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
//					LogUtil.e(this, "close: "+e.getMessage());
				}
			}
			//关闭链接
			if (httpClient != null) {
				httpClient.getConnectionManager().closeExpiredConnections();
			}
		}
	}

}
