package com.liupeiqing.http.ioc;

import com.liupeiqing.http.base.bean.HttpBeanFactory;
import com.liupeiqing.http.base.log.LoggerBuilder;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liupeqing
 * @date 2019/2/25 11:09
 */
public class HttpIoc implements HttpBeanFactory {
    private final static Logger LOGGER = LoggerBuilder.getLogger(HttpIoc.class);

    private static Map<String,Object> beans = new HashMap<>(16);
    @Override
    public void register(Object obj) {
        beans.put(obj.getClass().getName(),obj);

    }

    @Override
    public Object getBean(String name) throws Exception {
        return  beans.get(name);
    }

    @Override
    public void releaseBean() {
        beans = null;
        LOGGER .info("release all bean success.");

    }
}
