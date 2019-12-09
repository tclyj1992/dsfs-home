package com.dsfs.dsfshome.model;

import com.dsfs.dsfshome.entity.ProjectType;
import lombok.Data;

@Data
public class ProjectTypeQo extends ProjectType {
    Integer pageNum;

    Integer pageSize;
}
