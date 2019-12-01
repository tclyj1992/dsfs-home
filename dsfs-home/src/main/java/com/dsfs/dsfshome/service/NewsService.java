package com.dsfs.dsfshome.service;

import com.dsfs.dsfshome.common.Constants;
import com.dsfs.dsfshome.entity.News;
import com.dsfs.dsfshome.entity.Project;
import com.dsfs.dsfshome.mapper.NewsExampleMapper;
import com.dsfs.dsfshome.mapper.NewsMapper;
import com.dsfs.dsfshome.model.NewsDto;
import com.dsfs.dsfshome.model.NewsQo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author lyj
 * @date 2019/10/13
 **/
@Service
public class NewsService {

    @Autowired
    private NewsExampleMapper newsExampleMapper;

    public PageInfo<NewsDto> findAllNews(NewsQo newsQo) {
        //todo 排序
        Integer pageNum = newsQo.getPageNum();
        Integer pageSize = newsQo.getPageSize();
        if (pageNum == null || pageSize == null) {
            return null;
        }
        PageHelper.startPage(pageNum, pageSize);
        newsQo.setPageNum(null);
        newsQo.setPageSize(null);
        //小于0查询所有状态的数据
        if(newsQo.getState()!=null){
            newsQo.setState(newsQo.getState()<0?null:newsQo.getState());
        }
        List<NewsDto> newsList = newsExampleMapper.selectByQo(newsQo);
        return new PageInfo<>(newsList);
    }

    public News findById(Long newsId) {
        return newsExampleMapper.selectByPrimaryKey(newsId);
    }

    public int addNews(News news) {
        //处理文章摘要
        if (news.getSummary() == null || "".equals(news.getSummary())) {
            //直接截取
            String stripHtml = stripHtml(news.getHtmlContent());
            news.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (news.getState() == 1) {
            //设置发表日期
            news.setPublishDate(timestamp);
        }

        if (news.getId() == -1) {
            //添加操作
            news.setCreatedDate(timestamp);
            news.setUpdatedDate(timestamp);
            //设置当前用户
            //article.setUid(Util.getCurrentUser().getId());
            news.setCreatedBy(Constants.DEFAULT_USER);
            news.setId(null);
            int i = newsExampleMapper.insertSelective(news);
            return i;
        } else {
            news.setUpdatedDate(timestamp);
            int i = newsExampleMapper.updateByPrimaryKeySelective(news);
            return i;
        }
    }

    private String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }

    public int deleteById(Long newsId) {
        return newsExampleMapper.deleteByPrimaryKey(newsId);
    }
}
