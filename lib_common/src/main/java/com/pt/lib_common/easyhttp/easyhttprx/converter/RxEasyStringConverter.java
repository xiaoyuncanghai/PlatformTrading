package com.pt.lib_common.easyhttp.easyhttprx.converter;

/**
 * Created by yangyang on 2017/3/15.
 */
public class RxEasyStringConverter implements RxEasyConverter<String> {
    @Override
    public String convert(String body) throws Exception {
        return body;
    }

    @Override
    public void doNothing() {

    }
}
