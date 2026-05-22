package org.example.vecinomerchantserver.service;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.Order;
import org.example.vecinomerchantserver.dox.Review;
import org.example.vecinomerchantserver.repository.OrderRepository;
import org.example.vecinomerchantserver.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AIReviewAnalysis {
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    @Value("${my.aliyunkey}")
    private String apiKey;

    /**
     * AI评价分析：返回分析结果+智能回复双元素Map
     * @param reviewId 用户评价对象Id
     * @return key: AI分析结果、AI智能回复
     */
    public Map<String, String> aiAnalysis(String reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse( null);
        Map<String, String> resultMap = new HashMap<>();
        // 1. 查询订单，空值防护
        Order order = orderRepository.findById(review.getOrderId())
                .orElseThrow(() -> new RuntimeException("当前评价关联的订单不存在"));

        // 2. 提取核心参数
        String spuName = order.getSpuName();
        String specAttr = order.getSpecAttr();
        String content = review.getContent();
        String reviewType = review.getReviewType() == Review.HAOPING ? "好评" :
                review.getReviewType() == Review.ZHONGPING ? "中评" : "差评";

        // 3. 构造AI提示词（严格要求输出格式：分析结果|智能回复，各100字左右）
        String prompt = String.format("""
                你是即时零售行业的AI助手，请处理用户商品评价，严格按要求输出2段内容，用【|】分隔：
                商品信息：%s（规格：%s）
                评价类型：%s
                评价内容：%s
                
                要求1：AI分析结果（100字左右）：第一句话必须提取评价内容的关键词，然后对评价进行客观分析,最后一句是建议
                要求2：AI智能回复（100字左右）：以即时零售商家的口吻，礼貌回复用户评价，亲切自然
                禁止输出多余解释、标题，仅输出两段内容用|分隔
                """, spuName, specAttr, reviewType, content);

        try {
            // 4. 调用阿里云通义千问大模型（和你的标题优化服务完全一致）
            Generation gen = new Generation();
            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content("你是专业的即时零售评价分析助手，擅长分析用户评价并生成商家回复。")
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

            // 5. 获取并解析AI返回结果
            GenerationResult result = gen.call(param);
            String aiResponse = result.getOutput().getChoices().get(0).getMessage().getContent();
//            log.info("AI评价分析结果：{}", aiResponse);
            // 按分隔符分割结果
            String[] aiResults = aiResponse.split("\\|");
            if (aiResults.length >= 2) {
                resultMap.put("AI分析结果", aiResults[0].trim());
                resultMap.put("AI智能回复", aiResults[1].trim());
            } else {
                // 格式异常兜底
                setDefaultResult(resultMap);
            }

        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            log.error("AI评价分析调用大模型异常", e);
            // 异常兜底返回默认内容
            setDefaultResult(resultMap);
        }

        return resultMap;
    }

    /**
     * 异常/格式错误时的兜底返回
     */
    private void setDefaultResult(Map<String, String> map) {
        map.put("AI分析结果", "关键词：用户评价内容简略。本次未获取到有效评价信息，无法完成详细分析，请您补充评价内容后再次尝试。");
        map.put("AI智能回复", "亲，非常感谢您的惠顾！由于您的评价信息较少，我们没能完全理解您的想法。如果您有任何建议或需求，随时联系我们，我们会第一时间为您服务~");
    }
}