package com.zdy.threadpoolweb.enums;

import lombok.Getter;

@Getter
public enum WorkStatusEnum {
    /**
     * 运行中
     */
    RUNNING(0, "running"),
    /**
     * 不存在
     */
    NOTEXIST(1, "not existing"),
    /**
     * 已停止
     */
    STOPPED(2, "stopped"),
    /**
     * 等待启动中
     */
    WAITING(3, "waiting");

    private Integer status;

    private String msg;

    WorkStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

}
