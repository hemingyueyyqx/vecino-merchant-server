package org.example.vecinomerchantserver.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.exception.Code;
import org.example.vecinomerchantserver.exception.XException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
//JSON Web Token
public class JWTComponent {
    @Value("${my.secretkey}")//作为 JWT 签名的密钥
    private String secretkey;
   //用于存储 JWT 签名算法的实例，这里使用的是 HMAC256 算法。
    private Algorithm algorithm;

   //这是 Java EE 提供的注解，被该注解标注的方法会在 Bean 初始化完成后自动调用
    @PostConstruct
    //init() 方法：在方法中使用读取到的 secretkey 初始化 Algorithm 实例，后续生成和验证 JWT 时会使用该算法。
    private void init() {
        algorithm = Algorithm.HMAC256(secretkey);
    }


    //接受一个 Map<String, Object> 类型的参数 map，该参数将作为 JWT 的负载（Payload）
    public String encode(Map<String, Object> map) {
        // 1天过期
        LocalDateTime time = LocalDateTime.now().plusDays(1);
        //JWT 生成：使用 JWT.create() 方法创建一个 JWT 构建器，
        // 通过 withPayload() 方法设置负载，
        // withIssuedAt() 方法设置签发时间，
        // withExpiresAt() 方法设置过期时间，
        // 最后使用 sign() 方法使用之前初始化的算法进行签名，生成 JWT 字符串并返回。
        return JWT.create()
                .withPayload(map)
                .withIssuedAt(new Date())
                //atZone() 是 LocalDateTime 类的一个方法，用于将 LocalDateTime 对象转换为 ZonedDateTime 对象。ZonedDateTime 表示带有时区信息的日期和时间。
                //ZoneId.systemDefault() 用于获取当前系统默认的时区。所以 time.atZone(ZoneId.systemDefault()) 的作用是将 time 这个 LocalDateTime 对象转换为带有系统默认时区信息的 ZonedDateTime 对象
                //toInstant() 是 ZonedDateTime 类的一个方法，它将 ZonedDateTime 对象转换为 Instant 对象。Instant 表示一个精确到纳秒的时间戳，是一个与时区无关的时间点，通常用于表示某个具体的时刻。
//        from() 是 Date 类的一个静态方法，用于将 Instant 对象转换为 Date 对象
                .withExpiresAt(Date.from(time.atZone(ZoneId.systemDefault()).toInstant()))
                .sign(algorithm);
    }

    public DecodedJWT decode(String token) {
        try {
            //方法参数：接受一个 JWT 字符串 token 作为参数。
            //验证过程：使用 JWT.require(algorithm).build().verify(token) 方法对传入的 JWT 进行验证，如果验证通过，返回一个 DecodedJWT 对象，该对象包含了解码后的 JWT 信息。
            //异常处理：如果验证过程中抛出 TokenExpiredException 或 SignatureVerificationException 异常，根据异常类型抛出不同的自定义异常 XException，分别表示令牌过期和签名验证失败。
            return JWT.require(algorithm).build().verify(token);
        } catch (TokenExpiredException | SignatureVerificationException e) {
            if (e instanceof SignatureVerificationException) {
                throw XException.builder().code(Code.FORBIDDEN).build();
            }
            throw XException.builder().code(Code.TOKEN_EXPIRED).build();
        }
    }

}
