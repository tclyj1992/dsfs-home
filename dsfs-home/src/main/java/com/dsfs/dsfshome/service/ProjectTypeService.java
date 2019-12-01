package com.dsfs.dsfshome.service;

import com.dsfs.dsfshome.entity.ProjectType;
import com.dsfs.dsfshome.mapper.ProjectTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
}
