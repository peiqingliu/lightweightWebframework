package com.liupeiqing.http.core.intercept;

import com.liupeiqing.http.core.action.param.Param;
import com.liupeiqing.http.core.config.AppConfig;
import com.liupeiqing.http.core.context.HttpContext;
import com.liupeiqing.http.core.reflect.ClassScanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author liupeqing
 * @date 2019/2/25 20:17
 */
public class InterceptProcess {

    private InterceptProcess(){}

    private volatile static InterceptProcess process;

    private static List<CicadaInterceptor> interceptors;

    private AppConfig appConfig = AppConfig.getConfig();

    public static InterceptProcess getInterceptProcess(){
        if (process == null){
            synchronized (InterceptProcess.class){
                if (process == null){
                    process = new InterceptProcess();
                }
            }
        }
        return process;
    }

    public void loadInterceptors() throws Exception{
        if (interceptors != null){
            return;
        }else {
            interceptors = new ArrayList<>(10);
            Map<Class<?>,Integer> cicadaInterceptor = ClassScanner.getCicadaInterceptor(appConfig.getRootPackageName());
            for (Map.Entry<Class<?>,Integer> classEntry : cicadaInterceptor.entrySet()){
                Class<?> interceptorClass = classEntry.getKey();
                CicadaInterceptor cicadaInterceptor1 = (CicadaInterceptor) interceptorClass.newInstance();
                cicadaInterceptor1.setOrder(classEntry.getValue());
                interceptors.add(cicadaInterceptor1);
            }
           // Collections.sort(interceptors,new OrderComparator());
        }
    }

    /**
     * execute before
     * @param param
     * @return
     * @throws Exception
     */
    public boolean processBefore(Param param) throws Exception{
        for (CicadaInterceptor interceptor : interceptors){
            boolean access = interceptor.before(HttpContext.getContext(),param);
            if (!access){
                return access;
            }
        }
        return true;
    }

    /**
     * execute after
     * @param param
     * @throws Exception
     */
    public void processAfter(Param param) throws Exception{
        for (CicadaInterceptor interceptor : interceptors){
            interceptor.after(HttpContext.getContext(),param);
        }
    }
}
