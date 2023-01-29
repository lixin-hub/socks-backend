package com.lx.fileservice.controller;

import com.lx.fileservice.minio.MinioTemplate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author LIXIN
 * @description 负责对外暴露文件上传，下载接口
 * @date 2022/11/11 9:49
 */
@Slf4j
@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    MinioTemplate minioTemplate;

    @PostMapping("upload/{bucketName}")
    @ResponseBody
    public Object upload(@RequestBody MultipartFile file, @PathVariable("bucketName") String bucketName, HttpServletRequest request) throws IOException {
        return minioTemplate.putObject(file, bucketName, file.getOriginalFilename());
    }

    @GetMapping("preview/{bucket}/**")
    public Object presigned(@PathVariable("bucket") String bucket, HttpServletRequest request) {
        // 获取完整的路径
        String uri = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        // 获取映射的路径
        String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        // 截取带“/”的参数
        String customPath = new AntPathMatcher().extractPathWithinPattern(pattern, uri);
        log.info(customPath);
        return minioTemplate.getPresignedObjectUrl(bucket, customPath);
    }

    //文件下载
    // TODO: 2023/1/29 这里没有进行压缩，感觉下载太慢了
    @SneakyThrows
    @GetMapping("download/{bucket}/**")
    public void downlowd(@PathVariable("bucket") String bucket, HttpServletRequest request, HttpServletResponse response) {
        // 获取完整的路径
        String uri = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        // 获取映射的路径
        String pattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        // 截取带“/”的参数
        String customPath = new AntPathMatcher().extractPathWithinPattern(pattern, uri);
        InputStream is = minioTemplate.getObject(bucket, customPath);
        OutputStream os = response.getOutputStream();
        byte[] bytes;
        int len = -1;
        String fileName = customPath.substring(customPath.lastIndexOf("/"));
        // 全文件类型
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setHeader("Accept-Ranges", "bytes");
        try {
            bytes = new byte[2 * 1024];
            while ((len = is.read(bytes, 0, bytes.length)) != -1) {
                os.write(bytes, 0, len);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();//关闭BufferedOutputStream输出流
            }
        }
    }
}
