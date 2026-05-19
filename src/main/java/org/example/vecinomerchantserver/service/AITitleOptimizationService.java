package org.example.vecinomerchantserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.CategoryMappingUtil;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.repository.ShopInfoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
// Deleted:import org.springframework.web.client.RestTemplate;
// Deleted:import org.springframework.http.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.lang.System;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
// Deleted:import com.alibaba.dashscope.utils.Constants;


@Service
@Slf4j
@RequiredArgsConstructor
public class AITitleOptimizationService {

    // Deleted:private final ObjectMapper objectMapper = new ObjectMapper();
    // Deleted:private final RestTemplate restTemplate = new RestTemplate();
    private final ShopInfoRepository shopInfoRepository;

    @Value("${my.aliyunkey}")
    private String apiKey;

    // Deleted:private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";

    public List<String> optimizeTitle(String originalTitle, String uid) {
        ShopInfo shopInfo = shopInfoRepository.findByBusinessId(uid);
        String category = CategoryMappingUtil.getFullCategory(shopInfo.getFirstCategory(), shopInfo.getSecondCategory());
        // 电商标题优化Prompt
        String prompt = String.format("""
                你是电商资深运营专员，严格按照店铺主营类目优化商品标题。
                店铺主营类目：%s
                原始商品标题：%s
                优化要求：
                1. 严格贴合该类目行业属性，匹配店铺售卖品类
                2. 保留商品原有核心名称、品牌、规格、关键属性
                3. 优化通顺通顺，增加搜索热门关键词，提升曝光
                4. 标题字数控制15-30字，简洁干净无特殊符号
                5. 直接输出3条不同风格优化后的商品标题，每条单独一行，不要多余说明
                """, originalTitle, category);

        try {
            Generation gen = new Generation();
            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content("你是电商资深运营专员，擅长优化商品标题。")
                    .build();
            Message userMsg = Message.builder()
                    .role(Role.USER.getValue())
                    .content(prompt)
                    .build();

            GenerationParam param = GenerationParam.builder()
                    .apiKey(apiKey)
                    .model("qwen-plus")
                    .messages(Arrays.asList(systemMsg, userMsg))
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .build();

            GenerationResult result = gen.call(param);
            String content = result.getOutput().getChoices().get(0).getMessage().getContent();

            // 分割换行符，过滤空行，限制最多3条
            return Arrays.stream(content.split("\n"))
                    .map(String::trim)
                    .filter(line -> !line.isBlank())
                    .limit(3)
                    .collect(Collectors.toList());
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            log.error("AI标题优化异常", e);
            // 异常兜底：返回原始标题作为唯一元素
            return List.of(originalTitle);
        }
    }
    // Deleted:public static GenerationResult callWithMessage() throws ApiException, NoApiKeyException, InputRequiredException {
    // Deleted:Generation gen = new Generation();
    // Deleted:Message systemMsg = Message.builder()
    // Deleted:.role(Role.SYSTEM.getValue())
    // Deleted:.content("You are a helpful assistant.")
    // Deleted:.build();
    // Deleted:Message userMsg = Message.builder()
    // Deleted:.role(Role.USER.getValue())
    // Deleted:.content("你是谁？")
    // Deleted:.build();
    // Deleted:GenerationParam param = GenerationParam.builder()
    // Deleted:// 若没有配置环境变量，请用阿里云百炼API Key将下行替换为：.apiKey("sk-xxx")
    // Deleted:.apiKey(System.getenv("DASHSCOPE_API_KEY"))
    // Deleted:// 模型列表：https://help.aliyun.com/model-studio/getting-started/models
    // Deleted:.model("qwen-plus")
    // Deleted:.messages(Arrays.asList(systemMsg, userMsg))
    // Deleted:.resultFormat(GenerationParam.ResultFormat.MESSAGE)
    // Deleted:.build();
    // Deleted:return gen.call(param);
    // Deleted:}
    // Deleted:public static void main(String[] args) {
    // Deleted:try {
    // Deleted:GenerationResult result = callWithMessage();
    // Deleted:System.out.println(result.getOutput().getChoices().get(0).getMessage().getContent());
    // Deleted:} catch (ApiException | NoApiKeyException | InputRequiredException e) {
    // Deleted:System.err.println("错误信息："+e.getMessage());
    // Deleted:System.out.println("请参考文档：https://help.aliyun.com/model-studio/developer-reference/error-code");
    // Deleted:}
    // Deleted:System.exit(0);
    // Deleted:}
}
