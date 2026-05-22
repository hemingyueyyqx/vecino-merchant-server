CREATE TABLE IF NOT EXISTS `user`(
                        `id` VARCHAR(64) NOT NULL COMMENT '用户唯一标识',
                        `account` VARCHAR(20) NOT NULL COMMENT '登录账号（5-20位数字、字母或下划线）',
                        `nickname` VARCHAR(20) NOT NULL COMMENT '用户昵称/姓名',
                        `phone` CHAR(11) NOT NULL COMMENT '手机号（11位）',
                        `password` VARCHAR(255) NOT NULL COMMENT '密码（哈希存储，不存明文）',
                        `role` char(4) NOT NULL COMMENT '角色：商户/管理员/普通用户',
                        `balance` int unsigned NOT NULL DEFAULT 0 COMMENT '用户余额',
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
                             `shop_name` VARCHAR(20) NOT NULL COMMENT '店铺名称',
                             `first_category` VARCHAR(32) NULL COMMENT '一级类目编码',
                             `second_category` VARCHAR(32) NULL COMMENT '二级类目编码',
                             `address` VARCHAR(500) NOT NULL COMMENT '店铺物理地址',
                             `business_license` VARCHAR(20) NOT NULL COMMENT '营业执照注册号',
                             `legal_person` VARCHAR(10) NOT NULL COMMENT '法人姓名',
                             `status`  tinyint unsigned not null default 1 COMMENT '店铺状态：正常0/禁用1/待审核2',
                             `audit_reason` VARCHAR(255) NULL COMMENT '审核原因/驳回理由',
                             `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `uk_shop_name` (`shop_name`),
                             KEY `idx_business_id` (`business_id`),
                             KEY `idx_status` (`status`),
                             CONSTRAINT `fk_shop_business` FOREIGN KEY (`business_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户店铺信息表';

-- 1. 商品类目表 product_category
CREATE TABLE IF NOT EXISTS `product_category` (
                                    `id` varchar(64) NOT NULL COMMENT '类目唯一标识（主键）',
                                    `category_name` varchar(50) NOT NULL COMMENT '类目名称',
                                    `parent_id` varchar(64) DEFAULT '0' COMMENT '父类目ID（顶级类目为0）',
                                    `level` tinyint NOT NULL COMMENT '类目层级（1=一级，2=二级，3=三级）',
                                    `sort` int NOT NULL DEFAULT 0 COMMENT '类目排序号（数字越小越靠前）',
                                    `attr_standard` varchar(500) DEFAULT NULL COMMENT '类目属性标准',
                                    `status` tinyint NOT NULL DEFAULT 1 COMMENT '类目状态（0=禁用，1=启用）',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`id`),
                                    KEY `idx_parent_id` (`parent_id`),
                                    KEY `idx_level_status` (`level`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品类目表（平台端类目管理）';

-- 2. 商品标准单元表 product_spu
CREATE TABLE IF NOT EXISTS `product_spu` (
                               `id` varchar(64) NOT NULL COMMENT 'SPU唯一标识（主键）',
                               `shop_id` varchar(64) NOT NULL COMMENT '所属店铺ID',
                               `shop_name` varchar(20) NOT NULL COMMENT '店铺名称',
                               `spu_name` varchar(200) NOT NULL COMMENT '商品名称',
                               `main_image` varchar(500) NOT NULL COMMENT '商品主图',
                               `detail` varchar(500) NOT NULL COMMENT '商品详情',
                               `audit_status` tinyint NOT NULL DEFAULT 0 COMMENT '类目审核状态（0=待审核，1=审核通过，2=审核驳回）',
                               `audit_remark` varchar(255) DEFAULT NULL COMMENT '审核驳回原因',
                               `status` tinyint NOT NULL DEFAULT 1 COMMENT '商品上下架状态（0=下架，1=上架）',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uk_spu_name` (`spu_name`),
                               KEY `idx_shop_id` (`shop_id`),
                               KEY `idx_audit_status` (`audit_status`),
                               KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品标准单元表（商家端商品核心）';

-- 3. 商品库存单元表 product_sku
CREATE TABLE IF NOT EXISTS `product_sku` (
                               `id` varchar(64) NOT NULL COMMENT 'SKU唯一标识（主键）',
                               `spu_id` varchar(64) NOT NULL COMMENT '所属SPU ID',
                               `spec_attr` varchar(200) NOT NULL COMMENT '商品规格',
                               `price` decimal(10,2) NOT NULL COMMENT '销售价格',
                               `stock_num` int NOT NULL DEFAULT 0 COMMENT '库存数量',
                               `warn_stock` int NOT NULL DEFAULT 10 COMMENT '库存预警值',
                               `status` tinyint NOT NULL DEFAULT 1 COMMENT 'SKU状态（0=禁用，1=启用）',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`),
                               KEY `idx_spu_id` (`spu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品库存单元表（商品规格/库存）';

-- 4. 商品类目审核记录表 product_category_audit
CREATE TABLE IF NOT EXISTS `product_category_audit` (
                                          `id` varchar(64) NOT NULL COMMENT '审核记录唯一标识（主键）',
                                          `spu_id` varchar(64) NOT NULL COMMENT '待审核SPU ID',
                                          `old_category_id` varchar(64) DEFAULT NULL COMMENT '原类目ID',
                                          `new_category_id` varchar(64) NOT NULL COMMENT '申请修改的目标类目ID',
                                          `apply_user_id` varchar(64) NOT NULL COMMENT '提交审核的商家ID',
                                          `audit_user_id` varchar(64) DEFAULT NULL COMMENT '审核管理员ID',
                                          `audit_status` tinyint NOT NULL DEFAULT 0 COMMENT '审核状态（0=待审核，1=通过，2=驳回）',
                                          `audit_remark` varchar(255) DEFAULT NULL COMMENT '审核意见',
                                          `audit_time` datetime DEFAULT NULL COMMENT '审核完成时间',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请提交时间',
                                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                          PRIMARY KEY (`id`),
                                          KEY `idx_spu_id` (`spu_id`),
                                          KEY `idx_audit_status` (`audit_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品类目审核记录表（平台端类目审核）';

CREATE TABLE IF NOT EXISTS `order` (
                         `order_id`          VARCHAR(64)  NOT NULL COMMENT '订单内部ID（主键）',
                         `order_no`          VARCHAR(64)  NOT NULL COMMENT '对外订单编号',
                         `customer_id`       VARCHAR(64)  NOT NULL COMMENT '用户ID',
                         `shop_id`           VARCHAR(64)  NOT NULL COMMENT '店铺ID',
                         `shop_name`         VARCHAR(128) NOT NULL COMMENT '店铺名称快照',
                         `spu_id`            VARCHAR(64)  NOT NULL COMMENT '商品SPU ID',
                         `spu_name`          VARCHAR(255) NOT NULL COMMENT '商品名称快照',
                         `main_image`        VARCHAR(512) COMMENT '商品主图快照',
                         `sku_id`            VARCHAR(64)  NOT NULL COMMENT '商品SKU ID',
                         `spec_attr`         VARCHAR(255) COMMENT '规格属性快照（如颜色:红色;尺寸:M）',
                         `price`             INT          NOT NULL COMMENT '商品单价（单位：分，避免浮点误差）',
                         `quantity`          INT          NOT NULL COMMENT '购买数量',
                         `total_amount`      INT          NOT NULL COMMENT '订单总金额（单位：分）',
                         `address`           VARCHAR(512) NOT NULL COMMENT '收货地址快照',
                         `order_status`      INT unsigned  NOT NULL COMMENT '订单状态（如待付款/已付款/已发货/已完成/已取消）',
                         `create_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `update_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         PRIMARY KEY (`order_id`),
                         UNIQUE KEY `uk_order_no` (`order_no`),
                         KEY `idx_customer_id` (`customer_id`),
                         KEY `idx_shop_id` (`shop_id`),
                         KEY `idx_order_status` (`order_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';
CREATE TABLE IF NOT EXISTS `review` (
                             `review_id` varchar(64) NOT NULL COMMENT '评价ID（主键）',
                             `order_id` varchar(64) DEFAULT NULL COMMENT '关联订单ID',
                             `order_no` varchar(64) DEFAULT NULL COMMENT '关联订单编号',
                             `customer_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
                             `shop_id` varchar(64) DEFAULT NULL COMMENT '店铺ID',
                             `nickname` varchar(100) DEFAULT NULL COMMENT '用户昵称',
                             `image` varchar(1024) DEFAULT NULL COMMENT '评价图片（多个用逗号分隔）',
                             `content` text DEFAULT NULL COMMENT '评价内容',
                             `review_type` int unsigned DEFAULT NULL COMMENT '评价类型 1-好评 2-中评 3-差评',
                             `analysis` text default  null comment 'AI分析结果',
                             `reply_content` text DEFAULT NULL COMMENT '商家回复内容',
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`review_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品评价表';