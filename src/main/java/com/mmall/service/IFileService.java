package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Xin on 5/9/2017.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
