package com.liupeiqing.http.core;

import com.liupeiqing.http.core.bootstrap.NettyBootStrap;
import com.liupeiqing.http.core.config.CicadaSetting;

/**
 * @author liupeqing
 * @date 2019/2/25 11:24
 */
public final class HttpServer {

    public static void start(Class<?> clazz,String path) throws Exception {
        CicadaSetting.setting(clazz,path) ;
        NettyBootStrap.startCicada();

    }

    public static void start(Class<?> clazz) throws Exception {
        start(clazz,null);
    }
}
