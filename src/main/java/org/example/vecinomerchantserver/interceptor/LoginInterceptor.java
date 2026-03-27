package org.example.vecinomerchantserver.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.vecinomerchantserver.component.JWTComponent;
import org.example.vecinomerchantserver.exception.Code;
import org.example.vecinomerchantserver.exception.XException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    public final JWTComponent jwtComponent;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null) {
            throw XException.builder().code(Code.UNAUTHORIZED).build();
        }
        DecodedJWT decoded = jwtComponent.decode(token);
        String uid = decoded.getClaim("uid").asString();
        String role = decoded.getClaim("role").asString();
        request.setAttribute("uid", uid);
        request.setAttribute("role", role);
        return true;

    }



}
