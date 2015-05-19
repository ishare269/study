package com.stone.java._net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test {
	public static void main(String[] args) throws UnknownHostException {
		// 获得主机名 访问DNS域名服务器才能获得域名
		InetAddress host = InetAddress.getByName("114.215.197.146");
		System.out.println(host);

		/** 域名获取IP */
		String hostName = "youxin.telecomjs.com";
		InetAddress address = InetAddress.getByName(hostName);
		System.out.println(address);

	}
}