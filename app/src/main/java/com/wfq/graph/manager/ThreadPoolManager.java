package com.wfq.graph.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 对全局的下载线程进行管理和维护
 * @author Administrator
 *
 */
public class ThreadPoolManager {
	private int corePoolSize;//核心线程池的数量，能够同时执行的线程数量
	private int maximumPoolSize;//最大线程池的数量
	private int keepAliveTime = 1;//存活时间
	private TimeUnit unit = TimeUnit.HOURS;//存活时间的时间单位
	private static ThreadPoolManager mInstance = new ThreadPoolManager();
	private ThreadPoolExecutor executor;
	
	private ThreadPoolManager(){
		//核心线程池的数量: 当前设备可用处理器的核数*2 + 1,这样的算法能够让cpu的效率得到最大程度的执行
		corePoolSize = Runtime.getRuntime().availableProcessors()*2 + 1;
		maximumPoolSize = corePoolSize;//给最大线程池随便赋值
		executor = new ThreadPoolExecutor(
				corePoolSize, //3
				maximumPoolSize,//5，当缓冲队列满的时候，就会放入最大线程池，但是最大线程池是包含corePoolSize
				keepAliveTime,//表示最大线程池中的等待任务的存活时间
				unit, 
				new LinkedBlockingQueue<Runnable>(),//缓冲队列,超过corePoolSize的任务会放入缓冲队列等着
				Executors.defaultThreadFactory(), //创建线程的工厂
				new ThreadPoolExecutor.AbortPolicy());
		
		
	}
	
	public static ThreadPoolManager getInstance(){
		return mInstance;
	}
	/**
	 * 添加任务
	 * @param runnable
	 */
	public void execute(Runnable runnable){
		if(runnable==null) return;
		
		executor.execute(runnable);
	}
	
	/**
	 * 移除任务
	 * @param runnable
	 */
	public void remove(Runnable runnable){
		if(runnable==null) return;
		
		executor.remove(runnable);
	}
}
