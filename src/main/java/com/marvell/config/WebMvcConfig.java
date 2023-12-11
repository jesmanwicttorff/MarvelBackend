package com.marvell.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/api/**") // Ajusta según tus necesidades
            .allowedOrigins("http://localhost:3000") // Orígenes permitidos
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
            .allowedHeaders("Origin", "Content-Type", "Accept"); // Cabeceras permitidas
    }
}
