package org.gomsv.middleware;

import org.gomsv.middleware.interceptor.SimulationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SimulationInterceptor simulationInterceptor;

    public WebConfig(SimulationInterceptor simulationInterceptor) {
        this.simulationInterceptor = simulationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(simulationInterceptor)
                .addPathPatterns("/api/posts/**");
    }
}
