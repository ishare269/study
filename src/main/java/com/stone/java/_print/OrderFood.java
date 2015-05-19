package com.stone.java._print;

/**
 * 点单菜品
 * 
 * @author Stone+
 * 
 */
public class OrderFood {
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 菜品编号
	 */
	private String foodId;
	/**
	 * 菜品数量
	 */
	private int foodCount;
	/**
	 * 订单编号
	 */
	private String orderId;

	/**
	 * 菜品
	 */
	private Food food;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFoodId() {
		return foodId;
	}

	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}

	public int getFoodCount() {
		return foodCount;
	}

	public void setFoodCount(int foodCount) {
		this.foodCount = foodCount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

}
