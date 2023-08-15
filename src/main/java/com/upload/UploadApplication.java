package com.upload;

import com.upload.infraestructure.entity.Load;
import com.upload.infraestructure.entity.LoadData;
import com.upload.infraestructure.entity.LoadDetail;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
public class UploadApplication implements RepositoryRestConfigurer {


	public static void main(String[] args) {
		SpringApplication.run(UploadApplication.class, args);
	}
	
	@Override
	public void configureRepositoryRestConfiguration(final RepositoryRestConfiguration config, final CorsRegistry cors) {
		config.exposeIdsFor(Load.class, LoadDetail.class, LoadData.class);
	}
	
}
