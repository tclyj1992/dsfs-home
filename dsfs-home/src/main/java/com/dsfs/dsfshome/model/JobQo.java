package com.dsfs.dsfshome.model;

import com.dsfs.dsfshome.entity.Job;
import lombok.Data;

/**
 * @author lyj
 * @date 2019/10/13
 **/
@Data
public class JobQo extends Job {

    Integer pageNum;

    Integer pageSize;
}
