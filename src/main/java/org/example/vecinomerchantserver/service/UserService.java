package org.example.vecinomerchantserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.exception.Code;
import org.example.vecinomerchantserver.exception.XException;
import org.example.vecinomerchantserver.repository.ShopInfoRepository;
import org.example.vecinomerchantserver.repository.UserRepository;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShopInfoRepository shopInfoRepository;

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
        if(userRepository.findByPhone(user.getPhone()) != null) {
            return ResultVo.builder().code(Code.ERROR).message("手机号已存在！请重新注册").build();
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
//    查找当前用户是否有店铺以及店铺状态
    @Transactional
    public Map<String,Object> findShop(String uid) {
        Map<String,Object> map = new HashMap<>();
        ShopInfo shopInfo = shopInfoRepository.findByBusinessId(uid);
        if(shopInfo == null) {
            map.put("hasShop",false);
        }else {
            map.put("hasShop",true);
            map.put("shopInfo",shopInfo);
        }
        return map;
    }

}
