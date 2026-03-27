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
public class Address {
    @Id
    @CreatedBy
    private String id;
    private String customerId;
    private String address;
    @ReadOnlyProperty
    private LocalDateTime createTime;
    @ReadOnlyProperty
    private LocalDateTime updateTime;
}
