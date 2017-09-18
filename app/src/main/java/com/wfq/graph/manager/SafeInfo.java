package com.wfq.graph.manager;

public class SafeInfo {
	private String safeDes;//安全描述的文字
	private int safeDesColor;//安全描述的文字的颜色
	private String safeDesUrl;//安全描述的对应的图标的url后缀
	private String safeUrl;//安全信息对应的图片
	public String getSafeDes() {
		return safeDes;
	}
	public void setSafeDes(String safeDes) {
		this.safeDes = safeDes;
	}
	public int getSafeDesColor() {
		return safeDesColor;
	}
	public void setSafeDesColor(int safeDesColor) {
		this.safeDesColor = safeDesColor;
	}
	public String getSafeDesUrl() {
		return safeDesUrl;
	}
	public void setSafeDesUrl(String safeDesUrl) {
		this.safeDesUrl = safeDesUrl;
	}
	public String getSafeUrl() {
		return safeUrl;
	}
	public void setSafeUrl(String safeUrl) {
		this.safeUrl = safeUrl;
	}
	
	
}
