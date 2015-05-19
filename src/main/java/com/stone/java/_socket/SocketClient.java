package com.stone.java._socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	public SocketClient() {
		try {
//			socket = new Socket("10.0.1.85",10000);
//			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			out = new PrintWriter(socket.getOutputStream(),true);
//			BufferedReader line = new BufferedReader(new InputStreamReader(System.in));
//			out.println(line.readLine());
//			line.close();
//			out.close();
//			in.close();
//			socket.close();
			Socket socket = new Socket("10.0.1.85", 10000);
			DataOutputStream dos = null;
			if (socket.isConnected()) {
				dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF("sssss");
				dos.flush();
			}
			dos.close();
			socket.close();
			System.out.println("========client excute over==========");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new SocketClient();
	}
}
