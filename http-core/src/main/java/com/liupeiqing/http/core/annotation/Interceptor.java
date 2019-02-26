package com.liupeiqing.http.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解
 * @author liupeqing
 * @date 2019/2/25 12:13
 */
@Target(ElementType.TYPE)  //范围
@Retention(RetentionPolicy.RUNTIME)  //生命周期
@Documented
public @interface Interceptor {
    int order() default 0;  //权重
}
