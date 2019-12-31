package com.psx.prime360ClientService.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class WebAppInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		System.out.println("ServletInitializer::configure(-)");
		return application.sources(WebMvcConfig.class, JPAConfig.class);
	}

}