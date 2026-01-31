package cn.net.wanji.sp.server;

import cn.net.wanji.sp.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

public interface GoodsServer extends IService<Goods> {

    void createTable(String templateTable, String tableName);

    Integer existTable(String tableName);

}
