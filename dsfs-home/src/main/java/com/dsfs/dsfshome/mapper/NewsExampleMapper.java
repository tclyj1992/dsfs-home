package com.dsfs.dsfshome.mapper;

import com.dsfs.dsfshome.entity.News;
import com.dsfs.dsfshome.model.NewsDto;
import com.dsfs.dsfshome.model.NewsQo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface NewsExampleMapper extends Mapper<News> {
    /**
     * 查询新闻列表
     *
     * @param newsQo
     * @return
     */
    List<NewsDto> selectByQo(NewsQo newsQo);
}