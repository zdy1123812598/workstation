package com.example.demo.thread;

import java.util.concurrent.Semaphore;

public class Thread5 {

    private static final Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();
                    System.out.println("thread1 start");
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(500);
                        System.out.println("thread1 is working" + i);
                    }
                    System.out.println("thread1 end");
                    semaphore.release();
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();
                    System.out.println("thread2 start");
                    for (int i = 0; i < 5; i++) {
                        Thread.sleep(500);
                        System.out.println("thread2 is working" + i);
                    }
                    System.out.println("thread2 end");
                    semaphore.release();
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquireUninterruptibly(2);
                    System.out.println("thread3 start");
                    System.out.println("thread3 end");
                    semaphore.release();
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });
        try {
            thread1.start();
            thread2.start();
            thread3.start();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }

}
