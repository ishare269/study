package com.stone.java._arithmetic;

/**
 * 斐波那契数
 * 
 * @author Stone
 * 
 */
public class FibonacciNumber {
	/*
	 * 编写一个计算前100位斐波那契数的函数。根据定义，斐波那契序列的前两位数字是0和1，随后的每个数字是前两个数字的和。例如，前10位斐波那契数为：0，
	 * 1，1，2，3，5，8，13，21，34
	 */
	public static void main(String[] args) {
		int a = 0, b = 1, c;
		for (int i = 0; i < 100; i++) {
			System.out.println(a);
			c = b;
			b = a + b;
			a = c;
		}
	}
}
