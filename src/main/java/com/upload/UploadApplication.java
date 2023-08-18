package com.upload;

import com.upload.infraestructure.entity.Load;
import com.upload.infraestructure.entity.LoadData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;

@EnableConfigurationProperties
@SpringBootApplication
@EnableAsync
public class UploadApplication implements RepositoryRestConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(UploadApplication.class, args);
	}
	
	@Override
	public void configureRepositoryRestConfiguration(final RepositoryRestConfiguration config, final CorsRegistry cors) {
		config.exposeIdsFor(Load.class, LoadData.class);
	}

	@Override
	public void configureConversionService(final ConfigurableConversionService conversionService) {
		conversionService.addConverter(String.class, LocalDate.class, new CustomStringToLocalDate());
		RepositoryRestConfigurer.super.configureConversionService(conversionService);

		conversionService.addConverter(String.class, LocalDateTime.class, new CustomStringToLocalDateTime());
		RepositoryRestConfigurer.super.configureConversionService(conversionService);
	}

	private class CustomStringToLocalDate implements Converter<String, LocalDate> {
		@Override
		public LocalDate convert(final String source) {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
			return LocalDate.parse(source, formatter);
		}
	}

	private class CustomStringToLocalDateTime implements Converter<String, LocalDateTime> {
		@Override
		public LocalDateTime convert(final String source) {
			return LocalDateTime.parse(source);
		}
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("GithubLookup-");
		executor.initialize();
		return executor;
	}

}
