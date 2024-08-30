package com.zdy.threadpoolweb.service;

import com.zdy.threadpoolweb.model.PushTask;
import com.zdy.threadpoolweb.model.WorkerStatus;

public interface PushService {
    /**
     * 启动一个推流器
     *
     * @param pushTask
     * @return
     */
    WorkerStatus createPusher(PushTask pushTask);

    /**
     * 停止指定的推流
     *
     * @param pushId
     * @return
     */
    WorkerStatus stopPusher(String pushId);

    /**
     * 获取指定推流器的工作状态
     *
     * @param pushId
     * @return
     */
    WorkerStatus pusherStatus(String pushId);


}
