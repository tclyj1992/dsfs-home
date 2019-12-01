package com.dsfs.dsfshome.controller;

import com.dsfs.dsfshome.entity.ProjectType;
import com.dsfs.dsfshome.service.ProjectTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 项目分类
 *
 * @author lyj
 * @date 2019/10/13
 **/
@Api(tags = "项目类别接口")
@RequestMapping("projectType")
@RestController
public class ProjectTypeController {

    @Autowired
    private ProjectTypeService projectTypeService;

    @GetMapping("getFirstProjectType")
    @ApiOperation(value = "获取第一次项目类别", notes = "获取第一次项目类别")
    public List<ProjectType> getFirstProjectType() {
        return projectTypeService.getFirstProjectType();
    }

    @GetMapping("getProjectTypeByParentId")
    @ApiOperation(value = "根据父级id获取项目类别列表",notes = "根据父级id获取项目类别列表")
    public List<ProjectType> getProjectTypeByParentId(@RequestParam("parentId") int parentId){
        return projectTypeService.getProjectTypeByParentId(parentId);
    }
}
