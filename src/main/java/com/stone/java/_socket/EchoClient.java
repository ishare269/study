package com.stone.java._socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * @author Administrator
 * 
 */
public class EchoClient {
	/**
	 * 服务器地址
	 */
	private String host = "localhost";
	/**
	 * 服务器监听端口
	 */
	private int port = 8000;

	/**
	 * 
	 */
	private Socket socket;

	public EchoClient() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		System.out.println(socket.getPort());
		System.out.println(socket.getLocalPort());
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		return new PrintWriter(socket.getOutputStream(), true);
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		return new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
	}

	public void talk() {
		try {
			BufferedReader br = getReader(socket);
			PrintWriter pw = getWriter(socket);
			BufferedReader brInput = new BufferedReader(new InputStreamReader(
					System.in));
			String msg = null;
			while ((msg = brInput.readLine()) != null) {
				pw.println(msg);
				System.out.println(br.readLine());
				if (msg.equalsIgnoreCase("bye")) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		 new EchoClient().talk();
	}
}
