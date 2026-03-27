package org.example.vecinomerchantserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.exception.Code;
import org.example.vecinomerchantserver.exception.XException;
import org.example.vecinomerchantserver.repository.UserRepository;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //通过账号查找用户
    public User getUser(String account) {
        return userRepository.findByAccount(account);
    }
//    注册
    @Transactional
    public ResultVo register(User user) {
        if(userRepository.findByAccount(user.getAccount()) != null) {
            return ResultVo.builder().code(Code.ERROR).message("账号已存在！请重新注册").build();
        }
        User u = User.builder()
                .nickname(user.getNickname())
                .account(user.getAccount())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .phone(user.getPhone())
                .build();
        userRepository.save(u);
        return ResultVo.ok();
    }
//    重置密码
    @Transactional
    public ResultVo resetPassword(User user) {
        User u = userRepository.findByAccount(user.getAccount());
        if(u == null) {
            return ResultVo.builder().code(Code.ERROR).message("账号不存在！").build();
        }
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(u);
        return ResultVo.ok();
    }

}
