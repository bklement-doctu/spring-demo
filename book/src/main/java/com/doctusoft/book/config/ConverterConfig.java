package com.doctusoft.book.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;

import com.doctusoft.book.converter.BookDtoToBookConverter;
import com.doctusoft.book.converter.BookToBookResourceConverter;

@Configuration
public class ConverterConfig {

	@Bean
	@Primary
	public ConversionService getConversionService() {
		ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
		Set<Object> converters = new HashSet<>();
		converters.add(new BookToBookResourceConverter());
		converters.add(new BookDtoToBookConverter());
		factory.setConverters(converters);
		factory.afterPropertiesSet();
		return factory.getObject();
	}
}
