package com.stone.java._print;

import java.util.List;

/**
 * 菜品订单
 * 
 * @author Stone+
 * 
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 * 
 */
public class Order {
	/**
	 * 订单编号
	 */
	private String id;
	/**
	 * 下单时间
	 */
	private String createTime;
	/**
	 * 桌号
	 */
	private int deskNo;
	/**
	 * 人数
	 */
	private int peopleNo;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 菜品数量
	 */
	private int foodTotal;
	/**
	 * 总价格
	 */
	private double priceTotal;
	/**
	 * 订单状态 0:下单 1：确认中 2：完成
	 */
	private String status;
	/**
	 * 客户编号
	 */
	private String clientId;
	/**
	 * 商户编号
	 */
	private String merchantId;

	/**
	 * 是否打印 0：未打印（default） 1：已打印
	 */
	private int isprint;
	/**
	 * 订单中的所有菜品
	 */
	List<OrderFood> orderFoods;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getDeskNo() {
		return deskNo;
	}

	public void setDeskNo(int deskNo) {
		this.deskNo = deskNo;
	}

	public int getPeopleNo() {
		return peopleNo;
	}

	public void setPeopleNo(int peopleNo) {
		this.peopleNo = peopleNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(double priceTotal) {
		this.priceTotal = priceTotal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public List<OrderFood> getOrderFoods() {
		return orderFoods;
	}

	public void setOrderFoods(List<OrderFood> orderFoods) {
		this.orderFoods = orderFoods;
	}

	public int getFoodTotal() {
		return foodTotal;
	}

	public void setFoodTotal(int foodTotal) {
		this.foodTotal = foodTotal;
	}

	public int getIsprint() {
		return isprint;
	}

	public void setIsprint(int isprint) {
		this.isprint = isprint;
	}

}
