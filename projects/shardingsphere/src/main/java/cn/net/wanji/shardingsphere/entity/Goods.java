package cn.net.wanji.sp.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("goods")
public class Goods {
    
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
}
