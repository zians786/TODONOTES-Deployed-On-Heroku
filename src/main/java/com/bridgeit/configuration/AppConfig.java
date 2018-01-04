package com.bridgeit.configuration;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@EnableAsync
@ComponentScan(basePackages = "com.bridgeit")
public class AppConfig extends WebMvcConfigurerAdapter {
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/controller/**").addResourceLocations("/controller/");
	    registry.addResourceHandler("/css/**").addResourceLocations("/css/");
	    registry.addResourceHandler("/directive/**").addResourceLocations("/directive/");
	    registry.addResourceHandler("/script/**").addResourceLocations("/script/");
	    registry.addResourceHandler("/service/**").addResourceLocations("/service/");
	    registry.addResourceHandler("/template/**").addResourceLocations("/template/");
	    registry.addResourceHandler("/bower_components/**").addResourceLocations("/bower_components/");
	    registry.addResourceHandler("/img/**").addResourceLocations("/img/");
	    
	}
	
	   @Bean
	   public ViewResolver viewResolver() {
	       InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	       viewResolver.setViewClass(JstlView.class);
	       viewResolver.setPrefix("");
	       viewResolver.setSuffix(".jsp");
	       return viewResolver;
	   }
}  
    
  
