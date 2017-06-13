/*
* Copyright  2013. 360buy.com All Rights Reserved. 
* Application : vc_auth_service 
* Class Name  : BaseDomain.java 
* Date Created: 2013-4-12 
* Author      : niuqinghua
* 
* Revision History 
* 2013-4-12 下午04:15:27 By niuqinghua
*/ 
package com.jason.vo;


import java.util.Date;

/**
 * 基本
 */
public abstract class BaseVo extends IdEntity {
	
	/**创建人*/
	protected String createdBy;
	
	/**创建时间*/
	protected Date createdTime;
	
	/**修改人*/
	protected String modifiedBy;
	
	/**修改时间*/
	protected Date modifiedTime;
	
	/**状态*/
	protected Integer status;

	/**
	 * 自定义toString方法
	 */
	public abstract String toString();

	/**
	 * @Description 获取创建人
	 * @return createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @Description 设置创建人
	 * @param createdBy 
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @Description 获取创建时间
	 * @return createdTime
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * @Description 设置创建时间
	 * @param createdTime 
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * @Description 获取修改人
	 * @return modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @Description 设置修改人
	 * @param modifiedBy 
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @Description 获取修改时间
	 * @return modifiedTime
	 */
	public Date getModifiedTime() {
		return modifiedTime;
	}

	/**
	 * @Description 设置修改时间
	 * @param modifiedTime 
	 */
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	/**
	 * @Description get status
	 * @return status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @Description set status
	 * @param status 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

}
