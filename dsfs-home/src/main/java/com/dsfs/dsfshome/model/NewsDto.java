package com.dsfs.dsfshome.model;

import com.dsfs.dsfshome.entity.News;
import lombok.Data;

@Data
public class NewsDto extends News {

    /**
     * 新闻分类名称
     */
    private String newsTypeName;
}
