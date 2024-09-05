package com.example.demo.threadsync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class ThreadSync5 {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        //线程B
        final Thread threadB = new Thread(() -> {
            if (list.size() != 5) {
                LockSupport.park();
            }
            System.out.println("线程B收到通知，开始执行自己的业务...");
        });
        //线程A
        Thread threadA = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                list.add("abc");
                System.out.println("线程A添加元素，此时list的size为：" + list.size());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (list.size() == 5) {
                    LockSupport.unpark(threadB);
                }
            }
        });
        threadA.start();
        threadB.start();
    }
}
