package com.thread.pool.dynamic.param.object;


import java.util.concurrent.atomic.AtomicReference;

/**
 * 可变任务参数容器，线程安全，支持动态更新
 *
 * @param <T> 业务参数实体
 */
public class MutableTaskParam<T> {

    private final AtomicReference<T> paramRef;

    public MutableTaskParam(T initParam) {
        this.paramRef = new AtomicReference<>(initParam);
    }

    /**
     * 获取当前最新参数
     */
    public T get() {
        return paramRef.get();
    }

    /**
     * 原子更新参数
     */
    public void update(T newParam) {
        paramRef.set(newParam);
    }
}