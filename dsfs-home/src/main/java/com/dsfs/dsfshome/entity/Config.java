package com.dsfs.dsfshome.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 配置名称
     */
    @Column(name = "config_name")
    private String configName;

    /**
     * 配置code
     */
    @Column(name = "config_code")
    private String configCode;

    /**
     * 图片地址
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 配置内容
     */
    @Column(name = "config_content")
    private String configContent;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by")
    private String createdBy;

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
     * 获取配置名称
     *
     * @return config_name - 配置名称
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * 设置配置名称
     *
     * @param configName 配置名称
     */
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    /**
     * 获取配置code
     *
     * @return config_code - 配置code
     */
    public String getConfigCode() {
        return configCode;
    }

    /**
     * 设置配置code
     *
     * @param configCode 配置code
     */
    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    /**
     * 获取图片地址
     *
     * @return imgUrl - 图片地址
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 设置图片地址
     *
     * @param imgUrl 图片地址
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 获取配置内容
     *
     * @return config_content - 配置内容
     */
    public String getConfigContent() {
        return configContent;
    }

    /**
     * 设置配置内容
     *
     * @param configContent 配置内容
     */
    public void setConfigContent(String configContent) {
        this.configContent = configContent;
    }

    /**
     * @return created_date
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return created_by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}