package com.dsfs.dsfshome.controller.admin;

import com.dsfs.dsfshome.entity.Role;
import com.dsfs.dsfshome.entity.User;
import com.dsfs.dsfshome.model.JsonResult;
import com.dsfs.dsfshome.service.UserService;
import com.dsfs.dsfshome.util.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sang on 2017/12/24.
 */
@RestController
@RequestMapping("/admin")
public class UserManaController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> getUserByNickname(String nickname) {
        return userService.getUserByNickname(nickname);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List<Role> getAllRole() {
        return userService.getAllRole();
    }

    @RequestMapping(value = "/user/enabled", method = RequestMethod.PUT)
    public JsonResult updateUserEnabled(Boolean enabled, Long uid) {
        if (userService.updateUserEnabled(enabled, uid) == 1) {
            return JsonResultUtil.responseSuccess("更新成功!");
        } else {
            return JsonResultUtil.responseFailed("更新失败!");
        }
    }

    @RequestMapping(value = "/user/{uid}", method = RequestMethod.DELETE)
    public JsonResult deleteUserById(@PathVariable Long uid) {
        if (userService.deleteUserById(uid) == 1) {
            return JsonResultUtil.responseSuccess("删除成功!");
        } else {
            return JsonResultUtil.responseFailed("删除失败!");
        }
    }

    @RequestMapping(value = "/user/role", method = RequestMethod.PUT)
    public JsonResult updateUserRoles(Long[] rids, Long id) {
        if (userService.updateUserRoles(rids, id) == rids.length) {
            return JsonResultUtil.responseSuccess("更新成功!");
        } else {
            return JsonResultUtil.responseFailed("更新失败!");
        }
    }
}
