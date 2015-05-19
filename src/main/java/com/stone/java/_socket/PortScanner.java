package com.stone.java._socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 端口扫描
 * 
 * @author Stone
 * 
 */
public class PortScanner {
	public static void main(String[] args) throws UnknownHostException,
			IOException {
		String host = "localhost";
		if (args.length > 0) {
			host = args[0];
		}
		new PortScanner().scan(host);
	}

	private void scan(String host) {
		Socket socket = null;
		for (int port = 1; port < 1024; port++) {
			try {
				socket = new Socket(host, port);
				System.out.println("There is a server on port" + port);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Can't connect to port" + port);
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
