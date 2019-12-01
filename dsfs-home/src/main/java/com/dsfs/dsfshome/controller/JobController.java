package com.dsfs.dsfshome.controller;

import com.dsfs.dsfshome.entity.Job;
import com.dsfs.dsfshome.model.JobQo;
import com.dsfs.dsfshome.model.JsonResult;
import com.dsfs.dsfshome.service.JobService;
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
@Api(tags = "招聘信息接口")
@RequestMapping("job")
@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("getJobList")
    @ApiOperation(value = "获取岗位列表", notes = "获取岗位列表")
    public PageInfo<Job> getJobList(JobQo jobQo) {
        return jobService.getJobList(jobQo);
    }

    @GetMapping("findById")
    @ApiOperation(value = "查询职位详情", notes = "查询职位详情")
    public Job findById(@RequestParam("jobId") Long jobId) {
        return jobService.findById(jobId);
    }

    @DeleteMapping("deleteById")
    @ApiOperation(value = "删除职位", notes = "删除职位")
    public JsonResult deleteById(@RequestParam("jobId") Long jobId) {
        int result = jobService.deleteById(jobId);
        if (result == 1) {
            return JsonResultUtil.responseSuccess("删除成功!");
        } else {
            return JsonResultUtil.responseFailed("删除失败!");
        }
    }

    @PostMapping("saveJob")
    @ApiOperation(value = "添加或更新岗位信息", notes = "添加或更新岗位信息")
    public JsonResult saveJob(Job job) {
        int result = jobService.saveJob(job);
        if (result == 1) {
            return JsonResultUtil.responseSuccess(job.getId());
        } else {
            return JsonResultUtil.responseFailed(job.getState() == 0 ? "职位保存失败!" : "职位发表失败!");
        }
    }

}
