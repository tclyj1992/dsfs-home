package com.dsfs.dsfshome.model;

import com.dsfs.dsfshome.entity.Project;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDto extends Project {
    /**
     * 项目分类名称
     */
    private String projectTypeName;
    /**
     * 项目分类id，有层级，需要返回一个list
     */
    private List<Integer> projectType;
    /**
     * 项目分类parentId
     */
    private Integer projectTypeParentId;
}
