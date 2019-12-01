package com.dsfs.dsfshome.service;

import com.alibaba.druid.util.StringUtils;
import com.dsfs.dsfshome.common.Constants;
import com.dsfs.dsfshome.entity.Job;
import com.dsfs.dsfshome.mapper.JobMapper;
import com.dsfs.dsfshome.model.JobQo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author lyj
 * @date 2019/10/13
 **/
@Service
public class JobService {
    @Autowired
    private JobMapper jobMapper;

    public PageInfo<Job> getJobList(JobQo jobQo) {
        Integer pageNum = jobQo.getPageNum();
        Integer pageSize = jobQo.getPageSize();
        if (pageNum == null || pageSize == null) {
            return null;
        }
        PageHelper.startPage(pageNum, pageSize);
        jobQo.setPageNum(null);
        jobQo.setPageSize(null);
        if(jobQo.getState()!=null && jobQo.getState()<0){
            jobQo.setState(null);
        }
        List<Job> jobList = jobMapper.getJobList(jobQo);
        return new PageInfo<>(jobList);
    }

    public int saveJob(Job job) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (job.getState() == 1) {
            //设置发表日期
            job.setPublishDate(timestamp);
        }
        if(job.getId()==null||job.getId()<=0){
            job.setId(null);
            job.setCreatedBy(Constants.DEFAULT_USER);
            job.setCreatedDate(timestamp);
            return jobMapper.insert(job);
        }else {
            return jobMapper.updateByPrimaryKeySelective(job);
        }
    }

    public Job findById(Long jobId) {
        return jobMapper.selectByPrimaryKey(jobId);
    }

    public int deleteById(Long jobId) {
        return jobMapper.deleteByPrimaryKey(jobId);
    }
}
