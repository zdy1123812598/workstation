package com.thread.pool.dynamic.param.controller;

import com.thread.pool.dynamic.param.service.ThreadService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/thread")
public class ThreadController {

    @Resource
    private ThreadService threadService;

    // 1. 启动指定名称任务
    @GetMapping("/task/start")
    public String start(@RequestParam String taskName) {
        Map map = new HashMap();
        map.put("flag", false);
        return threadService.startTask(taskName, map);
    }

    // 2. 同一任务：动态新增/修改参数（新传入参数）
    @GetMapping("/task/update")
    public String update(
            @RequestParam String taskName, @RequestParam Boolean endFlag
    ) {
        Map map = new HashMap();
        map.put("flag", endFlag);
        return threadService.update(taskName, map);
    }

}
