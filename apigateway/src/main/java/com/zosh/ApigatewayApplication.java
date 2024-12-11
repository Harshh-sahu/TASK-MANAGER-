package com.zosh;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@SpringBootApplication
@EnableDiscoveryClient
public class ApigatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayApplication.class, args);
    }

    // Define Route Configuration
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("USER-SERVICE", r -> r.path("/auth/**", "/users/**", "/api/users/**", "/")
                        .uri("lb://USER-SERVICE"))
                .route("TASK-SERVICE", r -> r.path("/api/tasks/**", "/tasks/**")
                        .uri("lb://TASK-SERVICE"))
                .route("SUBMISSION-SERVICE", r -> r.path("/api/submissions/**", "/submissions/**")
                        .uri("lb://SUBMISSION-SERVICE"))
                .build();
    }

    // CORS Configuration Bean
    
	@Bean
	public CorsWebFilter corsWebFilter() {
		CorsConfiguration config = new CorsConfiguration();
		
		// Specify allowed origin (frontend's URL)
		config.addAllowedOrigin("https://task-management-omega-dusky.vercel.app"); // Ensure it's added only once
	
		// Allow necessary HTTP methods
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	
		// Allow necessary headers
		// config.addAllowedHeader("Authorization");
		// config.addAllowedHeader("Content-Type");
		// config.addAllowedHeader("Accept");
	
		// Allow credentials in cross-origin requests
		config.setAllowCredentials(true);
	
		// Register configuration for all routes
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
	
		return new CorsWebFilter(source);
	}
	
}
