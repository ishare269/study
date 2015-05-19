package com.stone.java._io;

import java.io.File;
import java.io.IOException;

/**
 * @名称：java.io.File类用于表示文件或目录
 * @描述：File类只用于表示文件（目录）的信息（名称、大小等），不能用于文件内容的访问
 * @author Stone
 * 
 */
public class FileTest {
	/**
	 * 列出指定目录下包括子目录的所有文件
	 * 
	 * @param dir
	 * @throws IOException
	 */
	public static void listDirectory(File dir) throws IOException {
		if (!dir.exists()) {
			throw new IllegalArgumentException(dir + "不存在！");
		}
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException(dir + "不是目录！");
		}
		// String[] filenames = dir.list();
		// for (String str : filenames) {
		// System.out.println(str);
		// }
		/**
		 * 如果要遍历子目录下的内容就需要构造成File对象做递归操作，File提供了直接文件数组
		 */
		File[] files = dir.listFiles();
		if (null != files && files.length > 0) {
			for (File file : files) {
				if (file.isDirectory()) {
					// 递归
					listDirectory(file);
				} else {
					System.out.println(file);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		/*
		 * File file = new File("C:" + File.separator); if (file.isDirectory())
		 * { file.mkdir(); // file.mkdirs(); } if (file.exists()) {
		 * file.delete(); } else { if (file.isFile()) { file.createNewFile(); }
		 * } file.getAbsoluteFile(); file.getAbsolutePath();
		 */
		listDirectory(new File("C:\\Users\\Administrator\\Desktop\\my"));
	}

}
