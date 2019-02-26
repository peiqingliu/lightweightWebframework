package com.liupeiqing.http.core.intercept;

import com.liupeiqing.http.core.action.param.Param;
import com.liupeiqing.http.core.context.HttpContext;

/**
 * @author liupeqing
 * @date 2019/2/25 20:15
 */
public abstract class AbstractCicadaInterceptorAdapter extends CicadaInterceptor{

    @Override
    protected boolean before(HttpContext context, Param param) throws Exception {
        return true;
    }

    @Override
    protected void after(HttpContext context, Param param) throws Exception {
    }
}
