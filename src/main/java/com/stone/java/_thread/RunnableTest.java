package com.stone.java._thread;

public class RunnableTest implements Runnable{
	private String name;
	
	public RunnableTest(String name) {
		this.name = name;
	}
	
	public void run() {
		for (int i = 0; i < 5; i++) {
            for (long k = 0; k < 100000000; k++) ;
            System.out.println(name + ": " + i);
        }
	}
	
	public static void main(String[] args) {
		Thread thread1 = new Thread(new RunnableTest("张三"));
		Thread thread2 = new Thread(new RunnableTest("李四"));
		thread1.start();
		thread2.start();
	}

}
