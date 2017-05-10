package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import com.mysql.jdbc.log.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Xin on 5/9/2017.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String upload(MultipartFile file, String path) {
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取扩展名
        String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1);
        //生成上传的文件名
        String uploadFileName = UUID.randomUUID().toString()+"." + fileExtName;

        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);
        //创建目录
        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        //上传文件  到tomcat 目录
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }
        return targetFile.getName();


    }

}
