package com.xiaomaguanjia.keeper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 *
 * @author 王昌龙
 *
 */
public class ThreadPool {
	/** 线程池任务 */
	public static ExecutorService services = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
}