package org.example.ServletFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilterConfiguration implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<UriFilter> uriFilterRegistrationBean() {
        FilterRegistrationBean<UriFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UriFilter());
        registrationBean.addUrlPatterns("/Boards");

        return registrationBean;
    }

//    @Bean
//    public FilterRegistrationBean<MethodFilter> methodFilterRegistrationBean() {
//        FilterRegistrationBean<MethodFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new MethodFilter());
//        registrationBean.addUrlPatterns("/stations");
//
//        return registrationBean;
//    }
}