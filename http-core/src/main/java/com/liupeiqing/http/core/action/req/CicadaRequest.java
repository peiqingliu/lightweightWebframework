package com.liupeiqing.http.core.action.req;


/**
 * 请求接口
 * @author liupeqing
 * @date 2019/2/25 13:53
 */
public interface CicadaRequest {

    /**
     * 获取请求的方法名
     * @return
     */
    String getMethod();

    /**
     * 获取请求的url
     * @return
     */
    String getUrl();

    Cookie getCookie(String key);
}
