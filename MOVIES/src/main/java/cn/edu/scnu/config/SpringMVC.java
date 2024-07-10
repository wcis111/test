package cn.edu.scnu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Slf4j
public class SpringMVC extends WebMvcConfigurationSupport{
        @Override
        protected void addResourceHandlers(ResourceHandlerRegistry registry) {
            log.info("开始静态资源映射");
            /*把resources下面的backend进行映射，不这样不行，这样他才能知道这个是静态资源*/
            registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        }
}
