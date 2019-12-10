package com.dsfs.dsfshome.controller;

import com.dsfs.dsfshome.entity.User;
import com.dsfs.dsfshome.exception.ServiceException;
import com.dsfs.dsfshome.model.JsonResult;
import com.dsfs.dsfshome.model.LoginQo;
import com.dsfs.dsfshome.service.UserService;
import com.dsfs.dsfshome.util.JsonResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "用户登录")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录",notes = "用户登录")
    public JsonResult login(LoginQo param){
        String username = param.getUsername();
        String password = param.getPassword();
        try {
            User user = userService.login(username,password);
            return JsonResultUtil.responseSuccess(user);
        }catch (ServiceException e){
            return JsonResultUtil.responseFailed(e.getMessage());
        }catch (Exception e){
            return JsonResultUtil.responseFailed("登录失败!");
        }
    }
}
