package com.lx.fileservice.controller;

import com.lx.fileservice.minio.MinioTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author LIXIN
 * @description 负责对外暴露文件上传，下载接口
 * @date 2022/11/11 9:49
 */
@RestController
@RequestMapping("file-service")
public class FileController {
    @Autowired
    MinioTemplate minioTemplate;

    @PostMapping("upload/{bucketName}")
    @ResponseBody
    public Object upload(@RequestBody MultipartFile file,@PathVariable("bucketName") String bucketName) throws IOException {
        return minioTemplate.putObject(file.getInputStream(), bucketName, file.getOriginalFilename());
    }
    @GetMapping("preview/{bucket}/**")
    public Object presigned(@PathVariable("bucket") String bucket,HttpServletRequest request)
    {
        // 获取完整的路径
        String uri = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        // 获取映射的路径
        String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        // 截取带“/”的参数
        String customPath = new AntPathMatcher().extractPathWithinPattern(pattern,uri);
        System.out.println(customPath);
        return minioTemplate.getPresignedObjectUrl(bucket, customPath);
    }
}
