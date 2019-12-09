package com.dsfs.dsfshome.mapper;

import com.dsfs.dsfshome.entity.ProjectType;
import com.dsfs.dsfshome.model.ProjectTypeQo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProjectTypeMapper extends Mapper<ProjectType> {
    List<ProjectType> getProjectTypeList(ProjectTypeQo projectTypeQo);
}