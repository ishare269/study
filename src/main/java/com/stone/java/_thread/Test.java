package com.stone.java._thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

	// /** 一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待 */
	// private static CountDownLatch latch = new CountDownLatch(4);
	// static int i = 0;
	//
	// public static void main(String[] args) throws InterruptedException {
	// final ExecutorService exec = Executors.newFixedThreadPool(10);// 创建线程池
	// for (int index = 0; index < 4; i++) {
	// final int NO = index + 1;
	// Runnable runnable = new Runnable() {
	// public void run() {
	// try {
	// i = NO;
	// System.out.println(NO + "working");
	// ThreadTest.sleep(1000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }finally{
	// latch.countDown();// 递减锁存器的计数，如果计数到达零，则释放所有等待的线程。
	// }
	// }
	// };
	// exec.submit(runnable);// 提交一个返回值的任务用于执行，返回一个表示任务的未决结果的
	// }
	// latch.await();// 使当前线程在锁存器倒计数至零之前一直等待，除非线程被中断。
	// System.out.println("finish");
	// System.out.println(i);
	// exec.shutdown();// 启动一次顺序关闭，执行以前提交的任务，但不接受新任务。
	//
	// }

	private static CountDownLatch mLatch = new CountDownLatch(4);
	static int i = 0;

	public static void main(String[] args) throws InterruptedException {
		// 创建线程池，线程数10个
		final ExecutorService exec = Executors.newFixedThreadPool(10);
		for (int index = 0; index < 4; index++) {
			final int NO = index + 1;
			Runnable run = new Runnable() {
				public void run() {
					try {
						i = NO;
						System.out.println(NO + " working");
						java.lang.Thread.sleep(2000);
					} catch (InterruptedException e) {
					} finally {
						mLatch.countDown();
					}
				}
			};
			exec.submit(run);
		}
		mLatch.await();
		System.out.println("finish");
		System.out.println(i);
		exec.shutdown();
	}
}
