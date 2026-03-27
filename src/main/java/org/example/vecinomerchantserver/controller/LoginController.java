package org.example.vecinomerchantserver.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.component.JWTComponent;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.exception.Code;
import org.example.vecinomerchantserver.service.UserService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class LoginController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTComponent jwtComponent;
    @PostMapping("login")
    public ResultVo login(@RequestBody User user, HttpServletResponse response) {
//        String account = loginData.get("account");
//        String password = loginData.get("password");

        User userR = userService.getUser(user.getAccount());
        if (userR == null || !passwordEncoder.matches(user.getPassword(), userR.getPassword())) {
            return ResultVo.error(Code.LOGIN_ERROR);
        }
        String token = jwtComponent.encode(Map.of("uid", userR.getId(), "role", userR.getRole()));
        response.setHeader("token", token);
        response.setHeader("role", userR.getRole());
        return ResultVo.success(userR);
    }
    @PostMapping("resetPassword")
    public ResultVo resetPassword(@RequestBody User user) {
        return userService.resetPassword(user);
    }
}
