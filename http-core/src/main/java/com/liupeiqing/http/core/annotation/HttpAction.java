package com.liupeiqing.http.core.annotation;

import java.lang.annotation.*;

/**
 * @author liupeqing
 * @date 2019/2/25 12:16
 */
@Target(ElementType.TYPE)  //作用域 类
@Retention(RetentionPolicy.RUNTIME)  //生命周期
@Documented
public @interface HttpAction {

    String value() default "";
}
