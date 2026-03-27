package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.service.UserService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class RegisterController {
    private final UserService userService;
    @PostMapping("register")
    public ResultVo register(@RequestBody User user) {
        return userService.register(user);
    }
}
