package com.liupeiqing.http.core.annotation;

import java.lang.annotation.*;

/**
 * 路由注解
 * @author liupeqing
 * @date 2019/2/25 12:18
 */
@Target(ElementType.METHOD)  //只能标注在方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpRoute {

    String value() default "";
}
