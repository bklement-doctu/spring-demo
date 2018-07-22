package com.doctusoft.inventory.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;

import com.doctusoft.inventory.converter.InventoryToInventoryResourceConverter;

@Configuration
public class ConverterConfig {

	@Bean
	@Primary
	public ConversionService getConversionService() {
		ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
		Set<Object> converters = new HashSet<>();
		converters.add(new InventoryToInventoryResourceConverter());
		factory.setConverters(converters);
		factory.afterPropertiesSet();
		return factory.getObject();
	}
}
