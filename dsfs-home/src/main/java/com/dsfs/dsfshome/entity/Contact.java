package com.dsfs.dsfshome.entity;

import javax.persistence.*;

public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 公司名称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 邮政编码
     */
    private String zipcode;

    /**
     * 电话
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 传真
     */
    @Column(name = "fax_number")
    private String faxNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址图片
     */
    private String imgUrl;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取公司名称
     *
     * @return company_name - 公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置公司名称
     *
     * @param companyName 公司名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取公司地址
     *
     * @return address - 公司地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置公司地址
     *
     * @param address 公司地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取邮政编码
     *
     * @return zipcode - 邮政编码
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * 设置邮政编码
     *
     * @param zipcode 邮政编码
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * 获取电话
     *
     * @return phone_number - 电话
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置电话
     *
     * @param phoneNumber 电话
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 获取传真
     *
     * @return fax_number - 传真
     */
    public String getFaxNumber() {
        return faxNumber;
    }

    /**
     * 设置传真
     *
     * @param faxNumber 传真
     */
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}