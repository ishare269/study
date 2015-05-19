package com.stone.java._socket;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 1、在服务器程序中，先创建一个ServerSocket对象，构造方法中指定要监听的端口（ServerSocket的够着方法负责在操作系统中把当前进程注册为服务器进程）
 * 	ServerSocket server = new ServerSocket(8000);
 * 2、服务器程序通过一直监听端口，来接受客户程序的连接请求（服务器程序调用ServerSocket对象的accept()方法，该方法一直监听端口，等待客户的连接请求，如果收到一个连接请求，accept（）方法就会返回一个Socket对象）
 * 	Socket socket = ServerSocket.accept();
 * 3、连接通道进行数据读取
 * @author Stone
 *
 */
public class SocketServer {
	private ServerSocket serverSocket;
	private Socket socket;
	private DataInputStream in;
	private PrintWriter out;
	
	public SocketServer() {
		try {
			System.out.println("========server excute begin==========");
			serverSocket = new ServerSocket(10000);
			while(true){
				System.out.println("========server excute==========");
				//ServerSocket.accept方法用于产生"阻塞"，直到接受到一个连接，并且返回一个客户端的Socket对象实例
				socket = serverSocket.accept();
				//Socket.getInputStream()方法获得网络连接输入
				in = new DataInputStream(socket.getInputStream());
				//Socket.getOutputStream方法连接的另一端将得到输入，同时返回一个OutputStream对象实例
//				out = new PrintWriter(socket.getOutputStream(),true);
				//
				String line = in.readUTF();
				//
				System.out.println(line);
//				out.println("You input is:"+line);
//				out.close();
				in.close();
				socket.close();
				System.out.println("========server excute end==========");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new SocketServer();
	}
}
