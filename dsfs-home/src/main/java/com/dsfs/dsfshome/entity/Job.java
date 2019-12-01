package com.dsfs.dsfshome.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 岗位名称
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * 需求人数
     */
    @Column(name = "need_amount")
    private Integer needAmount;

    @Column(name = "job_duty")
    private String jobDuty;

    @Column(name = "job_requirements")
    private String jobRequirements;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "publish_date")
    private Date publishDate;

    /**
     * 状态0草稿，1已发布
     */
    private Integer state;

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
     * 获取岗位名称
     *
     * @return job_name - 岗位名称
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 设置岗位名称
     *
     * @param jobName 岗位名称
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * 获取需求人数
     *
     * @return need_amount - 需求人数
     */
    public Integer getNeedAmount() {
        return needAmount;
    }

    /**
     * 设置需求人数
     *
     * @param needAmount 需求人数
     */
    public void setNeedAmount(Integer needAmount) {
        this.needAmount = needAmount;
    }

    /**
     * @return job_duty
     */
    public String getJobDuty() {
        return jobDuty;
    }

    /**
     * @param jobDuty
     */
    public void setJobDuty(String jobDuty) {
        this.jobDuty = jobDuty;
    }

    /**
     * @return job_requirements
     */
    public String getJobRequirements() {
        return jobRequirements;
    }

    /**
     * @param jobRequirements
     */
    public void setJobRequirements(String jobRequirements) {
        this.jobRequirements = jobRequirements;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}