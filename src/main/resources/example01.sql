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