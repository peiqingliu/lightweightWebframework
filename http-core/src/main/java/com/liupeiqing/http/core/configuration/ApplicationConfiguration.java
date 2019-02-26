package com.liupeiqing.http.core.configuration;

import com.liupeiqing.http.core.constant.HttpConstant;

/**
 * @author liupeqing
 * @date 2019/2/25 13:38
 */
public class ApplicationConfiguration extends AbstractHttpConfiguration {

    public ApplicationConfiguration(){
        super.setPropertiesName(HttpConstant.SystemProperties.APPLICATION_PROPERTIES);
    }
}
