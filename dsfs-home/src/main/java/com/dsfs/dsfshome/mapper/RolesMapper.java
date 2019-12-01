package com.dsfs.dsfshome.mapper;

import com.dsfs.dsfshome.entity.ProjectType;
import com.dsfs.dsfshome.entity.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by sang on 2017/12/17.
 */
public interface RolesMapper extends Mapper<Role> {
    int addRoles(@Param("roles") String[] roles, @Param("uid") Long uid);

    List<Role> getRolesByUid(Long uid);
}
