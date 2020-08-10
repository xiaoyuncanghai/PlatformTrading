package com.pt.lib_common.http.converter;

public interface EasyResponseConverter<T> {
	T convert(String body);
}
