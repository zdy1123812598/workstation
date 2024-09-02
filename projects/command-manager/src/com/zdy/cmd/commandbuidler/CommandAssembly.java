package com.zdy.cmd.commandbuidler;

import java.util.Map;

/**
 * 命令组装器接口
 */
public interface CommandAssembly {
    /**
     * 将参数转为命令
     *
     * @param paramMap
     * @return
     */
    public String assembly(Map<String, String> paramMap);

    public String assembly();
}

