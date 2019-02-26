package com.liupeiqing.http.core.utils;

import com.liupeiqing.http.core.config.AppConfig;

/**
 * @author liupeqing
 * @date 2019/2/25 11:35
 */
public class PathUtil {

    /**
     *
     * 得到根路径
     * @param path /cicada-example/demoAction
     * @return cicada-example
     */
    public static String getRootPath(String path){
        return "/" + path.split("/")[1];
    }

    /**
     * 得到action路径 相当于第二级的路径
     * @param path /cicada-example/demoAction
     * @return demoAction
     */
    public static String getActionPath(String path){
        return path.split("/")[2];
    }

    /**
     * 获取路由路径
     * @param path  /cicada-example/routeAction/getUser
     * @return  getUser
     */
    public static String getRoutePath(String path){
        AppConfig instance = AppConfig.getConfig();
        return path.replace(instance.getRootPackageName(),"");
    }
}
