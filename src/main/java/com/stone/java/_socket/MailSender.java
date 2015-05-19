package com.stone.java._socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import sun.misc.BASE64Encoder;

/**
 * SMTP简单程序
 * 
 * @author Stone
 * 
 */
public class MailSender {
	/**
	 * SMTP邮件服务器的主机名
	 */
	private String smtpServer = "smtp.citiz.net";
//	private String smtpServer = "smtp.mydomain.com";
	/**
	 * 服务器端口
	 */
	private int port = 25;

	public static void main(String[] args) {
		Message message = new Message("495631701@qq.com", "476896460@qq.com",
				"Test", "smtp mail test!");
		new MailSender().sendMail(message);
	}

	@SuppressWarnings("restriction")
	private void sendMail(Message message) {
		Socket socket = null;
		try {
			socket = new Socket(smtpServer, port);//链接到遇见服务器
			BufferedReader br = getReader(socket);
			PrintWriter pw = getWriter(socket);
			String localhost = InetAddress.getLocalHost().getHostName();//客户端主机名字
			
			String username = "java_mail";
			String password = "123456";
			//对用户名和口令进行Base64编码
			username = new BASE64Encoder().encode(username.getBytes());
			password = new BASE64Encoder().encode(password.getBytes());
			sendAndReceive(null, br, pw);
			sendAndReceive("HELO" + localhost, br, pw);
			sendAndReceive("AUTO LOGIN" + localhost, br, pw);//认证命令
			sendAndReceive(username, br, pw);//认证命令
			sendAndReceive(password, br, pw);//认证命令
			
			sendAndReceive("MAIL FROM:" + message.from, br, pw);
			sendAndReceive("RCPT TO:" + message.to, br, pw);
			sendAndReceive("DATA", br, pw);
			pw.println(message.data);
			System.out.println("Client>" + message.data);
			sendAndReceive(".", br, pw);
			sendAndReceive("QUIT", br, pw);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != socket)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 发送一行字符串，并接受一行服务器的响应数据
	 * 
	 * @param str
	 * @param br
	 * @param pw
	 * @throws IOException
	 */
	private void sendAndReceive(String str, BufferedReader br, PrintWriter pw)
			throws IOException {
		if (null != str) {
			System.out.println("Client>" + str);
			pw.println(str);
		}
		String response;
		if (null != (response = br.readLine())) {
			System.out.println("Server>" + response);
		}
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		return new PrintWriter(socket.getOutputStream(),true);
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		return new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
	}
}

/**
 * 邮件
 * 
 * @author Stone
 * 
 */
class Message {
	String from;// 发送者的邮件地址
	String to;// 接收者的邮件地址
	String subject;// 邮件标题
	String content;// 邮件正文
	String data;// 邮件内容，包括邮件标题和正文

	public Message(String from, String to, String subject, String content) {
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.content = content;
		this.data = "Subject:" + subject + "\r\n" + content;
	}
}
