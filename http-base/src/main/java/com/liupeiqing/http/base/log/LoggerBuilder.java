package com.liupeiqing.http.base.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liupeqing
 * @date 2019/2/25 10:53
 */
public class LoggerBuilder {

    public static Logger getLogger(Class clazz){
        return LoggerFactory.getLogger(clazz);
    }
}
