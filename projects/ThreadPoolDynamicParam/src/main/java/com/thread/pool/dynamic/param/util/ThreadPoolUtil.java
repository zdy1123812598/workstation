package com.thread.pool.dynamic.param.util;


import com.thread.pool.dynamic.param.config.ThreadPoolConfig;
import com.thread.pool.dynamic.param.object.MutableTaskParam;
import com.thread.pool.dynamic.param.object.NamedTask;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;

@Slf4j
public class ThreadPoolUtil {

    // 全局任务缓存：taskName -> 命名任务实体
    private final Map<String, NamedTask<?, ?>> taskCache = new ConcurrentHashMap<>();

    @Resource
    private ThreadPoolConfig threadPoolConfig;

    /**
     * 创建并提交命名任务
     *
     * @param taskName  唯一任务名，同名会覆盖旧任务
     * @param initParam 初始业务参数
     * @param taskLogic 任务执行逻辑，入参为可变参数容器，返回结果
     * @return 封装后的命名任务
     */
    public <T, R> NamedTask<T, R> submitTask(String taskName, T initParam, Function<MutableTaskParam<T>, R> taskLogic) {
        ThreadPoolTaskExecutor executor = threadPoolConfig.getSharedThreadPoolTaskExecutor();
        if (executor == null) {
            throw new IllegalStateException("线程池未初始化，请确保ThreadPoolConfig已加载");
        }
        // 1. 创建可变参数容器
        MutableTaskParam<T> mutableParam = new MutableTaskParam<>(initParam);
        // 2. 提交异步任务
        CompletableFuture<R> future = CompletableFuture.supplyAsync(() -> taskLogic.apply(mutableParam), executor);
        // 3. 封装命名任务
        NamedTask<T, R> namedTask = new NamedTask<>(taskName, mutableParam, future);
        // 4. 存入缓存，同名直接覆盖旧任务
        taskCache.put(taskName, namedTask);
        // 5. 任务完成后自动清理缓存（可选）
        future.whenComplete((r, e) -> {
            // 延迟清理，可保留一段时间用于查询结果，这里演示立即清理
            taskCache.remove(taskName);
        });
        return namedTask;
    }

    /**
     * 根据任务名称动态更新参数，同名运行中任务实时生效
     *
     * @param taskName 任务名
     * @param newParam 新业务参数
     * @return 是否更新成功（任务存在返回true）
     */
    @SuppressWarnings("unchecked")
    public <T> boolean updateTaskParam(String taskName, T newParam) {
        NamedTask<T, ?> task = (NamedTask<T, ?>) taskCache.get(taskName);
        if (task == null || task.isDone()) {
            return false;
        }
        task.updateParam(newParam);
        return true;
    }

    /**
     * 获取任务执行结果，阻塞等待
     */
    @SuppressWarnings("unchecked")
    public <R> R getTaskResult(String taskName, long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        NamedTask<?, R> task = (NamedTask<?, R>) taskCache.get(taskName);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在: " + taskName);
        }
        return task.getFuture().get(timeout, unit);
    }

    /**
     * 取消运行中任务
     *
     * @param taskName  任务名
     * @param interrupt 是否中断线程
     * @return 取消是否成功
     */
    public boolean cancelTask(String taskName, boolean interrupt) {
        NamedTask<?, ?> task = taskCache.get(taskName);
        if (task == null || task.isDone()) {
            return false;
        }
        return task.cancel(interrupt);
    }

    /**
     * 查询任务是否存在
     */
    public boolean taskExists(String taskName) {
        return taskCache.containsKey(taskName);
    }

    /**
     * 手动清理已完成任务缓存
     */
    public void clearFinishedTasks() {
        taskCache.entrySet().removeIf(entry -> entry.getValue().isDone());
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        ThreadPoolTaskExecutor executor = threadPoolConfig.getSharedThreadPoolTaskExecutor();
        if (executor == null) {
            throw new IllegalStateException("线程池未初始化，请确保ThreadPoolConfig已加载");
        }
        executor.shutdown();
    }
}