package com.example.demo.thread;

import java.util.concurrent.CyclicBarrier;

public class Thread7 {


    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(5);
        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("线程" + Thread.currentThread().getId() + "开始执行");
                        Thread.sleep(2000);
                        System.out.println("线程" + Thread.currentThread().getId() + "执行结束");
                        barrier.await();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }).start();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    barrier.await();
                    System.out.println("等待线程开始执行");
                    Thread.sleep(2000);
                    System.out.println("等待线程执行结束");
                } catch (Exception ex) {
                    System.out.println(ex);
                }

            }
        }).start();
    }


}
