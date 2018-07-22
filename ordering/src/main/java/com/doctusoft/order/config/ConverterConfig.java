package com.doctusoft.order.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;

import com.doctusoft.order.converter.OrderDtoToOrderConverter;
import com.doctusoft.order.converter.OrderToOrderResourceConverter;

@Configuration
public class ConverterConfig {
	
	@Bean
	@Primary
	public ConversionService getConversionService() {
		ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
		Set<Object> converters = new HashSet<>();
		converters.add(new OrderToOrderResourceConverter());
		converters.add(new OrderDtoToOrderConverter());
		factory.setConverters(converters);
		factory.afterPropertiesSet();
		return factory.getObject();
	}
}
