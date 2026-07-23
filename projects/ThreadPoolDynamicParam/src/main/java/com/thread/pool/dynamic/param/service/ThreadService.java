package com.thread.pool.dynamic.param.service;

import java.util.Map;

public interface ThreadService {

    String startTask(String taskName, Map map);


    String update(String taskName, Map map);

}
