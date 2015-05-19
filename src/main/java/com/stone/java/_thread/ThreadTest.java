package com.stone.java._thread;

public class ThreadTest extends Thread {
	public ThreadTest(String name) {
		super(name);
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			for (long k = 0; k < 100000000; k++)
				;
			System.out.println(this.getName() + " :" + i);
			Thread.currentThread().yield();
		}
	}

//	public static void main(String[] args) {
//		Thread thread1 = new ThreadTest("张三");
//		Thread thread2 = new ThreadTest("李四");
//		thread1.start();
//		thread2.start();
//	}
	
	public static void main(String[] args) {
		MyRunnable r = new MyRunnable();
		Thread ta = new Thread(r,"thread-A");
		Thread tb = new Thread(r,"thread-B");
		ta.start();
		tb.start();
	}
}

class Foo {
	private int  x = 100;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int fix(int x) {
		synchronized (this) {
			this.x = this.x - x;
		}
		return this.x;
	}
}

class MyRunnable implements Runnable {
	private Foo foo = new Foo();

	public void run() {
		for (int i = 0; i < 3; i++) {
			this.fix(30);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + ":当前对象x的值="
					+ foo.getX());
		}
	}

	private int fix(int x) {
		return foo.fix(x);
	}

}
