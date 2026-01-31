package cn.net.wanji.sp.mapper;

import cn.net.wanji.sp.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    void createTable(@Param("templateTable") String templateTable, @Param("tableName") String tableName);

    Integer existTable(@Param("tableName") String tableName);
}
