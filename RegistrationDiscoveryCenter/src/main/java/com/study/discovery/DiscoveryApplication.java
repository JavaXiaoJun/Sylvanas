package com.study.discovery;

import com.newegg.ec.base.filter.AuthenticationFIlter;
import com.newegg.ec.base.util.Constant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.Filter;

/**
 * Created by jn50 on 2018/1/27.
 */
@SpringBootApplication
@EnableEurekaServer
@ComponentScan(basePackages={"org.springframework.cloud.netflix.eureka.server","com.newegg.ec.base"})
@MapperScan(basePackages={"com.newegg.ec.base.dao.mysql","com.study.discovery.dao.mysql"})
public class DiscoveryApplication {

    public static void main(String[] args) {
        Constant.APPNAME="API";
        SpringApplication.run(DiscoveryApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(authenticationFilter());
        registration.addUrlPatterns("/rest/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("sessionFilter");
        return registration;
    }

    @Bean(name = "sessionFilter")
    public Filter authenticationFilter() {
        return new AuthenticationFIlter();
    }
}
