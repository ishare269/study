package com.stone.java._http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class HttpInvoker {

	public static void readContentFromGet(String url) throws IOException {
		// 拼接URL，使用URLEncoder.encode方法对特殊和不可见的字符串编码;
		url = url + "?username=" + URLEncoder.encode("stone", "utf-8");
		URL getUrl = new URL(url);
		// 根据URL打开链接，URL.openConenction()根据url的类型返回不同的URLConnection子类的对象
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		// 进行连接 但实际上get请求要在getInputStream()请求才能真正发送到服务器
		connection.connect();
		// 获得输出流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String lines;
		while ((lines = reader.readLine()) != null) {
			System.out.println(lines);
		}
		reader.close();
		// 断开连接
		connection.disconnect();
	}

	public static void main(String[] args) {
		try {
			// readContentFromGet("http://www.baidu.com");
			// readContentFromPost("http://www.baidu.com");
			sendGet("http://www.baidu.com", "name=zhangsan");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readContentFromPost(String url) throws IOException {
		// Post请求的url，与get不同的是不需要带参数
		URL postUrl = new URL(url);
		// 打开连接
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		// Output to the connection. Default is false, set to true because post
		// method must write something to the
		// 设置是否向connection输出，因为这个是post请求，参数要放在HTTP正文内，因此需要设为true
		connection.setDoInput(true);
		// Read from the connection. Default is true.
		connection.setDoInput(true);
		// Set the post method. Default is GET
		connection.setRequestMethod("POST");
		// Post cannot use caches
		// Post 请求不能使用缓存
		connection.setUseCaches(false);
		// URLConnection.setFollowRedirects是static 函数，作用于所有的URLConnection对象。
		// connection.setFollowRedirects(true);
		// URLConnection.setInstanceFollowRedirects 是成员函数，仅作用于当前函数
		connection.setInstanceFollowRedirects(true);
		// 配置本次连接的Content-type，配置为application/x- www-form-urlencoded的
		// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		// 连接，从postUrl.openConnection()至此的配置必须要在 connect之前完成，
		// 要注意的是connection.getOutputStream会隐含的进行 connect。
		connection.connect();
		DataOutputStream out = new DataOutputStream(
				connection.getOutputStream());
		// 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
		String content = "firstname= "
				+ URLEncoder.encode(" 一个大肥人 ", " utf-8 ");
		// DataOutputStream.writeBytes将字符串中的16位的 unicode字符以8位的字符形式写道流里面
		out.writeBytes(content);
		// flush and close
		out.flush();
		out.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		reader.close();
		connection.disconnect();
	}

	/**
	 * HttpURLConnection.connect函数，实际上只是建立了一个与服务器的
	 * tcp连接，并没有实际发送http请求。无论是post还是get，http请求实际上直到 HttpURLConnection
	 * .getInputStream()这个函数里面才正式发送出去
	 * 
	 * 
	 * 发起POST请求，顺序是重中之中，对connection对象的一切配置（那一堆set函数）都必须要在connect()函数执行之前完成。而对
	 * outputStream的写操作，又必须要在inputStream的读操作之前。这些顺序实际上是由http请求的格式决定的。
	 * 
	 * 
	 * http请求实际上由两部分组成，一个是
	 * http头，所有关于此次http请求的配置都在http头里面定义，一个是正文content，在connect()函数里面，会根据
	 * HttpURLConnection对象的配置值生成http头，因此在调用connect函数之前，就必须把所有的配置准备好。
	 * 
	 * 紧接着
	 * http头的是http请求的正文，正文的内容通过outputStream写入，实际上outputStream不是一个网络流，充其量是个字符串流，
	 * 往里面写入的东西不会立即发送到网络，而是在流关闭后，根据输入的内容生成http正文。
	 */

	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = url + "?" + param;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 建立实际的连接
			conn.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} finally {// 使用finally块来关闭输入流
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}