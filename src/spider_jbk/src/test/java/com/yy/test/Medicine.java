/**
 * <html>
 * <body>
 *  <P>  Copyright 2015-2016 www.yunyichina.cn Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2016年8月4日</p>
 *  <p> Created by ryb</p>
 *  </body>
 * </html>
 */
package com.yy.test;

/**
 * @Package: com.yy.platform.queryMedicine.medicine.entity
 * @ClassName: Resource
 * @Statement:
 *             <p>
 *             </p>
 * @JDK version used:
 * @Author: ryb
 * @Create Date: 2016年8月14日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Medicine {

	class FieldStruct {
		private String value;
		private String title;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public FieldStruct(String value, String title) {
			this.value = value;
			this.title = title;
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1626974507313155951L;
	/** 药品名称 */
	private FieldStruct ypmc;
	/**
	 * 批准文号
	 */
	private String pzwh;
	/*
	 * 药品类别
	 */
	private String yplb;
	/**
	 * 成份
	 */
	private String cf;

	public String getYplb() {
		return yplb;
	}

	public void setYplb(String yplb) {
		this.yplb = yplb;
	}

	/**
	 * 性状
	 */
	private String xz;
	/*
	 * 功能主治
	 */
	private String gnzz;
	/*
	 * 规格
	 */
	private String gg;
	/*
	 * 包装
	 */
	private String bz;
	/*
	 * 用法用量
	 */
	private String yfyl;
	/*
	 * 注意事项
	 */
	private String zysx;
	/*
	 * 禁忌
	 */
	private String jj;
	/*
	 * 药理作用
	 */
	private String ylzy;
	/*
	 * 适应症--疾病对应
	 */
	private String syzjbdy;
	/*
	 * 剂型
	 */
	private String jx;
	/*
	 * 儿童注意事项
	 */
	private String etzysx;
	/*
	 * 老人注意事项
	 */
	private String lrzysx;
	/*
	 * 妊娠与哺乳期注意事项
	 */
	private String rcprzysx;
	/*
	 * 生产地址
	 */
	private String scdd;
	/*
	 * 适应症
	 */
	private String syz;
	/*
	 * 品牌名
	 */
	private String ppm;
	/*
	 * 生产企业名称
	 */
	private String scqymc;
	/*
	 * 有效期
	 */
	private String yxq;
	/*
	 * 贮藏
	 */
	private String zc;

	public FieldStruct getYpmc() {
		return ypmc;
	}

	public void setYpmc(FieldStruct ypmc) {
		this.ypmc = ypmc;
	}

	public String getPzwh() {
		return pzwh;
	}

	public void setPzwh(String pzwh) {
		this.pzwh = pzwh;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getXz() {
		return xz;
	}

	public void setXz(String xz) {
		this.xz = xz;
	}

	public String getGnzz() {
		return gnzz;
	}

	public void setGnzz(String gnzz) {
		this.gnzz = gnzz;
	}

	public String getGg() {
		return gg;
	}

	public void setGg(String gg) {
		this.gg = gg;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getYfyl() {
		return yfyl;
	}

	public void setYfyl(String yfyl) {
		this.yfyl = yfyl;
	}

	public String getZysx() {
		return zysx;
	}

	public void setZysx(String zysx) {
		this.zysx = zysx;
	}

	public String getJj() {
		return jj;
	}

	public void setJj(String jj) {
		this.jj = jj;
	}

	public String getYlzy() {
		return ylzy;
	}

	public void setYlzy(String ylzy) {
		this.ylzy = ylzy;
	}

	public String getSyzjbdy() {
		return syzjbdy;
	}

	public void setSyzjbdy(String syzjbdy) {
		this.syzjbdy = syzjbdy;
	}

	public String getJx() {
		return jx;
	}

	public void setJx(String jx) {
		this.jx = jx;
	}

	public String getEtzysx() {
		return etzysx;
	}

	public void setEtzysx(String etzysx) {
		this.etzysx = etzysx;
	}

	public String getLrzysx() {
		return lrzysx;
	}

	public void setLrzysx(String lrzysx) {
		this.lrzysx = lrzysx;
	}

	public String getRcprzysx() {
		return rcprzysx;
	}

	public void setRcprzysx(String rcprzysx) {
		this.rcprzysx = rcprzysx;
	}

	public String getScdd() {
		return scdd;
	}

	public void setScdd(String scdd) {
		this.scdd = scdd;
	}

	public String getSyz() {
		return syz;
	}

	public void setSyz(String syz) {
		this.syz = syz;
	}

	public String getPpm() {
		return ppm;
	}

	public void setPpm(String ppm) {
		this.ppm = ppm;
	}

	public String getScqymc() {
		return scqymc;
	}

	public void setScqymc(String scqymc) {
		this.scqymc = scqymc;
	}

	public String getYxq() {
		return yxq;
	}

	public void setYxq(String yxq) {
		this.yxq = yxq;
	}

	public String getZc() {
		return zc;
	}

	public void setZc(String zc) {
		this.zc = zc;
	}

	public Medicine() {
		super();
	}

}