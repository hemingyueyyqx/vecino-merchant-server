package org.example.vecinomerchantserver.component;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
@OpenAPIDefinition(
        info = @Info(
                title = "API Documentation",
                version = "1.0.0",
                description = "API documentation for Laboratory Booking Management System"
        )
)
@Configuration

public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme token = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                // 按约定命名的header中属性名称
                .name("token")
                .in(SecurityScheme.In.HEADER);
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(HttpHeaders.AUTHORIZATION);
        return new OpenAPI()
//                .info(new Info().title("API Documentation")
//                        .version("1.0.0")
//                        .description("API documentation for Laboratory Booking Management System"))
                .schemaRequirement(HttpHeaders.AUTHORIZATION, token)
                // 在全局请求添加header信息
                .addSecurityItem(securityRequirement);
    }
}
