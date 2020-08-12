package com.pt.lib_common.easyhttp.converter;

public interface EasyResponseConverter<T> {
	T convert(String body);
}
