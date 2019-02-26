package com.liupeiqing.http.core.action.res;

import com.liupeiqing.http.core.action.req.Cookie;
import com.liupeiqing.http.core.constant.HttpConstant;
import com.liupeiqing.http.core.exception.HttpException;
import io.netty.handler.codec.http.cookie.DefaultCookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 相应实现类
 * @author liupeqing
 * @date 2019/2/25 14:19
 */
public class CicadaHttpResponse implements CicadaResponse {

    private Map<String,String> headers = new HashMap<>();

    private String contentType;

    private String httpContent;

    private List<io.netty.handler.codec.http.cookie.Cookie> cookies = new ArrayList<>(6);

    private CicadaHttpResponse() {
    }

    /**
     * 相应的初始化方法
     * @return
     */
    public static CicadaHttpResponse init(){
        CicadaHttpResponse  response = new CicadaHttpResponse();
        response.contentType = HttpConstant.ContentType.TEXT;
        return response;

    }

    public void setHeaders(String key,String value){
        this.headers.put(key,value);
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void setHttpContent(String content) {
        httpContent = content;
    }

    @Override
    public String getHttpContent() {
        return httpContent;
    }

    @Override
    public void setCookie(Cookie cookie) {

        if (null == cookie){
            throw new HttpException("cookie is null");
        }
        if (null == cookie.getName()){
            throw new HttpException("cookie.getName() is null");
        }
        if (null == cookie.getValue()){
            throw new HttpException("cookie.getValue() is null");
        }

        DefaultCookie cookie1 = new DefaultCookie(cookie.getName(),cookie.getValue());
        cookie1.setPath("/");
        cookie1.setMaxAge(cookie.getMaxAge());
        cookies.add(cookie1);

    }

    @Override
    public List<io.netty.handler.codec.http.cookie.Cookie> cookies() {
        return cookies;
    }
}
