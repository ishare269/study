package com.stone.java._socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Sender {
	private String host = "localhost";
	private int port = 8000;
	private Socket socket;
	private static int stopWay = 1;// 结束通信的方式
	private final int natural_stop = 1;// 自然结束
	private final int sudden_stop = 2;// 突然终止程序
	private final int socket_stop = 3;// 关闭socket，在结束程序
	private final int output_stop = 4;// 关闭输出流，在结束程序

	public Sender() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
	}

	public static void main(String[] args) throws UnknownHostException,
			IOException, InterruptedException {
		 new Sender().send();
	}

	private void send() throws IOException, InterruptedException {
		PrintWriter pw = getWriter(socket);
		for (int i = 0; i < 20; i++) {
			String msg = "hello_" + i;
			pw.println(msg);
			System.out.println("hello_" + i);
			Thread.sleep(500);
			if (i == 2) {
				if (stopWay == sudden_stop) {
					System.out.println("突然终止程序");
					System.exit(0);
				} else if (stopWay == socket_stop) {
					System.out.println("关闭Socket并");
					socket.close();
					break;
				} else if (stopWay == output_stop) {
					socket.shutdownOutput();
					System.out.println("关闭输出流并终止程序");
					break;
				}
			}
		}
		if (stopWay == natural_stop) {
			socket.close();
		}
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		return new PrintWriter(socket.getOutputStream(), true);
	}

}
