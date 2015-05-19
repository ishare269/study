package com.stone.java._arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * Class Name: BubbleSort
 * </p>
 * <p>
 * Description: 冒泡排序
 * </p>
 * <p>
 * Sample: 该类的典型使用方法和用例
 * </p>
 * <p>
 * Author: 石涛
 * </p>
 * <p>
 * Date: 2014-12-3
 * </p>
 * <p>
 * Modified History: 修改记录，格式(Name) (Version) (Date) (Reason & Contents)
 * </p>
 */
public class BubbleSort {
	public static void main(String[] args) {
		Random random = new Random();
		for (int j = 0; j <= 10; j++) {
			List<Integer> randomArrays = new ArrayList<Integer>();
			String temp = null;
			String randomStr = (random.nextLong() + "" + random.nextLong())
					.replace("-", "");
			for (int i = 0; i < randomStr.length(); i++) {
				int num = Integer.valueOf(randomStr.substring(i, i + 1));
				if ("00".equals(temp)) {
					System.out.println("00：" + num);
					num = Integer.valueOf((num + 1) + "" + temp);
					temp = null;
				} else if (temp != null) {
					if ("0".equals(temp) && 0 != num) {
						temp = num + temp;
					} else if ("0".equals(temp) && 0 == num) {
						temp = num + temp;
						continue;
					} else {
						temp += num;
					}
					System.out.println(temp);
					num = Integer.valueOf(temp);
					temp = null;
				} else {
					if (num < 5 && i != randomStr.length() - 1) {
						temp = num + "";
						continue;
					}
				}
				randomArrays.add(num);
			}
			//
			// System.out.println("随机字符串："+randomStr);
			// System.out.print("随机数字串：");
			// for (Integer i : randomArrays) {
			// System.out.print(i);
			// }
			System.out.println();
			System.out.print("随机数字串：");
			for (Integer i : randomArrays) {
				System.out.print(i + ",");
			}
		}
	}
}
