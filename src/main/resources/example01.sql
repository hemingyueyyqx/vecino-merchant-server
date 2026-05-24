select u.id ,s.id,u.nickname,u.account,u.phone,s.shop_name ,
       s.shop_type ,
       s.address,
       s.status,
       s.legal_person ,
       s.business_license,
       s.audit_reason,
       s.update_time

from `user` u
         join shop_info s on u.id = s.business_id
where u.role = 'Sj08';
-- 一级类目
INSERT INTO category_first (code, name)
VALUES
    ('flower','鲜花绿植'),
    ('clothes','服饰鞋帽'),
    ('beauty','美妆日化'),
    ('baby_toy','母婴玩具'),
    ('fruit','水果类'),
    ('food_material','食材'),
    ('pet','宠物类'),
    ('supermarket','超市便利'),
    ('daily_goods','日用百货'),
    ('drink_wine','酒水茶饮'),
    ('snack','休闲食品'),
    ('digital','数码家电'),
    ('mall','大型百货商场');

-- 二级类目
INSERT INTO category_second (code, name, first_code)
VALUES
-- 鲜花绿植
('flower_shop','鲜花店','flower'),
('plant_shop','绿植/园艺店','flower'),
('flower_plant_mix','综合鲜花绿植店','flower'),

-- 服饰鞋帽
('bag_accessory','箱包/配饰店','clothes'),
('underwear_home','内衣/家居服店','clothes'),
('shoe_men_women','男女鞋店','clothes'),
('clothes_mix','综合服饰鞋帽店','clothes'),
('women_clothes','女装店','clothes'),
('men_clothes','男装店','clothes'),
('sport_shoe_clothes','运动鞋/服店','clothes'),
('outdoor_sport','户外/体育用品店','clothes'),

-- 美妆日化
('beauty_mix','综合美妆日化专营店','beauty'),
('beauty_brand','美妆品牌专卖店','beauty'),

-- 母婴玩具
('baby_clothes','婴童服饰鞋帽店','baby_toy'),
('toy_model','玩具/潮玩/模型店','baby_toy'),
('baby_mix','综合母婴店','baby_toy'),

-- 水果类
('whole_fruit','整果店','fruit'),
('cut_fruit','果切店','fruit'),
('fruit_pulp','果捞店','fruit'),

-- 食材
('fresh_supermarket','综合生鲜果蔬超市','food_material'),
('market','菜市场','food_material'),
('front','前置仓','food_material'),
('hotpot','火锅专营店','food_material'),
('seafood','海鲜/水产店','food_material'),
('meat_poultry','肉禽店','food_material'),
('bbq_shop','烧烤专营店','food_material'),
('pre_food','预制菜专营店','food_material'),

-- 宠物类
('pet_food_goods','宠物食品/用品店','pet'),

-- 超市便利
('big_supermarket','大型超市/卖场','supermarket'),
('mini_shop','小型超市便利店','supermarket'),

-- 日用百货
('daily_mix','综合日用百货店','daily_goods'),
('book_store','书店','daily_goods'),
('hardware_light','五金/建材/灯具店','daily_goods'),
('stationery','文具/文创/办公用品店','daily_goods'),
('tableware_kitchen','餐具厨具店','daily_goods'),
('home_textile','家居/家具/家纺店','daily_goods'),
('festival_gift','节庆用品/礼品店','daily_goods'),
('jewelry','珠宝首饰店','daily_goods'),

-- 酒水茶饮
('wine_shop','酒水店','drink_wine'),
('water_station','水站','drink_wine'),
('milk_station','奶站','drink_wine'),
('tea_shop','茶行','drink_wine'),

-- 休闲食品
('snack_shop','零食店','snack'),
('nut_fried','干果炒货店','snack'),
('ice_shop','冰品店','snack'),
('local_special','地方特产店','snack'),
('grain_seasoning','粮油调味店','snack'),
('bulk_snack','量贩零食店','snack'),
('discount_shop','折扣超市','snack'),

-- 数码家电
('phone_brand','手机通讯品牌店','digital'),
('computer_digital','电脑数码品牌店','digital'),
('home_appliance','家用电器品牌店','digital'),
('3c_mix','3C电器综合店','digital'),
('operator_shop','运营商店','digital');
UPDATE category_first SET sort =
                              CASE code
                                  WHEN 'flower' THEN 1
                                  WHEN 'clothes' THEN 2
                                  WHEN 'beauty' THEN 3
                                  WHEN 'baby_toy' THEN 4
                                  WHEN 'fruit' THEN 5
                                  WHEN 'food_material' THEN 6
                                  WHEN 'pet' THEN 7
                                  WHEN 'supermarket' THEN 8
                                  WHEN 'daily_goods' THEN 9
                                  WHEN 'drink_wine' THEN 10
                                  WHEN 'snack' THEN 11
                                  WHEN 'digital' THEN 12
                                  WHEN 'mall' THEN 13
                                  END;
