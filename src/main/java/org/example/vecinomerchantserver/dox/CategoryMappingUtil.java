package org.example.vecinomerchantserver.dox;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品类目工具类：value → 中文名称
 * 完全对应你前端的 categoryOptions 配置
 */
public class CategoryMappingUtil {

    // 一级类目：value → 中文
    private static final Map<String, String> FIRST_CATEGORY = new HashMap<>();
    // 二级类目：value → 中文
    private static final Map<String, String> SECOND_CATEGORY = new HashMap<>();

    static {
        // =============== 一级类目 ===============
        FIRST_CATEGORY.put("flower", "鲜花绿植");
        FIRST_CATEGORY.put("clothes", "服饰鞋帽");
        FIRST_CATEGORY.put("beauty", "美妆日化");
        FIRST_CATEGORY.put("baby_toy", "母婴玩具");
        FIRST_CATEGORY.put("fruit", "水果类");
        FIRST_CATEGORY.put("food_material", "食材");
        FIRST_CATEGORY.put("pet", "宠物类");
        FIRST_CATEGORY.put("supermarket", "超市便利");
        FIRST_CATEGORY.put("daily_goods", "日用百货");
        FIRST_CATEGORY.put("drink_wine", "酒水茶饮");
        FIRST_CATEGORY.put("snack", "休闲食品");
        FIRST_CATEGORY.put("digital", "数码家电");
        FIRST_CATEGORY.put("mall", "大型百货商场");

        // =============== 二级类目 ===============
        // 鲜花绿植
        SECOND_CATEGORY.put("flower_shop", "鲜花店");
        SECOND_CATEGORY.put("plant_shop", "绿植/园艺店");
        SECOND_CATEGORY.put("flower_plant_mix", "综合鲜花绿植店");
        // 服饰鞋帽
        SECOND_CATEGORY.put("bag_accessory", "箱包/配饰店");
        SECOND_CATEGORY.put("underwear_home", "内衣/家居服店");
        SECOND_CATEGORY.put("shoe_men_women", "男女鞋店");
        SECOND_CATEGORY.put("clothes_mix", "综合服饰鞋帽店");
        SECOND_CATEGORY.put("women_clothes", "女装店");
        SECOND_CATEGORY.put("men_clothes", "男装店");
        SECOND_CATEGORY.put("sport_shoe_clothes", "运动鞋/服店");
        SECOND_CATEGORY.put("outdoor_sport", "户外/体育用品店");
        // 美妆日化
        SECOND_CATEGORY.put("beauty_mix", "综合美妆日化专营店");
        SECOND_CATEGORY.put("beauty_brand", "美妆品牌专卖店");
        // 母婴玩具
        SECOND_CATEGORY.put("baby_clothes", "婴童服饰鞋帽店");
        SECOND_CATEGORY.put("toy_model", "玩具/潮玩/模型店");
        SECOND_CATEGORY.put("baby_mix", "综合母婴店");
        // 水果类
        SECOND_CATEGORY.put("whole_fruit", "整果店");
        SECOND_CATEGORY.put("cut_fruit", "果切店");
        SECOND_CATEGORY.put("fruit_pulp", "果捞店");
        // 食材
        SECOND_CATEGORY.put("fresh_supermarket", "综合生鲜果蔬超市");
        SECOND_CATEGORY.put("market", "菜市场");
        SECOND_CATEGORY.put("front", "前置仓");
        SECOND_CATEGORY.put("hotpot", "火锅专营店");
        SECOND_CATEGORY.put("seafood", "海鲜/水产店");
        SECOND_CATEGORY.put("meat_poultry", "肉禽店");
        SECOND_CATEGORY.put("bbq_shop", "烧烤专营店");
        SECOND_CATEGORY.put("pre_food", "预制菜专营店");
        // 宠物类
        SECOND_CATEGORY.put("pet_food_goods", "宠物食品/用品店");
        // 超市便利
        SECOND_CATEGORY.put("big_supermarket", "大型超市/卖场");
        SECOND_CATEGORY.put("mini_shop", "小型超市便利店");
        // 日用百货
        SECOND_CATEGORY.put("daily_mix", "综合日用百货店");
        SECOND_CATEGORY.put("book_store", "书店");
        SECOND_CATEGORY.put("hardware_light", "五金/建材/灯具店");
        SECOND_CATEGORY.put("stationery", "文具/文创/办公用品店");
        SECOND_CATEGORY.put("tableware_kitchen", "餐具厨具店");
        SECOND_CATEGORY.put("home_textile", "家居/家具/家纺店");
        SECOND_CATEGORY.put("festival_gift", "节庆用品/礼品店");
        SECOND_CATEGORY.put("jewelry", "珠宝首饰店");
        // 酒水茶饮
        SECOND_CATEGORY.put("wine_shop", "酒水店");
        SECOND_CATEGORY.put("water_station", "水站");
        SECOND_CATEGORY.put("milk_station", "奶站");
        SECOND_CATEGORY.put("tea_shop", "茶行");
        // 休闲食品
        SECOND_CATEGORY.put("snack_shop", "零食店");
        SECOND_CATEGORY.put("nut_fried", "干果炒货店");
        SECOND_CATEGORY.put("ice_shop", "冰品店");
        SECOND_CATEGORY.put("local_special", "地方特产店");
        SECOND_CATEGORY.put("grain_seasoning", "粮油调味店");
        SECOND_CATEGORY.put("bulk_snack", "量贩零食店");
        SECOND_CATEGORY.put("discount_shop", "折扣超市");
        // 数码家电
        SECOND_CATEGORY.put("phone_brand", "手机通讯品牌店");
        SECOND_CATEGORY.put("computer_digital", "电脑数码品牌店");
        SECOND_CATEGORY.put("home_appliance", "家用电器品牌店");
        SECOND_CATEGORY.put("3c_mix", "3C电器综合店");
        SECOND_CATEGORY.put("operator_shop", "运营商店");
    }

    /**
     * 获取一级类目中文名称
     */
    public static String getFirstCategory(String value) {
        return FIRST_CATEGORY.getOrDefault(value, "其他");
    }

    /**
     * 获取二级类目中文名称
     */
    public static String getSecondCategory(String value) {
        return SECOND_CATEGORY.getOrDefault(value, "");
    }

    /**
     * 获取完整类目名称（一级+二级）
     */
    public static String getFullCategory(String firstValue, String secondValue) {
        String first = getFirstCategory(firstValue);
        String second = getSecondCategory(secondValue);
        return second.isEmpty() ? first : first + " > " + second;
    }
}
