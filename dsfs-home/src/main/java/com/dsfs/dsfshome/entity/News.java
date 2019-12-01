package com.dsfs.dsfshome.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 新闻标题
     */
    private String title;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by")
    private String createdBy;

    /**
     * 新闻分类
     */
    @Column(name = "news_type")
    private Integer newsType;

    /**
     * 状态0草稿，1已发布
     */
    private Integer state;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;

    /**
     * 发布日期
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "publish_date")
    private Date publishDate;

    /**
     * 新闻内容(存富文本)
     */
    private String content;

    @Column(name = "html_content")
    private String htmlContent;

    private String summary;

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
     * 获取新闻标题
     *
     * @return title - 新闻标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置新闻标题
     *
     * @param title 新闻标题
     */
    public void setTitle(String title) {
        this.title = title;
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

    /**
     * 获取新闻分类
     *
     * @return news_type - 新闻分类
     */
    public Integer getNewsType() {
        return newsType;
    }

    /**
     * 设置新闻分类
     *
     * @param newsType 新闻分类
     */
    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }

    /**
     * 获取状态0草稿，1已发布
     *
     * @return state - 状态0草稿，1已发布
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态0草稿，1已发布
     *
     * @param state 状态0草稿，1已发布
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @return updated_date
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return updated_by
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * 获取发布日期
     *
     * @return publish_date - 发布日期
     */
    public Date getPublishDate() {
        return publishDate;
    }

    /**
     * 设置发布日期
     *
     * @param publishDate 发布日期
     */
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * 获取新闻内容(存富文本)
     *
     * @return content - 新闻内容(存富文本)
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置新闻内容(存富文本)
     *
     * @param content 新闻内容(存富文本)
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return html_content
     */
    public String getHtmlContent() {
        return htmlContent;
    }

    /**
     * @param htmlContent
     */
    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    /**
     * @return summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }
}