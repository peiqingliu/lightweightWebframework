package com.liupeiqing.http.core.intercept;

import com.liupeiqing.http.core.action.param.Param;
import com.liupeiqing.http.core.context.HttpContext;

/**
 * 拦截器
 * @author liupeqing
 * @date 2019/2/25 20:13
 */
public abstract class CicadaInterceptor {

    private int order;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * true if the execution chain should proceed with the next interceptor or the handler itself
     * @param context
     * @param param
     * @return
     * @throws Exception
     */
    protected boolean before(HttpContext context, Param param) throws Exception{
        return true;
    }

    protected void after(HttpContext context,Param param) throws Exception{

    }
}
