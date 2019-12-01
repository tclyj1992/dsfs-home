package com.dsfs.dsfshome.service;

import com.dsfs.dsfshome.common.Constants;
import com.dsfs.dsfshome.entity.Config;
import com.dsfs.dsfshome.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lyj
 * @date 2019/10/13
 **/
@Service
public class ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    public List<Config> getConfigByConfigCode(String configCode) {
        Config config = new Config();
        config.setConfigCode(configCode);
        return configMapper.select(config);
    }

    public int saveConfig(Config config) {
        if(config.getId()==null||config.getId()<=0){
            config.setCreatedDate(new Date());
            //todo 待修改
            config.setCreatedBy(Constants.DEFAULT_USER);
            configMapper.insertSelective(config);
        }else {
            configMapper.updateByPrimaryKeySelective(config);
        }
        return config.getId();
    }

    public Config findById(Long configId) {
        return configMapper.selectByPrimaryKey(configId);
    }

    public int deleteById(Long configId) {
        return configMapper.deleteByPrimaryKey(configId);
    }
}
