package com.stone.java._invocationhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LoggingInvocationHandler implements InvocationHandler {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(LoggingInvocationHandler.class.getName());
	private Object receiverObject;

	public LoggingInvocationHandler(Object object) {
		this.receiverObject = object;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("调用方法：" + method.getName() + ";参数为："
				+ Arrays.deepToString(args));
		return method.invoke(receiverObject, args);
	}

}
