package com.dsfs.dsfshome.controller;

import com.dsfs.dsfshome.entity.Contact;
import com.dsfs.dsfshome.mapper.ContactMapper;
import com.dsfs.dsfshome.model.JsonResult;
import com.dsfs.dsfshome.util.JsonResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lyj
 * @date 2019/10/13
 **/
@Api(tags = "联系方式")
@RequestMapping("contact")
@RestController
public class ContactController {

    /**
     * 联系方式就一条记录  固定id为1
     */
    private static final int FIXED_CONTACT_ID = 1;

    @Autowired
    private ContactMapper contactMapper;

    @GetMapping("get")
    @ApiOperation(value = "获取联系方式详情",notes = "获取联系方式详情")
    public Contact getContact(){
        return contactMapper.selectByPrimaryKey(FIXED_CONTACT_ID);
    }

    @PostMapping("update")
    @ApiOperation(value = "更新联系方式",notes = "更新联系方式")
    public JsonResult updateContact(Contact contact){
        if(contact.getId()==null || contact.getId()==0){
            contact.setId(FIXED_CONTACT_ID);
        }
        return JsonResultUtil.responseSuccess(contactMapper.updateByPrimaryKey(contact));
    }



}
