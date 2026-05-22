package org.example.vecinomerchantserver.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    public static final Integer HAOPING = 1;
    public static final Integer ZHONGPING = 2;
    public static final Integer CHAPING = 3;
    @Id
    @CreatedBy
    private String reviewId;
    private String orderId;
    private String orderNo;
    private String customerId;
    private String shopId;
    private String nickname;
    private String image;
    private String content;
    private Integer reviewType;
    private String analysis;
    private String replyContent;
    @ReadOnlyProperty
    private LocalDateTime createTime;
    @ReadOnlyProperty
    private LocalDateTime updateTime;
}
