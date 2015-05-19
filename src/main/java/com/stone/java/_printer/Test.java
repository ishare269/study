package com.stone.java._printer;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Test {
	public static void main(String[] args) {
		// 字体放大指令
		char[] FD_FONT = new char[3];
		FD_FONT[0] = 0x1c;
		FD_FONT[1] = 0x21;
		FD_FONT[2] = 4;
		// 字体加粗指令
		char[] FONT_B = new char[3];
		FONT_B[0] = 27;
		FONT_B[1] = 33;
		FONT_B[2] = 8;
		// 清除字体纵向放大,恢复初始化
		char[] CLEAR_FONT = new char[3];
		CLEAR_FONT[0] = 0x1c;
		CLEAR_FONT[1] = 0x21;
		CLEAR_FONT[2] = 0;
		try{
			Socket client=new java.net.Socket();
			PrintWriter socketOut;
			client.connect(new InetSocketAddress("192.168.10.3",9100),1000);
			socketOut = new PrintWriter(client.getOutputStream());
			socketOut.write(27);
			socketOut.write(64);
			socketOut.write(FD_FONT);// 字体放大
			socketOut.write(FONT_B);// 字体加粗
			socketOut.write(10);
			socketOut.write("  " + "襄阳汉北酒店" + " \r\n");
			socketOut.flush();// 关键,很重要,不然指令一次性输出,后面指令覆盖前面指令,导致取消放大指令无效
			socketOut.write(CLEAR_FONT);
			socketOut.write(10);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}