UPDATE category_second SET sort =
                               CASE code
-- 鲜花绿植
                                   WHEN 'flower_shop' THEN 1
                                   WHEN 'plant_shop' THEN 2
                                   WHEN 'flower_plant_mix' THEN 3

-- 服饰鞋帽
                                   WHEN 'bag_accessory' THEN 1
                                   WHEN 'underwear_home' THEN 2
                                   WHEN 'shoe_men_women' THEN 3
                                   WHEN 'clothes_mix' THEN 4
                                   WHEN 'women_clothes' THEN 5
                                   WHEN 'men_clothes' THEN 6
                                   WHEN 'sport_shoe_clothes' THEN 7
                                   WHEN 'outdoor_sport' THEN 8

-- 美妆日化
                                   WHEN 'beauty_mix' THEN 1
                                   WHEN 'beauty_brand' THEN 2

-- 母婴玩具
                                   WHEN 'baby_clothes' THEN 1
                                   WHEN 'toy_model' THEN 2
                                   WHEN 'baby_mix' THEN 3

-- 水果类
                                   WHEN 'whole_fruit' THEN 1
                                   WHEN 'cut_fruit' THEN 2
                                   WHEN 'fruit_pulp' THEN 3

-- 食材
                                   WHEN 'fresh_supermarket' THEN 1
                                   WHEN 'market' THEN 2
                                   WHEN 'front' THEN 3
                                   WHEN 'hotpot' THEN 4
                                   WHEN 'seafood' THEN 5
                                   WHEN 'meat_poultry' THEN 6
                                   WHEN 'bbq_shop' THEN 7
                                   WHEN 'pre_food' THEN 8

-- 宠物类
                                   WHEN 'pet_food_goods' THEN 1

-- 超市便利
                                   WHEN 'big_supermarket' THEN 1
                                   WHEN 'mini_shop' THEN 2

-- 日用百货
                                   WHEN 'daily_mix' THEN 1
                                   WHEN 'book_store' THEN 2
                                   WHEN 'hardware_light' THEN 3
                                   WHEN 'stationery' THEN 4
                                   WHEN 'tableware_kitchen' THEN 5
                                   WHEN 'home_textile' THEN 6
                                   WHEN 'festival_gift' THEN 7
                                   WHEN 'jewelry' THEN 8

-- 酒水茶饮
                                   WHEN 'wine_shop' THEN 1
                                   WHEN 'water_station' THEN 2
                                   WHEN 'milk_station' THEN 3
                                   WHEN 'tea_shop' THEN 4

-- 休闲食品
                                   WHEN 'snack_shop' THEN 1
                                   WHEN 'nut_fried' THEN 2
                                   WHEN 'ice_shop' THEN 3
                                   WHEN 'local_special' THEN 4
                                   WHEN 'grain_seasoning' THEN 5
                                   WHEN 'bulk_snack' THEN 6
                                   WHEN 'discount_shop' THEN 7

-- 数码家电
                                   WHEN 'phone_brand' THEN 1
                                   WHEN 'computer_digital' THEN 2
                                   WHEN 'home_appliance' THEN 3
                                   WHEN '3c_mix' THEN 4
                                   WHEN 'operator_shop' THEN 5

                                   ELSE 0
                                   END;
-- 新增字段：营业执照注册图片访问地址
ALTER TABLE `shop_info`
    ADD COLUMN `business_license_img` VARCHAR(512) NULL COMMENT '营业执照注册图片访问地址' AFTER `legal_person`;
