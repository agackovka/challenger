package org.challenger.challenger.infrastructure.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObjectConverter {

	private final GenericConversionService conversionService;

	public <T> T convert(Object source, Class<T> to) {
		return conversionService.convert(source, to);
	}

}
