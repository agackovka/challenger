package org.challenger.challenger.infrastructure.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

import javax.annotation.PostConstruct;

public abstract class BaseConverter<T, R> implements Converter<T, R> {

	@Autowired
	public GenericConversionService conversionService;

	@PostConstruct
	public void register() {
		conversionService.addConverter(this);
	}

}
