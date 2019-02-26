package com.liupeiqing.http.core.action.param;

import java.util.Map;

/**
 * 保存参数
 * @author liupeqing
 * @date 2019/2/25 16:47
 */
public interface Param extends Map<String,String> {

    /**
     * get string
     * @param param
     * @return
     */
    String getString(String param);

    /**
     * get integer
     * @param param
     * @return
     */
    Integer getInteger(String param);

    /**
     * get Long
     * @param param
     * @return
     */
    Long getLong(String param);

    /**
     * get Double
     * @param param
     * @return
     */
    Double getDouble(String param);

    /**
     * get Float
     * @param param
     * @return
     */
    Float getFloat(String param);

    /**
     * get Boolean
     * @param param
     * @return
     */
    Boolean getBoolean(String param) ;
}
