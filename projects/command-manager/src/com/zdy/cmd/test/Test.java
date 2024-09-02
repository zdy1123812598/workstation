package com.zdy.cmd.test;

import com.zdy.cmd.CommandManager;
import com.zdy.cmd.CommandManagerImpl;
import com.zdy.cmd.commandbuidler.CommandBuidlerFactory;

public class Test {

    public static void main(String[] args) {

        test();

    }


    public static void test() {
        CommandManager manager = new CommandManagerImpl();

        manager.start("testx", CommandBuidlerFactory.createBuidler()
                .add("java")
                .add("-version")
        );

        manager.stopAll();
        manager.destory();
    }
}
