package com.liupeiqing.http.core.action.req;

import com.liupeiqing.http.core.constant.HttpConstant;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求实现类
 * @author liupeqing
 * @date 2019/2/25 13:57
 */
public class CicadaHttpRequest implements CicadaRequest {

    private String method;
    private String url;
    private String clientAddress;

    private Map<String,Cookie> cookies = new HashMap<>(8);
    private Map<String,String> headers = new HashMap<>(8);

    private CicadaHttpRequest(){}

    /**
     * init方法
     * @return
     */
    public static CicadaHttpRequest init(DefaultHttpRequest httpRequest){
        CicadaHttpRequest  request = new CicadaHttpRequest();
        request.method = httpRequest.method().name();
        request.url = httpRequest.uri();

        //build headers
        buildHeaders(httpRequest,request);

        //init cookies
        initCookies(request);

        return request;

    }

    /**
     * 创建头部信息
     */
    private static void buildHeaders(DefaultHttpRequest httpRequest,CicadaHttpRequest request){
        for (Map.Entry<String,String> entry : httpRequest.headers().entries()){
            request.headers.put(entry.getKey(),entry.getValue());
        }
    }

    /**
     * 初始化cookie
     * @param request
     */
    private static void initCookies(CicadaHttpRequest request){
        for (Map.Entry<String,String> entry : request.headers.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            //遍历头部
            if (!key.equals(HttpConstant.ContentType.COOKIE)){
                continue;
            }
            for (io.netty.handler.codec.http.cookie.Cookie cookie : ServerCookieDecoder.LAX.decode(value)){
                Cookie cicadaCookie = new Cookie() ;
                cicadaCookie.setName(cookie.name());
                cicadaCookie.setValue(cookie.value());
                cicadaCookie.setDomain(cookie.domain());
                cicadaCookie.setMaxAge(cookie.maxAge());
                cicadaCookie.setPath(cookie.path()) ;
                request.cookies.put(cicadaCookie.getName(),cicadaCookie) ;
            }
        }

    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public Cookie getCookie(String key) {
        return this.cookies.get(key);
    }
}
