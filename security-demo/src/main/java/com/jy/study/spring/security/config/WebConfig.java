package com.jy.study.spring.security.config;

import com.jy.study.spring.security.filter.ThirdPartyFilter;
import com.jy.study.spring.security.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {


    @Autowired
    private TimeInterceptor timeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(timeInterceptor);
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        //用来配置异步请求拦截器
        configurer.registerCallableInterceptors();
        configurer.registerDeferredResultInterceptors();
        //配置异步线程池,默认spring是不会复用已创建的线程
        //configurer.setTaskExecutor();
    }

    //    @Bean
    public FilterRegistrationBean thirdPartyFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        ThirdPartyFilter thirdPartyFilter = new ThirdPartyFilter();
        registrationBean.setFilter(thirdPartyFilter);
        List<String> urlList = new ArrayList<>();
        urlList.add("/*");
        registrationBean.setUrlPatterns(urlList);
        return registrationBean;
    }

}
