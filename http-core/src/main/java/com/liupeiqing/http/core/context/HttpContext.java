package com.liupeiqing.http.core.context;

import com.alibaba.fastjson.JSON;
import com.liupeiqing.http.core.action.req.CicadaRequest;
import com.liupeiqing.http.core.action.res.CicadaResponse;
import com.liupeiqing.http.core.action.res.WorkRes;
import com.liupeiqing.http.core.constant.HttpConstant;
import com.liupeiqing.http.core.thread.ThreadLocalHolder;

/**
 * 容器
 * @author liupeqing
 * @date 2019/2/25 13:51
 */
public class HttpContext {

    /**
     * current thread request
     */
    private CicadaRequest request;

    /**
     * current thread response
     */
    private CicadaResponse response ;

    public HttpContext(CicadaRequest request,CicadaResponse response){
        this.request = request;
        this.response = response;
    }

    /**
     * response json message
     */
    public void json(WorkRes workRes){
        HttpContext.getResponse().setContentType(HttpConstant.ContentType.JSON);
        HttpContext.getResponse().setHttpContent(JSON.toJSONString(workRes));
    }

    public void text(String text){
        HttpContext.getResponse().setContentType(HttpConstant.ContentType.TEXT);
        HttpContext.getResponse().setHttpContent(text);
    }

    public static void html(String html){
        HttpContext.getResponse().setContentType(HttpConstant.ContentType.HTML);
        HttpContext.getResponse().setHttpContent(html);
    }

    public static CicadaRequest getRequest(){
        return HttpContext.getContext().request;
    }

    public CicadaRequest request(){
        return HttpContext.getContext().request ;
    }

    public static CicadaResponse getResponse(){
        return HttpContext.getContext().response;
    }


    public static void setContext(HttpContext context){
        ThreadLocalHolder.setHttpContext(context);
    }


    public static void removeContext(){
        ThreadLocalHolder.removeHttpContext();
    }

    public static HttpContext getContext(){
        return ThreadLocalHolder.getCicadaContext();
    }


}
