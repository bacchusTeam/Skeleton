package sample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
    viewControllerRegistry.addViewController("/hello").setViewName("hello");
    viewControllerRegistry.addViewController("/login").setViewName("login");
    viewControllerRegistry.addViewController("/user").setViewName("user");
    viewControllerRegistry.addViewController("/admin").setViewName("admin");
  }


//   404 발생에 따른 주석 처리
  /*@Override
  public void addResourceHandlers (ResourceHandlerRegistry resourceHandlerRegistry) {
    resourceHandlerRegistry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }*/

}
