package com.zdy.threadpoolweb.service.impl;

import com.zdy.threadpoolweb.model.PushTask;
import com.zdy.threadpoolweb.model.WorkerStatus;
import com.zdy.threadpoolweb.service.PushService;
import com.zdy.threadpoolweb.worker.WorkerThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushServiceImpl implements PushService {

    @Autowired
    private WorkerThreadPool workerThreadPool;
    
    @Override
    public WorkerStatus createPusher(PushTask pushTask) {
        return workerThreadPool.execPushTask(pushTask);
    }

    @Override
    public WorkerStatus stopPusher(String taskId) {
        return workerThreadPool.stopPushWorker(taskId);
    }

    @Override
    public WorkerStatus pusherStatus(String taskId) {
        return workerThreadPool.getPushWorkerStatus(taskId);
    }
}
