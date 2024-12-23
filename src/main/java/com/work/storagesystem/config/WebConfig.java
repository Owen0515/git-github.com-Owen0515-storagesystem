package com.work.storagesystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.work.storagesystem.interceptor.AuthorizationInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
    private ApplicationContext applicationContext;
	
	@Value("${front.url}")
	private String front_url ;
    
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	// 延遲注入，避免循環
        AuthorizationInterceptor authorizationInterceptor = applicationContext.getBean(AuthorizationInterceptor.class);
        registry.addInterceptor(authorizationInterceptor)
		        .addPathPatterns("/api/**") 
		        .excludePathPatterns("/api/auth/**"); 
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(front_url) 
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*") 
                .allowCredentials(true);
    }
}