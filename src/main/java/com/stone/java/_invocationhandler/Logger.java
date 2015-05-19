package com.stone.java._invocationhandler;

import java.lang.reflect.Proxy;


public class Logger {

	public static Logger getLogger(Class<LoggingInvocationHandler> class1) {
		return null;
	}

	public static void main(String[] args) {
		LoggingInvocationHandler handler = new LoggingInvocationHandler("1231");
		ClassLoader cl = Logger.class.getClassLoader();
		Comparable obj =  (Comparable) Proxy.newProxyInstance(cl, new Class[]{Comparable.class}, handler);
		obj.compareTo("123");
	}
}
