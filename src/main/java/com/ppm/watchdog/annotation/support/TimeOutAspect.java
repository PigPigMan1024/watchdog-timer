package com.ppm.watchdog.annotation.support;

import com.ppm.watchdog.Timer;
import com.ppm.watchdog.annotation.TimeOut;
import java.util.concurrent.TimeUnit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author GGBound (ggbound1024@163.com)
 */
@Aspect
public class TimeOutAspect {


    @Around("@annotation(timeOut)")
    public Object around(ProceedingJoinPoint joinPoint, TimeOut timeOut) throws Throwable {
        final long survival = TimeUnit.MILLISECONDS.convert(timeOut.survival(), timeOut.unit());

        try (Timer timer = new Timer(survival, Thread.currentThread(), timeOut.message())) {
            System.out.println("-------------------------------------------------------------------------");
            timer.start();
            return joinPoint.proceed();
        } catch (InterruptedException interrupted) {
            throw new IllegalStateException(timeOut.message());
        }
    }
}
