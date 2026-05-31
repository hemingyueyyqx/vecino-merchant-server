package org.example.vecinomerchantserver.service;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dto.ActivityConfigDTO;
import org.example.vecinomerchantserver.dto.AiCheckResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    @Value("${my.aliyunkey}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<AiCheckResultDTO> checkActivityRule(ActivityConfigDTO dto) {
        // ========== 针对【平台运营、618大促、面向商家】定制提示词 ==========
        String systemPrompt = """
                你是零售电商平台营销专家，当前是重点日期或节日的大促活动，服务对象是入驻商家，活动ID是数据入库时自动生成的。
                请从4个维度分析活动配置，输出JSON数组，每条包含 type 和 content：
                1. 商家毛利风险：优惠规则是否会导致商家亏损？给出建议。
                2. 商家参与吸引力：规则对不同类目商家是否友好？
                3. 合规性检查：文案、规则是否有广告法违规词、歧义。
                4. 运营风险：活动时间、预算、类目配置是否存在隐患。
                
                type 取值仅限：success / warning / info / error
                严格只返回JSON数组，不要额外解释。
                """;

        // 拼接用户输入
        String userContent = "当前大促活动配置：" + dto.toString();

        try {
            // 👇 和你成功的Service 完全一致的调用方式
            Generation gen = new Generation();
            Message sysMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content(systemPrompt)
                    .build();
            Message userMsg = Message.builder()
                    .role(Role.USER.getValue())
                    .content(userContent)
                    .build();

            // 标准化构建参数（修复原代码的写法问题）
            GenerationParam param = GenerationParam.builder()
                    .apiKey(apiKey)
                    .model("qwen-plus")
                    .messages(Arrays.asList(sysMsg, userMsg))
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .build();

            // 调用千问接口
            GenerationResult result = gen.call(param);
            String aiJson = result.getOutput().getChoices().get(0).getMessage().getContent();
            log.info("AI规则校验返回结果：{}", aiJson);

            // 解析JSON
            return objectMapper.readValue(aiJson, new TypeReference<List<AiCheckResultDTO>>() {});

        }
        // 👇 捕获所有千问SDK异常（和成功Service对齐）
        catch (ApiException | NoApiKeyException | InputRequiredException e) {
            log.error("AI活动规则校验调用千问接口异常", e);
            throw new RuntimeException("AI服务调用失败：" + e.getMessage());
        }
        // 捕获JSON解析异常
        catch (Exception e) {
            log.error("AI返回结果解析失败", e);
            throw new RuntimeException("AI结果解析失败，请重试");
        }
    }
}