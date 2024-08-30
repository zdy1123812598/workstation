package com.zdy.threadpoolweb.worker;

import com.zdy.threadpoolweb.cache.CacheUtil;
import com.zdy.threadpoolweb.model.PushTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PushWorker implements Runnable {

    private PushTask pushTask = null;

    /**
     * 控制该进程是否进行
     */
    private volatile boolean isRunning = false;
    /**
     * 记录进程的启动时间，用于计算运行时长
     */
    private long startTime = System.currentTimeMillis();

    PushWorker(PushTask pushTask) {
        this.pushTask = pushTask;
    }

    public void stop() {
        this.isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }


    /**
     * 获取进程的运行时长
     *
     * @return
     */
    public long getRunningTime() {
        return System.currentTimeMillis() - startTime;
    }


    @Override
    public void run() {
        try {
            //具体任务
            go();
        } catch (Exception e) {
            log.error("推流服务异常");
        }
        CacheUtil.PUSHWORKERMAP.remove(pushTask.getId());
        log.debug("从cache移除，id=" + pushTask.getId());


    }


    public void go() throws Exception {
        startTime = System.currentTimeMillis();

        /**
         *
         * 具体执行任务启动 start
         */

        isRunning = true;
        while (isRunning) {
            try {
                // 用于中断线程时，结束该循环
                Thread.sleep(1);

                CacheUtil.PUSHWORKERMAP.entrySet().stream().forEach(entry -> {
                    System.out.print(entry.getKey() + "_");
                });
                System.out.println("_" + System.currentTimeMillis());

            } catch (InterruptedException e) {
                log.error("拉流进程出现异常");
                isRunning = false;
            }
        }

        /**
         * 具体任务停止执行 stop
         */

    }


}
