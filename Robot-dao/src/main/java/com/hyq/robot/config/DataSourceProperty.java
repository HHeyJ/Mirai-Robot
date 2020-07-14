package com.hyq.robot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author nanke
 * @date 2020/4/4 下午2:31
 */
@Data
@Component
@ConfigurationProperties(prefix = "mysql")
public class DataSourceProperty {

    /**
     * 连接
     */
    private String url;
    /**
     * 账号
     */
    private String name;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 驱动类
     */
    private String driverClass;
}
