package com.stone.study.domain;

/**
 * <p>
 * Class Name: User
 * </p>
 * <p>
 * Description: 用户
 * </p>
 * <p>
 * Sample: 用户参照实体模型
 * </p>
 * <p>
 * Author: 石涛
 * </p>
 * <p>
 * Date: 2014-10-22
 * </p>
 * <p>
 * Modified History: 修改记录，格式(Name) (Version) (Date) (Reason & Contents)
 * </p>
 */
public class User {
	/** 编号 */
	private long id;
	/** 用户名 */
	private String username;
	/** 昵称 */
	private String nickname;
	/** 真实姓名 */
	private String realname;
	/** 密码 */
	private String password;
	/** 肖像 */
	private String portrait;
	/** 自我介绍 */
	private String userdetail;
	/** 出生年月 */
	private String birthday;
	/** 婚姻状况 0：未婚 1：已婚 */
	private int marriage;
	/** 性别 0：女 1：男 */
	private int sex;
	/** 血型 */
	private String blood;
	/** 体型 */
	private String figure;
	/** 星座 */
	private String constellation;
	/** 学历 */
	private String education;
	/** 省份 */
	private String province;
	/** 城市 */
	private String city;
	/** 街道 */
	private String street;
	/** 行业 */
	private String trade;
	/** 职位 */
	private String job;
	/** 电话 */
	private String tel;
	/** 邮箱 */
	private String mail;
	/** 状态 */
	private int status;
	/** 删除标示 0：正常 1：删除 */
	private int flag;
	/** 授权码 */
	private String openid;
	/** 创建时间 */
	private String addtime;
	/** 最近登录时间 */
	private String logintime;

	/** CONSTRUCTOR */
	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getUserdetail() {
		return userdetail;
	}

	public void setUserdetail(String userdetail) {
		this.userdetail = userdetail;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getMarriage() {
		return marriage;
	}

	public void setMarriage(int marriage) {
		this.marriage = marriage;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBlood() {
		return blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}

	public String getFigure() {
		return figure;
	}

	public void setFigure(String figure) {
		this.figure = figure;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}
}
