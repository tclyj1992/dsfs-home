package com.dsfs.dsfshome.model;

import com.dsfs.dsfshome.entity.Project;
import lombok.Data;

/**
 * @author lyj
 * @date 2019/10/13
 **/
@Data
public class ProjectQo extends Project{
    Integer pageNum;

    Integer pageSize;
}
