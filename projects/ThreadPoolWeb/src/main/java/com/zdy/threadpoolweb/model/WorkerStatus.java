package com.zdy.threadpoolweb.model;

import cn.hutool.core.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zdy.threadpoolweb.enums.WorkStatusEnum;
import lombok.Data;

@Data
public class WorkerStatus {

    /**
     * 当前推理worker的工作时长
     */
    private Integer workerStatus = WorkStatusEnum.NOTEXIST.getStatus();

    /**
     * 当前推流worker工作时长
     */
    private long runningTime = 0L;

    @JsonIgnore
    WorkStatusEnum getWorkStatusEnum() {
        return EnumUtil.getEnumAt(WorkStatusEnum.class, workerStatus);
    }

}
