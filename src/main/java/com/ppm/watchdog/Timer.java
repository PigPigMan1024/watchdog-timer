package com.ppm.watchdog;


/**
 * Watchdog timer
 *
 * @author GGBound (ggbound1024@163.com)
 */
public class Timer implements AutoCloseable {

    private final Thread timerThread, targetThread;

    private final String message;

    /**
     * @param survivalTime countdown time
     * @param targetThread The monitored thread object
     */
    public Timer(long survivalTime, Thread targetThread, String message) {
        this.targetThread = targetThread;
        this.timerThread = genTimerThread(survivalTime);
        this.message = message;
    }


    /**
     * Start timer
     */
    public void start() {
        this.timerThread.start();
    }


    /**
     * Generate a timer thread
     *
     * @param survivalTime countdown time
     * @return TimerThread
     */
    private Thread genTimerThread(final long survivalTime) {
        return new Thread("Timer-Watch-Thread:" + targetThread.getId()) {

            @Override
            public void run() {
                long finalTime = System.currentTimeMillis() + survivalTime;
                while (System.currentTimeMillis() <= finalTime) {
                    if (!targetThread.isAlive()) {
                        return;
                    }
                }

                Timer.this.stop();
            }
        };
    }

    public synchronized void stop() throws WatchdogTimeOutException {

        if (!timerThread.isInterrupted()
                && Thread.currentThread().equals(timerThread)) {
            targetThread.interrupt();
            boolean signal = true;
            System.out.println("TimerThread ---> interrupt targetThread,signal:{}" + signal);
        } else {
            //If timerThread is alive and not marked interrupted, timerThread needs to be marked as interrupted
            System.out.println("TargetThread ---> access to the lock ,TargetThread isInterrupted:{}" + targetThread.isInterrupted());
            if (timerThread.isAlive()
                    && !timerThread.isInterrupted()) {
                timerThread.interrupt();
                System.out.println("TargetThread ---> stop the timerThread");
            }
            //When the target Thread is marked as interrupted,
            //the interrupt flag needs to be removed and an WatchDogTimeOutException is thrown
            if (targetThread.isInterrupted()) {
                boolean ignore = Thread.interrupted();
                throw new WatchdogTimeOutException(message);
            }
        }
    }

    @Override
    public void close() {
        stop();
    }
}
