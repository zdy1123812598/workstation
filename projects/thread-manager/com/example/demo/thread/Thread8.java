package com.example.demo.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thread8 {

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();

    public void start1() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getId() + "执行");
            condition1.await();
            System.out.println(Thread.currentThread().getId() + "start");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getId() + "end");
            lock.unlock();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void start2() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getId() + "执行");
            condition2.await();
            System.out.println(Thread.currentThread().getId() + "start");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getId() + "end");
            lock.unlock();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void releaseAll() {
        lock.lock();
        condition1.signalAll();
        condition2.signalAll();
        lock.unlock();
    }

    public synchronized void release1() {
        lock.lock();
        condition1.signal();
        lock.unlock();
    }

    public synchronized void release2() {
        lock.lock();
        condition2.signal();
        lock.unlock();
    }


    public static void main(String[] args) {

        Thread8 t = new Thread8();
        try {
            Thread thread1 = new Thread(() -> t.start1());
            thread1.start();
            Thread.sleep(100);
            Thread thread2 = new Thread(() -> t.start2());
            thread2.start();
            Thread.sleep(1000);
            t.release2();
            t.release1();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

}
