package com.lx.fileservice.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *@author LIXIN
 *@description 配置文件映射
 *@date 2022/11/11 9:35
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

    /**
     * 是否开启
     */
    Boolean enabled;

    /**
     * 存储对象服务器类型
     */
    OssType type;

    /**
     * OSS 访问端点，集群时需提供统一入口
     */
    String endpoint;

    /**
     * 用户名
     */
    String accessKey;

    /**
     * 密码
     */
    String secretKey;

    /**
     * 默认存储桶名，没有指定时，会放在默认的存储桶
     */
    String defaultBucketName;
}
