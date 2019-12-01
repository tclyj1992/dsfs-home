package com.dsfs.dsfshome.mapper;

import com.dsfs.dsfshome.entity.Job;
import com.dsfs.dsfshome.model.JobQo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface JobMapper extends Mapper<Job> {
    List<Job> getJobList(JobQo jobQo);
}