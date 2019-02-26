package com.liupeiqing.http.core.action;

import com.liupeiqing.http.core.action.param.Param;
import com.liupeiqing.http.core.context.HttpContext;

/**
 * @author liupeqing
 * @date 2019/2/25 14:56
 */
@Deprecated  //若某类或某方法加上该注解之后，表示此方法或类不再建议使用，调用时也会出现删除线，但并不代表不能用，只是说，不推荐使用，因为还有更好的方法可以调用。
public interface  WorkAction {

    void execute(HttpContext context, Param param)throws Exception;
}
