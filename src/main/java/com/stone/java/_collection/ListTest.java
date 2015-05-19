package com.stone.java._collection;

import java.util.*;

/**
 * 
 如果你要将一个数组转换为list时最好将其转换为一个string，使用Arrays.toString代替上面的方法吧。
 * 即使对于基本类型的数组该方法也不会出现任何问题。 如果你打算将一个基本类型的数组转换为所对应的封装类型的list，使用Apache Commons
 * Lang吧，关于这个可能你很早就在项目中使用过了，类似下面这样使用ArrayUtils.toObject：
 * 
 * List<Integer> list = Arrays.asList(ArrayUtils.toObject(new int[] { 1, 2 }));
 * 请注意：一般情况下推荐使用原始数据类型数组而不是装箱后的原始数据类型列表。
 * 
 * 如果你打算将一个引用类型的数组转换为list，可以直接使用Arrays.asList：
 * 
 * List<String> list = Arrays.asList(new String[] { "a", "b" });
 * 
 * 不要忘了告诉和你一起工作的人以确保他们不和你犯同样的错误。当然，你也可以选择仅仅记住那些使用Arrays.asList方法时可能出现问题的地方，
 * 并使用普通的for循环来代替，但是那会使你的代码很杂乱，还会带来性能方面的问题。
 */

public class ListTest {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add(null);
		list.add(0, "0");
		list.add("张三");
		for (int i = 0; i < list.size(); i++) {
			System.out.println();
		}
	}
}
