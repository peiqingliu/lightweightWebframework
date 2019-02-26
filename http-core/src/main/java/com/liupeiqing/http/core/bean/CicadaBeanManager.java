package com.liupeiqing.http.core.bean;

import com.liupeiqing.http.base.bean.HttpBeanFactory;
import com.liupeiqing.http.core.reflect.ClassScanner;

import java.util.Map;

/**
 * 类管理器 单例模式
 * @author liupeqing
 * @date 2019/2/25 16:56
 */
public class CicadaBeanManager {
    private static volatile CicadaBeanManager cicadaBeanManager;
    private static HttpBeanFactory httpBeanFactory ;
    private CicadaBeanManager(){}

    public static CicadaBeanManager getInstance(){
        if (cicadaBeanManager == null){
            synchronized (CicadaBeanManager.class){
                if (cicadaBeanManager == null){
                    cicadaBeanManager = new CicadaBeanManager();
                }
            }
        }
        return cicadaBeanManager;
    }

    /**
     * init route bean factory
     *
     * @param packageName
     * @throws Exception
     */
    public void init(String packageName) throws Exception {
        Map<String,Class<?>> cicadaAction = ClassScanner.getCicadaAction(packageName);
        Class<?> bean = ClassScanner.getCustomRouteBean();
        httpBeanFactory = (HttpBeanFactory) bean.newInstance();
        for (Map.Entry<String,Class<?>> classEntry : cicadaAction.entrySet()){
            Object instance = classEntry.getValue().newInstance();
            httpBeanFactory.register(instance);
        }
    }


    /**
     * 获取bean
     * @param name
     * @return
     * @throws Exception
     */
    public Object getBean(String name) throws Exception{
        return httpBeanFactory.getBean(name);
    }

    public void releaseBean(){
        httpBeanFactory.releaseBean();
    }
}
