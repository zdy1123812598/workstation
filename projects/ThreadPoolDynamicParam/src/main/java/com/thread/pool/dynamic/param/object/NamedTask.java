package com.thread.pool.dynamic.param.object;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 带名称的CompletableFuture任务封装
 *
 * @param <T> 参数类型
 * @param <R> 任务返回结果类型
 */
public class NamedTask<T, R> {
    // 任务唯一名称
    private final String taskName;
    // 可变参数容器
    private final MutableTaskParam<T> mutableParam;
    // 任务Future
    private final CompletableFuture<R> future;
    // 标记任务是否主动取消
    private final AtomicBoolean cancelled = new AtomicBoolean(false);

    public NamedTask(String taskName, MutableTaskParam<T> mutableParam, CompletableFuture<R> future) {
        this.taskName = taskName;
        this.mutableParam = mutableParam;
        this.future = future;
    }

    // 更新参数
    public void updateParam(T newParam) {
        mutableParam.update(newParam);
    }

    // 获取当前最新参数
    public T getCurrentParam() {
        return mutableParam.get();
    }

    // 取消任务
    public boolean cancel(boolean mayInterruptIfRunning) {
        cancelled.set(true);
        return future.cancel(mayInterruptIfRunning);
    }

    public String getTaskName() {
        return taskName;
    }

    public CompletableFuture<R> getFuture() {
        return future;
    }

    public boolean isCancelled() {
        return cancelled.get() || future.isCancelled();
    }

    public boolean isDone() {
        return future.isDone();
    }
}