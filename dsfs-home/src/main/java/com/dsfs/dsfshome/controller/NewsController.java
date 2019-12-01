package com.dsfs.dsfshome.controller;

import com.dsfs.dsfshome.entity.News;
import com.dsfs.dsfshome.model.JsonResult;
import com.dsfs.dsfshome.model.NewsDto;
import com.dsfs.dsfshome.model.NewsQo;
import com.dsfs.dsfshome.service.NewsService;
import com.dsfs.dsfshome.util.JsonResultUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 新闻接口
 *
 * @author lyj
 * @date 2019/10/12
 **/
@RestController
@RequestMapping("news")
@Api(tags = "新闻接口类")
public class NewsController {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public JsonResult addNews(News news) {
        int result = newsService.addNews(news);
        if (result == 1) {
            return JsonResultUtil.responseSuccess(news.getId());
        } else {
            return JsonResultUtil.responseFailed(news.getState() == 0 ? "文章保存失败!" : "文章发表失败!");
        }
    }


    @GetMapping("findAllNews")
    @ApiOperation(value = "获取新闻列表", notes = "获取新闻列表")
    public PageInfo<NewsDto> findAllNews(NewsQo newsQo) {
        return newsService.findAllNews(newsQo);
    }

    @GetMapping("findById")
    @ApiOperation(value = "查询新闻详情", notes = "查询新闻详情")
    public News findById(@RequestParam("newsId") Long newsId) {
        return newsService.findById(newsId);
    }

    @DeleteMapping("deleteById")
    @ApiOperation(value = "删除职位", notes = "删除职位")
    public JsonResult deleteById(@RequestParam("newsId") Long newsId) {
        int result = newsService.deleteById(newsId);
        if (result == 1) {
            return JsonResultUtil.responseSuccess("删除成功!");
        } else {
            return JsonResultUtil.responseFailed("删除失败!");
        }
    }

    /**
     * 上传图片
     *
     * @return 返回值为图片的地址
     */
    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
    public JsonResult uploadImg(HttpServletRequest req, MultipartFile file) {
        StringBuffer url = new StringBuffer();
        String filePath = "/blogimg/" + sdf.format(new Date());
        String imgFolderPath = req.getServletContext().getRealPath(filePath);
        File imgFolder = new File(imgFolderPath);
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }
        url.append(req.getScheme())
                .append("://")
                .append(req.getServerName())
                .append(":")
                .append(req.getServerPort())
                .append(req.getContextPath())
                .append(filePath);
        String imgName = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(file.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
            url.append("/").append(imgName);
            return JsonResultUtil.responseSuccess(url.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonResultUtil.responseFailed("上传失败!");
    }


}
