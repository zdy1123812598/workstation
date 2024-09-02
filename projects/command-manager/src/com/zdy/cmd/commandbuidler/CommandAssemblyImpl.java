package com.zdy.cmd.commandbuidler;


import java.util.Map;

/**
 * 默认命令组装器实现
 */
public class CommandAssemblyImpl implements CommandAssembly {
    /**
     * @param paramMap -要组装的map
     * @return
     */
    @Override
    public String assembly(Map<String, String> paramMap) {
        try {
            StringBuilder comm = new StringBuilder();
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                comm.append(entry.getKey() + " " + entry.getValue());
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public String assembly() {
        // TODO Auto-generated method stub
        return null;
    }
}

