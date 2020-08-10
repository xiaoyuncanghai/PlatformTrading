package com.pt.lib_common.http.easyhttprx.converter;

import org.json.JSONObject;

/**
 * Created by yangyang on 2017/3/15.
 */
public class RxEasyJsonConverter implements RxEasyConverter<JSONObject> {
    @Override
    public JSONObject convert(String body) throws Exception {
        return new JSONObject(body);
    }

    @Override
    public void doNothing() {

    }
}