-- 第一步：插入10条【商户角色】用户数据（完全匹配user表结构，唯一约束+字段合规）
INSERT INTO `user` (
    `id`, `account`, `nickname`, `phone`, `password`, `role`, `balance`
) VALUES
-- 商户1
('biz_001', 'merchant_001', '李梅', '13800138001', '$2a$10$Z8H4k7s9F2d5A1g0J3k6L8s9F2d5A1g0', 'Sj08', 500),
-- 商户2
('biz_002', 'merchant_002', '张婷', '13800138002', '$2a$10$Z8H4k7s9F2d5A1g0J3k6L8s9F2d5A1g1', 'Sj08', 300),
-- 商户3
('biz_003', 'merchant_003', '王芳', '13800138003', '$2a$10$Z8H4k7s9F2d5A1g0J3k6L8s9F2d5A1g2', 'Sj08', 0),
-- 商户4
('biz_004', 'merchant_004', '刘勇', '13800138004', '$2a$10$Z8H4k7s9F2d5A1g0J3k6L8s9F2d5A1g3', 'Sj08', 800),
-- 商户5
('biz_005', 'merchant_005', '陈强', '13800138005', '$2a$10$Z8H4k7s9F2d5A1g0J3k6L8s9F2d5A1g4', 'Sj08', 200),
-- 商户6
('biz_006', 'merchant_006', '赵海', '13800138006', '$2a$10$Z8H4k7s9F2d5A1g0J3k6L8s9F2d5A1g5', 'Sj08', 0),
-- 商户7
('biz_007', 'merchant_007', '孙萌', '13800138007', '$2a$10$Z8H4k7s9F2d5A1g0J3k6L8s9F2d5A1g6', 'Sj08', 1000),
-- 商户8
('biz_008', 'merchant_008', '周明', '13800138008', '$2a$10$Z8H4k7s9F2d5A1g0J3k6L8s9F2d5A1g7', 'Sj08', 400),
-- 商户9
('biz_009', 'merchant_009', '吴文', '13800138009', '$2a$10$Z8H4k7s9F2d5A1g0J3k6L8s9F2d5A1g8', 'Sj08', 0),
-- 商户10
('biz_010', 'merchant_010', '郑茶', '13800138010', '$2a$10$Z8H4k7s9F2d5A1g0J3k6L8s9F2d5A1g9', 'Sj08', 600);

-- 第二步：插入10条对应店铺数据（business_id 严格关联 user.id，无外键报错）
INSERT INTO `shop_info` (
    `id`, `business_id`, `shop_name`, `first_category`, `second_category`,
    `address`, `business_license`, `legal_person`, `status`, `audit_reason`
) VALUES
-- 1. 鲜花绿植-鲜花店
('shop_001', 'biz_001', '花香四溢鲜花店', 'flower', 'flower_shop',
 '北京市朝阳区建国路88号现代城5层', '91110105MA01234567', '李梅', 0, NULL),
-- 2. 服饰鞋帽-女装店
('shop_002', 'biz_002', '时尚伊人女装馆', 'clothes', 'women_clothes',
 '上海市浦东新区张江路123号科创大厦1楼', '91310115MA02345678', '张婷', 0, NULL),
-- 3. 美妆日化-品牌专卖店（待审核）
('shop_003', 'biz_003', '悦颜美妆专卖店', 'beauty', 'beauty_brand',
 '广州市天河区天河路385号太古汇一座', '91440106MA03456789', '王芳', 2, '资料提交中，等待平台审核'),
-- 4. 母婴玩具-综合母婴店
('shop_004', 'biz_004', '宝贝乐园母婴馆', 'baby_toy', 'baby_mix',
 '深圳市南山区科技园8号腾讯大厦旁', '91440305MA04567890', '刘勇', 0, NULL),
-- 5. 水果类-果切店
('shop_005', 'biz_005', '鲜切果坊', 'fruit', 'cut_fruit',
 '杭州市西湖区文二路158号西湖国际大厦', '91330106MA05678901', '陈强', 0, NULL),
-- 6. 食材-海鲜水产店（禁用，驳回理由）
('shop_006', 'biz_006', '鲜享海鲜水产店', 'food_material', 'seafood',
 '青岛市市南区香港中路69号麦凯乐商场', '91370202MA06789012', '赵海', 1, '营业执照信息与法人不符'),
-- 7. 宠物类-宠物食品用品店
('shop_007', 'biz_007', '萌宠之家用品店', 'pet', 'pet_food_goods',
 '成都市锦江区春熙路步行街1号', '91510104MA07890123', '孙萌', 0, NULL),
-- 8. 超市便利-小型便利店
('shop_008', 'biz_008', '邻里便民小超市', 'supermarket', 'mini_shop',
 '武汉市洪山区光谷大道16号光谷天地', '91420111MA08901234', '周明', 0, NULL),
-- 9. 日用百货-文具办公用品店（待审核）
('shop_009', 'biz_009', '书香文创办公用品店', 'daily_goods', 'stationery',
 '西安市雁塔区高新路50号高新大都荟', '91610113MA09012345', '吴文', 2, '补充店铺门头照后审核'),
-- 10. 酒水茶饮-茶行
('shop_010', 'biz_010', '茗香茶行', 'drink_wine', 'tea_shop',
 '苏州市姑苏区观前街212号玄妙广场', '91320508MA00123456', '郑茶', 0, NULL);