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
public class ProductSpu {
    @Id
    @CreatedBy
    private String id;
    private String shopId;
    private String spuName;
    private String categoryId;
    private String mainImage;
    private String detail;
    private Integer auditStatus;
    private String auditRemark;
    private Integer status;
    @ReadOnlyProperty
    private LocalDateTime createTime;
    @ReadOnlyProperty
    private LocalDateTime updateTime;
}
