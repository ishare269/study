package com.stone.java._io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * java提供的对文件内容的访问，既可以读文件，以可以写文件。 支持随机访问文件，可以访问文件的任意位置 java 文件模型 在硬盘上的文件是byte
 * byte byte存储的，是数据的集合 打开文件 两种模式“rm”读写“r”只读 RandomAccessFile raf = new
 * RandomAccessFile(file,"w"); 文件指针，打开文件的时指针在开头pointer=0； 写方法 读方法
 * 
 * @author Administrator
 * 
 */
public class RandomAccessFileTest {
	public static void main(String[] args) throws IOException {
		File demo = new File("demo");
		if (!demo.exists()) {
			demo.mkdir();
		}
		File file = new File(demo, "raf.dat");
		if (!file.exists()) {
			file.createNewFile();
		}
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		// 获得指针的位置
		System.out.println(raf.getFilePointer());
		raf.write('A');// 只写了一个字节
		System.out.println(raf.getFilePointer());
		raf.write('B');
		int i = 0x7fffffff;
		// 用write方法每次只能写一个字节，如果要把i写进去就得写4次
		raf.write(i >>> 24);
		raf.write(i >>> 16);
		raf.write(i >>> 8);
		raf.write(i);
		System.out.println(raf.getFilePointer());
		//写一个int
		raf.writeInt(i);
		//写一个汉子
		raf.write("中".getBytes("gbk"));
		System.out.println(raf.length());
		
		//读文件，必须把指针移到头部
		raf.seek(0);
		//一次性读取，把文件中的内容读到字节数组中
		byte[] buf = new byte[(int) raf.length()];
		raf.read(buf);
		System.out.println(Arrays.toString(buf));
		String str1 = new String(buf,"gbk");
		System.out.println(str1);
		raf.close();
	}
}
