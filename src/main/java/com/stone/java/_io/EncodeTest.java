package com.stone.java._io;

import java.io.UnsupportedEncodingException;

/**
 * @名称：编码
 * @描述： 当你的字节序列是某种编码时，这时候想把字节序列变成字符串，也需要用这个编码方式，否者会出现乱码
 * @author Stone
 * 
 */
public class EncodeTest {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "中国China";
		byte[] bytes1 = s.getBytes();// 转换成为字节序列用的是项目的默认编码
		for (byte b : bytes1) {
			// 把字节（转换为int）以16进制的方式显示
			System.out.print(Integer.toHexString(b & 0xff) + " ");
		}
		System.out.println();
		// gbk编码中文占用2个字节，英文占用1个字节
		byte[] bytes2 = s.getBytes("gbk");
		for (byte b : bytes2) {
			System.out.print(Integer.toHexString(b & 0xff) + " ");
		}

		System.out.println();
		// utf-8编码中文占用3个字节，英文占用1个字节
		byte[] bytes3 = s.getBytes("utf-8");
		for (byte b : bytes3) {
			System.out.print(Integer.toHexString(b & 0xff) + " ");
		}

		System.out.println();
		// java双字节编码 utf-16be 编码中中文占用2个字节，英文占用2个字节
		byte[] bytes4 = s.getBytes("utf-16be");
		for (byte b : bytes4) {
			System.out.print(Integer.toHexString(b & 0xff) + " ");
		}

		System.out.println();
		String str1 = new String(bytes4);// 用项目默认的编码
		System.out.println(str1);
		String str2 = new String(bytes4, "utf-16be");
		System.out.println(str2);
		/**
		 * 文本文件可以存放任意编码的字节序列 如果在中文系统上创建文本文件，那么该文本文件只认识ansi编码
		 * 在中文系统上创建“联”的文本文件出现乱码是一种巧合，它正好符合了utf-8编码的规则
		 */
	}
}
