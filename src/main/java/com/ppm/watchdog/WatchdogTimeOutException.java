package com.ppm.watchdog;

/**
 * @author GGBound (ggbound1024@163.com)
 */
public class WatchdogTimeOutException extends RuntimeException {

    public WatchdogTimeOutException(String message) {
        super(message);
    }
}
