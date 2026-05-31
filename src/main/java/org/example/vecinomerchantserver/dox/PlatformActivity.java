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
public class PlatformActivity {
    @Id
    @CreatedBy
    private String activityId;
    private String activityName;
    private String activityTheme;
    private String activityType;
    private String startTime; // 数据库datetime对应LocalDateTime
    private String endTime;
    private String fullReductionRule;
    private String targetCategory; // 逗号分隔的类目列表
    private String targetMerchantType;
    private Integer budget;
    private String activityDesc;
    @ReadOnlyProperty
    private LocalDateTime createTime;
    @ReadOnlyProperty
    private LocalDateTime updateTime;
}
