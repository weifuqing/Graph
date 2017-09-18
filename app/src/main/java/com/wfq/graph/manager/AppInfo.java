package com.wfq.graph.manager;

import java.util.ArrayList;

public class AppInfo {
	//1期字段
	private String des;//app的描述信息
	private String downloadUrl;//app的下载地址的url后缀
	private String iconUrl;//app的图标的url后缀
	private int id;//app的id
	private String name;//app的名称
	private String packageName;//app的包名
	private long size;//apk的体积大小
	private float stars;//app的星级
	
	//二期项目的字段
	private String author;//app的作者
	private String date;//上传日期
	private String downloadNum;//下载数量
	private String version;//app的版本
	private ArrayList<String> screen;//app截图的url集合
	private ArrayList<SafeInfo> safe;//安全信息的集合
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDownloadNum() {
		return downloadNum;
	}
	public void setDownloadNum(String downloadNum) {
		this.downloadNum = downloadNum;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public ArrayList<String> getScreen() {
		return screen;
	}
	public void setScreen(ArrayList<String> screen) {
		this.screen = screen;
	}
	public ArrayList<SafeInfo> getSafe() {
		return safe;
	}
	public void setSafe(ArrayList<SafeInfo> safe) {
		this.safe = safe;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public float getStars() {
		return stars;
	}
	public void setStars(float stars) {
		this.stars = stars;
	}
	
	
}
