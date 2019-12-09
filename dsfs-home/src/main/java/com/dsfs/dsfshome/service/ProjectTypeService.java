package com.dsfs.dsfshome.service;

import com.dsfs.dsfshome.common.Constants;
import com.dsfs.dsfshome.entity.ProjectType;
import com.dsfs.dsfshome.mapper.ProjectTypeMapper;
import com.dsfs.dsfshome.model.ProjectDto;
import com.dsfs.dsfshome.model.ProjectTypeQo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author lyj
 * @date 2019/10/13
 **/
@Service
public class ProjectTypeService {

    @Autowired
    private ProjectTypeMapper projectTypeMapper;

    public List<ProjectType> getFirstProjectType() {
        Example example = new Example(ProjectType.class);
        example.createCriteria().andIsNull("parentId");
        return projectTypeMapper.selectByExample(example);
    }

    public List<ProjectType> getProjectTypeByParentId(int parentId) {
        ProjectType projectType = new ProjectType();
        projectType.setParentId(parentId);
        return projectTypeMapper.select(projectType);
    }

    public PageInfo<ProjectType> getProjectTypeList(ProjectTypeQo projectTypeQo) {
        Integer pageNum = projectTypeQo.getPageNum();
        Integer pageSize = projectTypeQo.getPageSize();
        if (pageNum == null || pageSize == null) {
            return null;
        }
        PageHelper.startPage(pageNum, pageSize);
        projectTypeQo.setPageNum(null);
        projectTypeQo.setPageSize(null);
        if(projectTypeQo.getId()!=null && projectTypeQo.getId()<=0){
            projectTypeQo.setId(null);
        }
        List<ProjectType> projectList = projectTypeMapper.getProjectTypeList(projectTypeQo);
        return new PageInfo<ProjectType>(projectList);
    }

    public ProjectType findById(Integer projectId) {
        return projectTypeMapper.selectByPrimaryKey(projectId);
    }

    public int saveProject(ProjectType projectType) {
        if(projectType.getId()==null||projectType.getId()<=0){
            projectType.setId(null);
            return projectTypeMapper.insert(projectType);
        }else {
            return projectTypeMapper.updateByPrimaryKeySelective(projectType);
        }
    }
}
