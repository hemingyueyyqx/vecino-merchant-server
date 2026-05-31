package org.example.vecinomerchantserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityConfigDTO {
    @Id
    @CreatedBy
    private String activityId;
    private String activityName;
    private String activityTheme;
    private String activityType;
    private String startTime;
    private String endTime;
    private String fullReductionRule;
    private List<String> targetCategory;
    private String targetMerchantType;
    private Integer budget;
    private String activityDesc;
}
