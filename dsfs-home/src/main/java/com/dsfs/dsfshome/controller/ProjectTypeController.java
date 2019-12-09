package com.dsfs.dsfshome.controller;

import com.dsfs.dsfshome.entity.Project;
import com.dsfs.dsfshome.entity.ProjectType;
import com.dsfs.dsfshome.model.JsonResult;
import com.dsfs.dsfshome.model.ProjectDto;
import com.dsfs.dsfshome.model.ProjectTypeQo;
import com.dsfs.dsfshome.service.ProjectTypeService;
import com.dsfs.dsfshome.util.JsonResultUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("getProjectTypeList")
    @ApiOperation(value = "获取项目分类列表", notes = "获取项目分类列表")
    public PageInfo<ProjectType> getProjectTypeList(ProjectTypeQo projectTypeQo){
        return projectTypeService.getProjectTypeList(projectTypeQo);
    }

    @GetMapping("findById")
    @ApiOperation(value = "查询分类详情", notes = "查询分类详情")
    public ProjectType findById(@RequestParam("projectId") Integer projectId) {
        return projectTypeService.findById(projectId);
    }

    @PostMapping("saveProject")
    @ApiOperation(value = "添加项目", notes = "添加项目")
    public JsonResult saveProject(ProjectType projectType) {
        int result = projectTypeService.saveProject(projectType);
        if (result == 1) {
            return JsonResultUtil.responseSuccess(projectType.getId());
        } else {
            return JsonResultUtil.responseFailed("保存失败!");
        }
    }

}
