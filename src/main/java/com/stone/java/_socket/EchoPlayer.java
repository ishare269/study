package com.stone.java._socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EchoPlayer {
	BufferedReader reader;

	public String echo(String msg) {
		return "echo:" + msg;
	}

	public void talk() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in,"gbk"));
		String msg = null;
		while ((msg = br.readLine()) != null) {
			System.out.println(echo(msg));
			if (msg.equalsIgnoreCase("bye"))
				break;
		}
	}
	
	public static void main(String[] args) {
		try {
			new EchoPlayer().talk();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
