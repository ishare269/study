package com.stone.java._socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class HTTPClient {
	String host = "www.javathinker.org";
	int port = 80;
	Socket socket;

	public HTTPClient() throws UnknownHostException, IOException {
		new Socket(host, port);
	}
	
	public void communicate(){
		StringBuffer sb = new StringBuffer("");
	}
}
