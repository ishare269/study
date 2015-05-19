package com.stone.java._socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class Receiver {
	private int port = 8000;
	private Socket socket;
	private ServerSocket serverSocket;
	private static int stopWay = 1;// 结束通信的方式
	private final int natural_stop = 1;// 自然结束
	private final int sudden_stop = 2;// 突然终止程序
	private final int socket_stop = 3;// 关闭socket，在结束程序
	private final int input_stop = 4;// 关闭输入流，在结束程序
	private final int serversocket_stop = 5;// 关闭socket，在结束程序
	public Receiver() throws IOException {
		serverSocket = new ServerSocket(port);
	}
	private BufferedReader getReader(Socket socket) throws IOException{
		return new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void receiver() throws Exception{
		Socket socket = null;
		socket  = serverSocket.accept();
		BufferedReader br = getReader(socket);
		for(int i =0;i<20;i++){
			String msg = br.readLine();
			System.out.println("receive:"+msg);
			Thread.sleep(1000);
			if(i==2){
				if(stopWay==sudden_stop){
					System.out.println("突然终止程序");
					System.exit(0);
				}else if(stopWay==socket_stop){
					System.out.println("关闭Socket并终止程序");
					socket.close();
					break;
				}else if(stopWay==input_stop){
					System.out.println("关闭输入流并终止程序");
					socket.shutdownInput();
					break;
				}else if(stopWay==serversocket_stop){
					System.out.println("关闭socketserver并终止程序");
					serverSocket.close();
					break;
				}
			}
		}
		if(stopWay==natural_stop){
			socket.close();
			serverSocket.close();
		}
	}

	public static void main(String[] args) throws IOException, Exception {
//		new Receiver().receiver();
		Socket socket = new Socket();
		SocketAddress remoteAddr1 = new InetSocketAddress("localhost",8000);
		SocketAddress remoteAddr2 = new InetSocketAddress("localhost",8001);
		socket.connect(remoteAddr1,60000);
		socket.connect(remoteAddr2,60000);
	}
}
