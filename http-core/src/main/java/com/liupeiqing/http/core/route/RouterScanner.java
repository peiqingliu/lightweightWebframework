package com.liupeiqing.http.core.route;

import com.liupeiqing.http.core.annotation.HttpAction;
import com.liupeiqing.http.core.annotation.HttpRoute;
import com.liupeiqing.http.core.config.AppConfig;
import com.liupeiqing.http.core.context.HttpContext;
import com.liupeiqing.http.core.enums.StatusEnum;
import com.liupeiqing.http.core.exception.HttpException;
import com.liupeiqing.http.core.reflect.ClassScanner;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author liupeqing
 * @date 2019/2/25 20:32
 */
public class RouterScanner {

    private static Map<String,Method> routes = null;

    private volatile static RouterScanner routerScanner;

    private AppConfig appConfig = AppConfig.getConfig();

    /**
     * get single Instance
     *
     * @return
     */
    public static RouterScanner getInstance() {
        if (routerScanner == null) {
            synchronized (RouterScanner.class) {
                if (routerScanner == null) {
                    routerScanner = new RouterScanner();
                }
            }
        }
        return routerScanner;
    }

    private RouterScanner() {
    }

    /**
     * get route method
     * @param queryStringDecoder
     * @return
     * @throws Exception
     */
    public Method routeMethod(QueryStringDecoder queryStringDecoder) throws Exception{
        if (routes == null){
            routes = new HashMap<>(16);
            loadRouteMethods(appConfig.getRootPackageName());
        }
        //default response
        boolean defaultResponse = defaultResponse(queryStringDecoder.path());
        if (defaultResponse){
            return null;
        }

        Method method = routes.get(queryStringDecoder.path());

        if (method == null){
            throw new HttpException(StatusEnum.NOT_FOUND);
        }
        return method;
    }

    private boolean defaultResponse(String path){
        if (appConfig.getRootPath().equals(path)){
            HttpContext.getContext().html("<center> Hello Cicada <br/><br/>\" +\n" +
                    " \"Power by <a href='https://github.com/TogetherOS/cicada'>@Cicada</a> </center>");
            return true;
        }
        return false;
    }
    private void loadRouteMethods(String packageName) throws Exception{
        Set<Class<?>> classes = ClassScanner.getClasses(packageName);
        for (Class<?> aClass : classes){
            Method[] declaredMethods = aClass.getMethods();
            for (Method method : declaredMethods){
                HttpRoute annotation = method.getAnnotation(HttpRoute.class);
                if (annotation == null){
                    continue;
                }
                HttpAction cicadaAction = aClass.getAnnotation(HttpAction.class);
                routes.put(appConfig.getRootPath() + "/" + cicadaAction.value() + "/" + annotation.value(),method);
            }
        }
    }
}
