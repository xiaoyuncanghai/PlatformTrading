package com.pt.lib_common.easyhttp.easyhttprx.converter;

/**
 * Created by yangyang on 2017/3/15.
 */
public interface RxEasyConverter<T> {
    T convert(String body) throws Exception;

    void doNothing();
}
