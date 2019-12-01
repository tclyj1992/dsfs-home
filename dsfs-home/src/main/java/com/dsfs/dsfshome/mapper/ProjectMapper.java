package com.dsfs.dsfshome.mapper;

import com.dsfs.dsfshome.entity.Project;
import com.dsfs.dsfshome.model.ProjectDto;
import com.dsfs.dsfshome.model.ProjectQo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProjectMapper extends Mapper<Project> {
    /***
     * 获取项目列表
     * @param projectQo
     * @return
     */
    List<ProjectDto> getProjectList(ProjectQo projectQo);

    /**
     * 获取项目详情
     * @param projectId
     * @return
     */
    ProjectDto findById(Integer projectId);
}