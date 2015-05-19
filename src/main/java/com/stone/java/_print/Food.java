package com.stone.java._print;

/**
 * 菜品
 * <p>Class Name: Food</p>
 * <p>Description: 类功能说明</p>
 * <p>Sample: 该类的典型使用方法和用例</p>
 * <p>Author: 刘劲松</p>
 * <p>Date: 2015-1-7</p>
 * <p>Modified History: 修改记录，格式(Name)  (Version)  (Date) (Reason & Contents)</p>
 */
public class Food {
	
	private String id;//主键
	
	private String name;//菜名
	
	private String imgSrc;//图片
	
	private String remark;//描述
	
	private Double price;//价格
	
	private Double discountPrice;//打折后的价格
	
	private String discountDesc;//打折描述
	
	private Integer praiseTotal;//点赞总数
	
	private String isRecommend;//是否推荐
	
	private String isSell;//是否出售
	
	private String typeId;//所属分类

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getDiscountDesc() {
		return discountDesc;
	}

	public void setDiscountDesc(String discountDesc) {
		this.discountDesc = discountDesc;
	}

	public Integer getPraiseTotal() {
		return praiseTotal;
	}

	public void setPraiseTotal(Integer praiseTotal) {
		this.praiseTotal = praiseTotal;
	}

	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getIsSell() {
		return isSell;
	}

	public void setIsSell(String isSell) {
		this.isSell = isSell;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}
