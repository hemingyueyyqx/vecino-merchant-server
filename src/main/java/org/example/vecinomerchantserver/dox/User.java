package org.example.vecinomerchantserver.dox;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class User {
    public static final String ADMIN = "Js09";
    public static final String BUSINESS = "Sj08";
    public static final String CUSTOMER = "lM07";
    @Id
    @CreatedBy
    private String id;
    private String phone;
    private String account;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String nickname;
    private String role;
    @ReadOnlyProperty
    private LocalDateTime createTime;
    @ReadOnlyProperty
    private LocalDateTime updateTime;
}
