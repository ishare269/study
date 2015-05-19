package com.stone.java._socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>
 * 1、在服务器程序中，先创建一个ServerSocket对象，构造方法中指定要监听的端口（
 * ServerSocket的够着方法负责在操作系统中把当前进程注册为服务器进程）
 * </p>
 * ServerSocket server = new ServerSocket(8000);
 * <p>
 * 2、服务器程序通过一直监听端口，来接受客户程序的连接请求（服务器程序调用ServerSocket对象的accept(
 * )方法，该方法一直监听端口，等待客户的连接请求
 * ，如果收到一个连接请求，accept（）方法就会返回一个Socket对象，这个Socket对象与客户端的Socket对象形成了一条通信线路）
 * </p>
 * Socket socket = ServerSocket.accept();
 * <p>
 * 3、向输出流写数据（向对方发数据），从输入流读数据（接收对方的数据）
 * </p>
 * Socket.getInputStream(); Socket.getOutputStream();
 * 
 * @author Stone
 * 
 */
public class EchoServer {

	/**
	 * 端口号
	 */
	private int port = 8000;

	/**
	 * 服务器套接字
	 */
	private ServerSocket serverSocket;

	public EchoServer() throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println("服务器启动");
	}

	/**
	 * 回显
	 * 
	 * @param msg
	 * @return
	 */
	public String echo(String msg) {
		return "echo:" + msg;
	}

	/**
	 * 
	 * @param socket
	 * @return PrintWriter 向文本输出流打印对象的格式化表示形式
	 * @throws IOException
	 */
	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}

	public void service() {
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();// 等待客户连接
				System.out.println("New connection accpted"
						+ socket.getInetAddress() + ":" + socket.getPort());
				BufferedReader br = getReader(socket);
				PrintWriter pw = getWriter(socket);
				String msg = null;
				while (null != (msg = br.readLine())) {
					System.out.println(msg);
					pw.println(msg);
					if (msg.equals("bye")) {
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (socket != null)
						socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new EchoServer().service();
	}
}
