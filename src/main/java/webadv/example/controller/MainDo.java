package webadv.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class MainDo implements WebMvcConfigurer{
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/404").setViewName("404");
        registry.addViewController("/move").setViewName("move");
        registry.addViewController("/courseDesign_add").setViewName("courseDesign_add");
    }
}