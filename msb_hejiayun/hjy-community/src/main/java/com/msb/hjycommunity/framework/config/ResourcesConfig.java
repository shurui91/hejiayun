package com.msb.hjycommunity.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

/**
 * 通用配置
 */
@Configuration
public class ResourcesConfig {
    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //1,允许任何来源
        config.setAllowedOriginPatterns(Collections.singletonList("*"));

        // 2,允许任何请求头
        //config.addAllowedHeader("*");
        config.addAllowedHeader(CorsConfiguration.ALL);

        // 3,允许任何请求方法
        //config.addAllowedMethod("*");
        config.addAllowedMethod(CorsConfiguration.ALL);

        // 4,允许凭证
        config.setAllowCredentials(true);

        // 对接口配置跨域设置
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
