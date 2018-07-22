package com.doctusoft.review.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;

import com.doctusoft.review.converter.ReviewDtoToReviewConverter;
import com.doctusoft.review.converter.ReviewToReviewResourceConverter;

@Configuration
public class ConverterConfig {

	@Bean
	@Primary
	public ConversionService getConversionService() {
		ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
		Set<Object> converters = new HashSet<>();
		converters.add(new ReviewDtoToReviewConverter());
		converters.add(new ReviewToReviewResourceConverter());
		factory.setConverters(converters);
		factory.afterPropertiesSet();
		return factory.getObject();
	}
}
