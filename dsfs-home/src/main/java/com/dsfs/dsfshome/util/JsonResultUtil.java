package com.dsfs.dsfshome.util;

import com.dsfs.dsfshome.model.JsonResult;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonResultUtil {


    public static JsonResult responseSuccess(Object o){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus("success");
        jsonResult.setData(o);
        return jsonResult;
    }


    public static JsonResult responseFailed(String msg){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus("error");
        jsonResult.setMsg(msg);
        return jsonResult;
    }
}
