package com.zdy.threadpoolweb.controller;

import cn.hutool.core.util.StrUtil;
import com.zdy.threadpoolweb.VO.ResultVO;
import com.zdy.threadpoolweb.enums.ResultEnum;
import com.zdy.threadpoolweb.exceptions.PusherException;
import com.zdy.threadpoolweb.model.PushTask;
import com.zdy.threadpoolweb.model.WorkerStatus;
import com.zdy.threadpoolweb.service.PushService;
import com.zdy.threadpoolweb.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class PushController {

    @Autowired
    private PushService pushService;

    @PostMapping(value = "/pushers")
    public ResultVO<WorkerStatus> pusher(@RequestBody PushTask data) {
        if (StrUtil.hasBlank(data.getId())) {
            throw new PusherException(ResultEnum.PARAMES_ERROR);
        }
        WorkerStatus status = pushService.createPusher(data);
        return ResultUtil.success(status);
    }

    /**
     * 获取指定pusher的工作状态
     */
    @GetMapping(value = "/pushers/status")
    public ResultVO<WorkerStatus> pusherStatus(@RequestParam("id") String id) {
        if (StrUtil.isBlank(id)) {
            throw new PusherException(ResultEnum.PARAMES_ERROR);
        }
        WorkerStatus status = pushService.pusherStatus(id);
        return ResultUtil.success(status);
    }

    /**
     * 停止指定pusher的工作
     */
    @DeleteMapping(value = "/pushers")
    public ResultVO<WorkerStatus> stopPusher(@RequestParam("id") String id) {
        if (StrUtil.isBlank(id)) {
            throw new PusherException(ResultEnum.PARAMES_ERROR);
        }
        WorkerStatus status = pushService.stopPusher(id);
        return ResultUtil.success(status);
    }

}
