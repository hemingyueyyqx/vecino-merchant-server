CREATE TABLE IF NOT EXISTS `user`(
                        `id` VARCHAR(64) NOT NULL COMMENT '用户唯一标识',
                        `account` VARCHAR(20) NOT NULL COMMENT '登录账号（5-20位数字、字母或下划线）',
                        `nickname` VARCHAR(100) NOT NULL COMMENT '用户昵称/姓名',
                        `phone` CHAR(11) NOT NULL COMMENT '手机号（11位）',
                        `password` VARCHAR(255) NOT NULL COMMENT '密码（哈希存储，不存明文）',
                        `role` char(4) NOT NULL COMMENT '角色：商户/管理员/普通用户',
                        `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_account` (`account`),
                        UNIQUE KEY `uk_phone` (`phone`),
                        KEY `idx_role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基础信息表';
CREATE TABLE IF NOT EXISTS `address` (
                           `id` VARCHAR(64) NOT NULL COMMENT '地址唯一标识',
                           `customer_id` VARCHAR(64) NOT NULL COMMENT '所属用户ID（关联user表id）',
                           `address` VARCHAR(500) NOT NULL COMMENT '详细收货地址',
                           `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           PRIMARY KEY (`id`),
                           KEY `idx_customer_id` (`customer_id`),
                           CONSTRAINT `fk_address_customer` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='C端用户收货地址表';
CREATE TABLE IF NOT EXISTS `shop_info` (
                             `id` VARCHAR(64) NOT NULL COMMENT '店铺唯一标识',
                             `business_id` VARCHAR(64) NOT NULL COMMENT '所属商户ID（关联user表id，仅role=business的用户）',
                             `shop_name` VARCHAR(200) NOT NULL COMMENT '店铺名称',
                             `shop_type` VARCHAR(100) NOT NULL COMMENT '店铺类型（如：生鲜、便利店、餐饮等）',
                             `address` VARCHAR(500) NOT NULL COMMENT '店铺物理地址',
                             `business_license` VARCHAR(20) NOT NULL COMMENT '营业执照注册号',
                             `legal_person` VARCHAR(10) NOT NULL COMMENT '法人姓名',
                             `status`  tinyint unsigned not null default 1 COMMENT '店铺状态：正常0/禁用1/待审核2',
                             `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `uk_shop_name` (`shop_name`),
                             KEY `idx_business_id` (`business_id`),
                             KEY `idx_status` (`status`),
                             CONSTRAINT `fk_shop_business` FOREIGN KEY (`business_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户店铺信息表';