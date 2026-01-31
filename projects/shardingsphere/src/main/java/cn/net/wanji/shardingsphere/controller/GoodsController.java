package cn.net.wanji.sp.controller;

import cn.net.wanji.sp.entity.Goods;
import cn.net.wanji.sp.po.QueryPo;
import cn.net.wanji.sp.server.GoodsServer;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
public class GoodsController {

    @Resource
    private GoodsServer goodsServer;

    @PostMapping("/save")
    public boolean saveGoods(@RequestBody Goods goods) {
        if (null == goods) {
            goods.setCreateTime(new Date());
        }
        return goodsServer.save(goods);
    }

    @PostMapping("/list")
    public List<Goods> listGoods(@RequestBody QueryPo queryPo) {
        LambdaQueryWrapper<Goods> query = new LambdaQueryWrapper<>();
        if (null != queryPo.getStartTime()) {
            query.gt(Goods::getCreateTime, queryPo.getStartTime());
        }
        if (null != queryPo.getEndTime()) {
            query.le(Goods::getCreateTime, queryPo.getEndTime());
        }
        return goodsServer.list(query);
    }

}