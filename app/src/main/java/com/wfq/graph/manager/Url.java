package com.wfq.graph.manager;

public interface Url {
	//服务器的主机地址
	String SERVER_HOST = "http://127.0.0.1:8090/";
	//图片的url前缀，显示的时候需要拼接后缀 http://127.0.0.1:8090/image?name=
	String IMAGE_PREFIX = SERVER_HOST +"image?name=";
	//首页的接口地址
	String Home = SERVER_HOST + "home?index=";
	//App页的接口地址
	String App = SERVER_HOST + "app?index=";
	//Game页的接口地址
	String Game = SERVER_HOST + "game?index=";
	//Subject页的接口地址
	String Subject = SERVER_HOST + "subject?index=";
	//Recommend页的接口地址
	String Recommend = SERVER_HOST + "recommend?index=0";
	//Category页的接口地址
	String Category = SERVER_HOST + "category?index=0";
	//Hot页的接口地址
	String Hot = SERVER_HOST + "hot?index=0";
	//Detail页的接口地址
	String Detail = SERVER_HOST + "detail?packageName=%1$s";//相当于使用占位符的方式来占位，一会儿要替换掉它
	//apk下载的接口地址
	String Download = SERVER_HOST + "download?name=%1$s";
	//apk断点下载的接口地址
	String Break_Download = SERVER_HOST + "download?name=%1$s&range=%2$d";
}