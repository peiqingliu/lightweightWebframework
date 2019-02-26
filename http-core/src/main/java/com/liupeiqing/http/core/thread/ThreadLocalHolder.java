package com.liupeiqing.http.core.thread;

import com.liupeiqing.http.core.context.HttpContext;
import io.netty.util.concurrent.FastThreadLocal;

/**
 * 修改threadLocal
 * @author liupeqing
 * @date 2019/2/25 15:05
 */
public class ThreadLocalHolder {

    /**
     * 存储时间戳
     */
    private final static FastThreadLocal<Long>  LOCAL_TIME = new  FastThreadLocal();

    /**
     * 存储容器
     */
    private final static FastThreadLocal<HttpContext> HTTP_CONTEXT = new FastThreadLocal<>();

    /**
     *
     * @param context
     */
    public static void setHttpContext(HttpContext context){
        HTTP_CONTEXT.set(context);
    }

    /**
     * 删除
     */
    public static void removeHttpContext(){
        HTTP_CONTEXT.remove();
    }

    /**
     * 获取 容器
     * @return
     */
    public static HttpContext getCicadaContext(){
        return HTTP_CONTEXT.get();
    }

    /**
     * 设置时间
     * @param time
     */
    public static void setLocalTime(long time){
        LOCAL_TIME.set(time);
    }

    public static long getLocalTime(){
        Long time = LOCAL_TIME.get();
        LOCAL_TIME.remove();
        return time;
    }
}
