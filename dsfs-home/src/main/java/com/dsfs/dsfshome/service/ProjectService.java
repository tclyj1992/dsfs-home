package com.dsfs.dsfshome.service;

import com.dsfs.dsfshome.common.Constants;
import com.dsfs.dsfshome.entity.Job;
import com.dsfs.dsfshome.entity.Project;
import com.dsfs.dsfshome.mapper.ProjectMapper;
import com.dsfs.dsfshome.model.ProjectDto;
import com.dsfs.dsfshome.model.ProjectQo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lyj
 * @date 2019/10/13
 **/
@Service
public class ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    public PageInfo<ProjectDto> getProjectList(ProjectQo projectQo) {
        Integer pageNum = projectQo.getPageNum();
        Integer pageSize = projectQo.getPageSize();
        if (pageNum == null || pageSize == null) {
            return null;
        }
        PageHelper.startPage(pageNum, pageSize);
        projectQo.setPageNum(null);
        projectQo.setPageSize(null);
        if(projectQo.getProjectTypeId()!=null && projectQo.getProjectTypeId()<=0){
            projectQo.setProjectTypeId(null);
        }
        List<ProjectDto> projectList = projectMapper.getProjectList(projectQo);
        return new PageInfo<>(projectList);
    }

    public int saveProject(Project project) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(project.getId()==null||project.getId()<=0){
            project.setId(null);
            project.setCreatedBy(Constants.DEFAULT_USER);
            project.setCreatedDate(timestamp);
            return projectMapper.insert(project);
        }else {
            return projectMapper.updateByPrimaryKeySelective(project);
        }
    }

    public ProjectDto findById(Integer projectId) {
        ProjectDto project = projectMapper.findById(projectId);
        List<Integer> list = new ArrayList<>();
        if(project.getProjectTypeParentId()!=null && project.getProjectTypeParentId()!=0){
            list.add(project.getProjectTypeParentId());
        }
        if(project.getProjectTypeId()!=null && project.getProjectTypeId()!=0){
            list.add(project.getProjectTypeId());
        }

        project.setProjectType(list);
        return project;
    }
}
