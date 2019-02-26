package com.liupeiqing.http.base.bean;

/**
 * @author liupeqing
 * @date 2019/2/25 10:50
 */
public interface HttpBeanFactory {

    /**
     * 注册bean到factory
     * @param obj
     */
    void register(Object obj);

    /**
     * 从factory获取bean
     * @param name
     * @return
     * @throws Exception
     */
    Object getBean(String name)throws Exception;

    /**
     * release all beans
     */
    void releaseBean() ;
}
