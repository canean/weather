package com.cloud.weather.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/*
Rest 配置
启动时，RestTemplate会去看classpath中有哪些依赖，本例中是HTTPClient，则RestTemplate
会默认实现HTTPClient，这也是为什么我们没有new一个HTTPClient
 */

@Configuration
public class RestConfiguration {

    @Autowired
    private RestTemplateBuilder builder;

    @Bean
    public RestTemplate restTemplate(){
        return builder.build();
    }


}
