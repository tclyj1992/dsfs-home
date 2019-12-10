package com.dsfs.dsfshome.controller;

import com.dsfs.dsfshome.entity.Job;
import com.dsfs.dsfshome.entity.Project;
import com.dsfs.dsfshome.model.JsonResult;
import com.dsfs.dsfshome.model.ProjectDto;
import com.dsfs.dsfshome.model.ProjectQo;
import com.dsfs.dsfshome.service.ProjectService;
import com.dsfs.dsfshome.util.JsonResultUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lyj
 * @date 2019/10/13
 **/
@Api(tags = "项目接口")
@RequestMapping("project")
@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("getProjectList")
    @ApiOperation(value = "获取项目列表", notes = "获取项目列表")
    public PageInfo<ProjectDto> getProjectList(ProjectQo projectQo) {
        return projectService.getProjectList(projectQo);
    }

    @GetMapping("findById")
    @ApiOperation(value = "查询项目详情", notes = "查询项目详情")
    public ProjectDto findById(@RequestParam("projectId") Integer projectId) {
        return projectService.findById(projectId);
    }

    @PostMapping("saveProject")
    @ApiOperation(value = "添加项目", notes = "添加项目")
    public JsonResult saveProject(Project project) {
        int result = projectService.saveProject(project);
        if (result == 1) {
            return JsonResultUtil.responseSuccess(project.getId());
        } else {
            return JsonResultUtil.responseFailed("保存失败!");
        }
    }

    @DeleteMapping("deleteById")
    @ApiOperation(value = "删除项目", notes = "删除项目")
    public JsonResult deleteById(@RequestParam("projectId") Long projectId) {
        int result = projectService.deleteById(projectId);
        if (result == 1) {
            return JsonResultUtil.responseSuccess("删除成功!");
        } else {
            return JsonResultUtil.responseFailed("删除失败!");
        }
    }
}
