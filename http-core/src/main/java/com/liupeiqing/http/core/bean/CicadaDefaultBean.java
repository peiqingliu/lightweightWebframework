package com.liupeiqing.http.core.bean;

import com.liupeiqing.http.base.bean.HttpBeanFactory;

/**
 * @author liupeqing
 * @date 2019/2/25 16:54
 */
public class CicadaDefaultBean implements HttpBeanFactory {
    @Override
    public void register(Object obj) {

    }

    /**
     * 根据提供的类名称获取bean
     * @param name
     * @return
     * @throws Exception
     */
    @Override
    public Object getBean(String name) throws Exception {
        Class<?> clazz = Class.forName(name);
        Object obj = clazz.newInstance();
        return obj;
    }

    @Override
    public void releaseBean() {

    }
}
