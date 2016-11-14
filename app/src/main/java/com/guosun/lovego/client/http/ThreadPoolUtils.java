package com.guosun.lovego.client.http;

import com.guosun.lovego.util.ULog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadPoolUtils {

	private ThreadPoolUtils() {
	}

	// 定义核心线程数，并行线程数
	private static int CORE_POOL_SIZE = 5;

	// 线程池ExecutorService
	private static ExecutorService threadpool;
	// 静态代码块，在类被加载的时候进入
	static {
		threadpool = Executors.newFixedThreadPool(CORE_POOL_SIZE);
		ULog.i("threadpool","threadpool..............");
	}

	public static void execute(Runnable runnable) {
		threadpool.execute(runnable);
	}
}
