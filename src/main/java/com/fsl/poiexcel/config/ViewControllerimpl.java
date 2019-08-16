package com.fsl.poiexcel.config;

import com.fsl.poiexcel.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: chenrj
 * @Date: 2019/7/23 10:59
 * @Description:
 */
@Configuration
public class ViewControllerimpl implements WebMvcConfigurer {
    //第一种实现方法：编写addViewControllers方法
     @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //将login.html映射到路径urlpath为："/"上
        registry.addViewController("/").setViewName("login");
    }

    //第二种实现方法：添加WebMvcConfigurer组件
     @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        WebMvcConfigurer adapter = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                //将login.html映射到路径urlpath为："/"上
                registry.addViewController("/").setViewName("index");
                registry.addViewController("/index.html").setViewName("index");
                registry.addViewController("/main.html").setViewName("main");
            }

            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //super.addInterceptors(registry);
                //静态资源；  *.css , *.js
                //SpringBoot已经做好了静态资源映射
                //addPathPatterns 添加拦截路径
                //excludePathPatterns排除拦截路径
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/index.html","/demo.html","/","/user/*","/static/**","/message/*","/project/*","/process/*","/projectProCess/*");
            }

            //https://blog.csdn.net/nanshenjiang/article/details/81416654
            //springboot2.0中使用拦截器会将静态资源文件夹中拦截
            //https://blog.csdn.net/wangfuxu14/article/details/80670648
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
                WebMvcConfigurer.super.addResourceHandlers(registry);
            }

        };
        return adapter;
    }
}


