CREATE TABLE `goods` (
                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                         `create_time` datetime NOT NULL COMMENT '创建时间（分表键）',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表';