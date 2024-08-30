package com.zdy.threadpoolweb.cache;

import com.zdy.threadpoolweb.worker.PushWorker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CacheUtil {

    /**
     * 存储ID与PushWorker之间的映射
     * ID为PushTask的ID
     */
    public static Map<String, PushWorker> PUSHWORKERMAP = new ConcurrentHashMap<>();

    /*
     * 保存服务启动时间
     */
    public static long STARTTIME;

}
