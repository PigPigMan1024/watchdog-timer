package com.ppm.watchdog;

/**
 * @author GGBound (ggbound1024@163.com)
 */
public class Demo {


    public static void main(String[] args) {
        long mainThreadAliveTime = 1000, survivalTime = 500;
        long lastTime = System.currentTimeMillis() + mainThreadAliveTime;

        try (Timer watchdog = new Timer(survivalTime, Thread.currentThread(), "Time out")) {
            watchdog.start();
            while (System.currentTimeMillis() <= lastTime) {

            }
        }
    }
}
