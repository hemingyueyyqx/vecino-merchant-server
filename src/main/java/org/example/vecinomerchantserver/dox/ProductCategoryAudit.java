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
public class ProductCategoryAudit {
    @Id
    @CreatedBy
    private String id;
    private String spuId;
    private String oldCategoryId;
    private String newCategoryId;
    private String applyUserId;
    private String auditUserId;
    private Integer auditStatus;
    private String auditRemark;
    private LocalDateTime auditTime;
    @ReadOnlyProperty
    private LocalDateTime createTime;
    @ReadOnlyProperty
    private LocalDateTime updateTime;
}
