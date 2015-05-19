package com.stone.java._enum;

import java.util.EnumMap;
import java.util.EnumSet;

interface GenderInterface{
	void print();
}
public enum Gender implements GenderInterface{
	FEMALE("女", 0), MALE("男", 1), NEUTRAL("中性", 2);
	private String name;
	private int index;

	private Gender(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	// 普通方法
    public static String getName(int index) {
        for (Gender g : Gender.values()) {
            if (g.getIndex() == index) {
                return g.name;
            }
        }
        return null;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * @description:ENUM遍历 
	 * @author 石涛
	 * @date 2014-12-4
	 * @-- ------------------------------------------------
	 * @xx 修改人修改日期 修改描述
	 * @xx 石涛 2014-12-4 创建
	 * @-- ------------------------------------------------
	 * @Version Ver1.0
	 */
	public static void enumForech(){
		Gender[] gender = Gender.values();
		for(Gender g : gender){
			System.out.println(g.getName());
		}
	}
	
	/**
	 * @description:ENUMMAP 
	 * @author 石涛
	 * @date 2014-12-4
	 * @-- ------------------------------------------------
	 * @xx 修改人修改日期 修改描述
	 * @xx 石涛 2014-12-4 创建
	 * @-- ------------------------------------------------
	 * @Version Ver1.0
	 */
	public static void enumMap(){
		EnumMap<Gender,String> enumMap = new EnumMap<Gender, String>(Gender.class);
		enumMap.put(Gender.FEMALE, "女");
		enumMap.put(Gender.MALE, "男");
		for(Gender g : Gender.values()){
			System.out.println(enumMap.get(g));
		}
	}
	
	/**
	 * @description:ENUMSET 
	 * @author 石涛
	 * @date 2014-12-4
	 * @-- ------------------------------------------------
	 * @xx 修改人修改日期 修改描述
	 * @xx 石涛 2014-12-4 创建
	 * @-- ------------------------------------------------
	 * @Version Ver1.0
	 */
	public static void enumSet(){
		EnumSet<Gender> enumSet = EnumSet.allOf(Gender.class);
		for(Gender g : enumSet){
			System.out.println(g);
		}
	}
	
	public static void main(String[] args) {
		switch (Gender.FEMALE) {
		case FEMALE:
			System.out.println(Gender.FEMALE.getName());
			break;
		case MALE:
			System.out.println(Gender.MALE.getName());
			break;
		default:
			break;
		}
	}

	public void print() {
		System.out.println(this.index+","+this.name);
	}

}
