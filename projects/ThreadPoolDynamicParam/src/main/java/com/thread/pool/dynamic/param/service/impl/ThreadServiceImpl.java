package com.thread.pool.dynamic.param.service.impl;

import com.thread.pool.dynamic.param.object.InitParam;
import com.thread.pool.dynamic.param.object.MutableTaskParam;
import com.thread.pool.dynamic.param.object.NamedTask;
import com.thread.pool.dynamic.param.service.ThreadService;
import com.thread.pool.dynamic.param.util.ThreadPoolUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class ThreadServiceImpl implements ThreadService {

    ThreadPoolUtil threadPoolUtil = new ThreadPoolUtil();

    @Override
    public String startTask(String taskName, Map map) {
        InitParam initParam = new InitParam();
        initParam.setTaskName(taskName);
        initParam.setMap(map);

        NamedTask<InitParam, Integer> namedTask = threadPoolUtil.submitTask(taskName, initParam, mutableParam -> {
            return count(mutableParam);
        });

        try {
            return namedTask.getFuture().get().toString();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String update(String taskName, Map map) {

        Integer a = 0;
        InitParam initParam = new InitParam();
        initParam.setTaskName(taskName);
        initParam.setMap(map);
        boolean updateSuccess = threadPoolUtil.updateTaskParam(taskName, initParam);
        if (updateSuccess) {
            try {
                a = threadPoolUtil.getTaskResult(taskName, 3000, TimeUnit.MILLISECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
        return String.valueOf(a);
    }

    public Integer count(MutableTaskParam mutableParam) {
        Integer cou = 0;

        // 循环模拟长时间运行任务，每轮读取最新参数
        for (int i = 1; i <= 10; i++) {
            // 核心：每次循环读取最新可变参数
            InitParam currentParam = (InitParam) mutableParam.get();
            System.out.printf("【任务第%d轮执行】当前参数：%s%n", i, currentParam);
            try {
                if ((boolean) currentParam.getMap().get("flag")) {
                    break;
                }
                TimeUnit.SECONDS.sleep(1);
                cou = i;
            } catch (InterruptedException e) {
                System.out.println("任务被中断，退出循环");
                break;
            }
        }
        return cou;
    }

}
