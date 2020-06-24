package com.taotao.manage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manage.pojo.PicUploadResult;
import com.taotao.manage.service.PropertiesService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;


/**
 * Created by ning_ on 2020/6/21.
 */
@Controller
@RequestMapping("/pic")
public class PicUploadController {

    @Autowired
    private PropertiesService propertiesService;


    private static final Logger LOGGER = LoggerFactory.getLogger(PicUploadController.class);

    // JacksonJson中的序列化工具
    private static final ObjectMapper mapper = new ObjectMapper();

    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};

    // produces：指定返回的数据类型。这里是text/plain 普通文本类型
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String upload(@RequestParam("uploadFile") MultipartFile uploadFile) throws Exception {


        // 校验图片格式
        boolean isLegal = false;
        // 判断文件后缀名是否符合
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }

        // 封装Result对象，并且将文件的byte数组放置到result对象中
        PicUploadResult fileUploadResult = new PicUploadResult();

        // 文件新路径
        String filePath = getFilePath(uploadFile.getOriginalFilename());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Pic file upload .[{}] to [{}] .", uploadFile.getOriginalFilename(), filePath);
        }

        // 生成图片的绝对引用地址（URL地址）
        String picUrl = StringUtils.replace(StringUtils.substringAfter(filePath, propertiesService.REPOSITORY_PATH), "\\", "/");
        fileUploadResult.setUrl(propertiesService.IMAGE_BASE_URL + picUrl);

        File newFile = new File(filePath);

        // 写文件到磁盘
        uploadFile.transferTo(newFile);

        // 校验图片是否合法
        isLegal = false;
        try {
            BufferedImage image = ImageIO.read(newFile);
            if (image != null) {
                // 获取图片宽和高
                fileUploadResult.setWidth(image.getWidth() + "");
                fileUploadResult.setHeight(image.getHeight() + "");
                // 一切正常则代表合法
                isLegal = true;
            }
        } catch (IOException e) {
        }

        // 设置返回状态
        fileUploadResult.setError(isLegal ? 0 : 1);

        if (!isLegal) {
            // 不合法，将磁盘上的文件删除
            newFile.delete();
        }
        // 将返回结果对象转为JSON字符串返回
        return mapper.writeValueAsString(fileUploadResult);
    }

    private String getFilePath(String sourceFileName) {
        String baseFolder = "G:\\taotao\\taotao-upload" + File.separator + "images";
        Date nowDate = new Date();
        // 根据年月日：生成yyyy/MM/dd的目录结构
        String fileFolder = baseFolder + File.separator + new DateTime(nowDate).toString("yyyy" + File.separator + "MM" + File.separator + "dd");
        File file = new File(fileFolder);
        // 如果目录不存在，则创建目录
        if (!file.exists()) {
            file.mkdirs();
        }
        // 生成新的文件名
        String fileName = new DateTime(nowDate).toString("yyyyMMddhhmmssSSSS")
                + RandomUtils.nextInt(100, 9999) + "." + StringUtils.substringAfterLast(sourceFileName, ".");
        // 返回文件的全路径
        return fileFolder + File.separator + fileName;
    }

}














