package com.chanzor.entity;

import java.io.Serializable;

public class AuthInfo implements Serializable {
	private static final long serialVersionUID = -6752477072357658500L;
	
	private Integer id;
	private String company;//公司名称
	private String companyAddress;//公司地址
	private String organizationNo;//组织机构号
	private String organizationImage;//组织机构证件(image)
	private String taxpayerNo;//税务登记号
	private String taxpayerImage;//税务登记证件(image)
	private String registeredNo;//营业执照号
	private String registeredImage;//营业执照证件
	private String legalRepresentative;//法定代表人
	private String contact;//公司电话
	private Integer userId;
	private Integer status =0;//审核状态   0:未认证,1:待审核,2:认证通过,3:认证驳回
	
	private String idcardImage;//经办人身份证件(image)
	private Page page;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return the companyAddress
	 */
	public String getCompanyAddress() {
		return companyAddress;
	}
	/**
	 * @param companyAddress the companyAddress to set
	 */
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	/**
	 * @return the organizationNo
	 */
	public String getOrganizationNo() {
		return organizationNo;
	}
	/**
	 * @param organizationNo the organizationNo to set
	 */
	public void setOrganizationNo(String organizationNo) {
		this.organizationNo = organizationNo;
	}
	/**
	 * @return the organizationImage
	 */
	public String getOrganizationImage() {
		return organizationImage;
	}
	/**
	 * @param organizationImage the organizationImage to set
	 */
	public void setOrganizationImage(String organizationImage) {
		this.organizationImage = organizationImage;
	}
	/**
	 * @return the taxpayerNo
	 */
	public String getTaxpayerNo() {
		return taxpayerNo;
	}
	/**
	 * @param taxpayerNo the taxpayerNo to set
	 */
	public void setTaxpayerNo(String taxpayerNo) {
		this.taxpayerNo = taxpayerNo;
	}
	/**
	 * @return the taxpayerImage
	 */
	public String getTaxpayerImage() {
		return taxpayerImage;
	}
	/**
	 * @param taxpayerImage the taxpayerImage to set
	 */
	public void setTaxpayerImage(String taxpayerImage) {
		this.taxpayerImage = taxpayerImage;
	}
	/**
	 * @return the registeredNo
	 */
	public String getRegisteredNo() {
		return registeredNo;
	}
	/**
	 * @param registeredNo the registeredNo to set
	 */
	public void setRegisteredNo(String registeredNo) {
		this.registeredNo = registeredNo;
	}
	/**
	 * @return the registeredImage
	 */
	public String getRegisteredImage() {
		return registeredImage;
	}
	/**
	 * @param registeredImage the registeredImage to set
	 */
	public void setRegisteredImage(String registeredImage) {
		this.registeredImage = registeredImage;
	}
	/**
	 * 法定代表人
	 * @return the legalRepresentative
	 */
	public String getLegalRepresentative() {
		return legalRepresentative;
	}
	/**
	 * 法定代表人
	 * @param legalRepresentative the legalRepresentative to set
	 */
	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getContact() {
		return contact;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 审核状态   0:未认证,1:待审核,2:认证通过,3:认证驳回
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getIdcardImage() {
		return idcardImage;
	}
	public void setIdcardImage(String idcardImage) {
		this.idcardImage = idcardImage;
	}
	
	
}
