package com.liupeiqing.http.core.action.param;

import java.util.HashMap;

/**
 * @author liupeqing
 * @date 2019/2/25 16:49
 */
public class ParamMap extends HashMap<String,String> implements Param {

    @Override
    public String getString(String param) {
        return super.get(param).toString();
    }

    @Override
    public Integer getInteger(String param) {
        return Integer.parseInt(super.get(param).toString());
    }

    @Override
    public Long getLong(String param) {
        return Long.parseLong(super.get(param).toString());
    }

    @Override
    public Double getDouble(String param) {
        return Double.valueOf(super.get(param));
    }

    @Override
    public Float getFloat(String param) {
        return Float.valueOf(super.get(param));
    }

    @Override
    public Boolean getBoolean(String param) {
        return Boolean.valueOf(super.get(param));
    }
}
