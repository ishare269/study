package com.stone.java._thread;

public class DeadLockRisk extends Thread {
	private static class Resource {
		public int value;
	}

	private Resource resource1 = new Resource();
	private Resource resource2 = new Resource();

	public int read() {
		synchronized (resource1) {
			synchronized (resource2) {
				return resource1.value + resource2.value;
			}
		}
	}

	public void write(int a, int b) {
		synchronized (resource1) {
			synchronized (resource2) {
				resource1.value = a;
				resource2.value = b;
			}
		}
	}

	@Override
	public void run() {
		this.read();
		this.write(1, 2);
	}

	public static void main(String[] args) {
		Test1 test1 = new Test1();
		test1.x(1,2l);
	}

}

class Test1<I extends Integer> {
	<L extends Long> void x(I i, L l) {
		System.out.println(i.intValue() + ", " + l.longValue());
	}
}
