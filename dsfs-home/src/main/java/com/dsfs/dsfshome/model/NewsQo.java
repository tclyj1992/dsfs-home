package com.dsfs.dsfshome.model;

import com.dsfs.dsfshome.entity.News;
import lombok.Data;

/**
 * @author lyj
 * @date 2019/10/13
 **/
@Data
public class NewsQo extends News {

    Integer pageNum;

    Integer pageSize;
}
