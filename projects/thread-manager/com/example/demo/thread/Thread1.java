package com.example.demo.thread;

public class Thread1 {


    public static void main(String[] args) {

        try {

            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("thread1 start");
                        for (int i = 0; i < 10; i++) {
                            Thread.sleep(5000);
                            System.out.println("thread1 is working" + i);
                        }
                        System.out.println("thread1 end");
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }
            });

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("thread2 start");
                        for (int i = 100; i < 105; i++) {
                            Thread.sleep(5000);
                            System.out.println("thread2 is working" + i);
                        }
                        System.out.println("thread2 end");
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }
            });

            Thread thread3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("thread3 start");

                        System.out.println("thread3 end");
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }


            });

            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
            thread3.start();


        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }


}
