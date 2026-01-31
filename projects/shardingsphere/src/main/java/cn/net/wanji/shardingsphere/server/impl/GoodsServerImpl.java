package cn.net.wanji.sp.server.impl;

import cn.net.wanji.sp.entity.Goods;
import cn.net.wanji.sp.mapper.GoodsMapper;
import cn.net.wanji.sp.server.GoodsServer;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsServerImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsServer {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public void createTable(String templateTable, String tableName) {
        goodsMapper.createTable(templateTable, tableName);
    }

    @Override
    public Integer existTable(String tableName) {
        return goodsMapper.existTable(tableName);
    }
}
