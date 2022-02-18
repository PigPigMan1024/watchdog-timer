package com.ppm.watchdog.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author GGBound (ggbound1024@163.com)
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeOut {


    /**
     * survival time
     */
    long survival() default 3000;

    String message() default "Time out";

    TimeUnit unit() default TimeUnit.MILLISECONDS;


}
