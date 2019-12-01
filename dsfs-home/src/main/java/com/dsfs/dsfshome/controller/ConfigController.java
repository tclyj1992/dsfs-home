package com.dsfs.dsfshome.controller;

import com.dsfs.dsfshome.entity.Config;
import com.dsfs.dsfshome.entity.Job;
import com.dsfs.dsfshome.model.JsonResult;
import com.dsfs.dsfshome.service.ConfigService;
import com.dsfs.dsfshome.util.JsonResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**l
 * @author lyj
 * @date 2019/10/13
 **/
@Api(tags = "通用配置类")
@RequestMapping("config")
@RestController
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/getConfigByConfigCode")
    @ApiOperation(value = "获取数据字典",notes = "公司简介：companyDesc，领导寄语：leaderWishes，公司团队：companyTeam，新闻分类：newsCategory")
    public List<Config> getConfigByConfigCode(@RequestParam("configCode") String configCode) {
        return configService.getConfigByConfigCode(configCode);
    }

    @GetMapping("findById")
    @ApiOperation(value = "查询配置详情", notes = "查询配置详情")
    public Config findById(@RequestParam("configId") Long configId) {
        return configService.findById(configId);
    }

    @PostMapping("/saveConfig")
    @ApiOperation(value = "保存配置",notes = "保存配置")
    public JsonResult saveConfig(Config config) {
        int i = configService.saveConfig(config);
        return JsonResultUtil.responseSuccess(i);
    }

    @DeleteMapping("deleteById")
    @ApiOperation(value = "保存配置", notes = "保存配置")
    public JsonResult deleteById(@RequestParam("configId") Long configId) {
        int result = configService.deleteById(configId);
        if (result == 1) {
            return JsonResultUtil.responseSuccess("删除成功!");
        } else {
            return JsonResultUtil.responseFailed("删除失败!");
        }
    }
}
