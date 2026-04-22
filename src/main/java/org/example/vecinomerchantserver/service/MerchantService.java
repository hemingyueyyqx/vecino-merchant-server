package org.example.vecinomerchantserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.dto.MerchantShop;
import org.example.vecinomerchantserver.exception.Code;
import org.example.vecinomerchantserver.repository.MerchantShopRepository;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantShopRepository merchantShopRepository;
    @Transactional
    public ResultVo findMerchantShop(String role) {
        if(!role.equals(User.ADMIN)){
            return ResultVo.builder().code(Code.FORBIDDEN.getCode()).message(Code.FORBIDDEN.getMessage()).build();
        }
        List<MerchantShop> merchants = merchantShopRepository.findMerchantShop();
        log.info("merchants:{}",merchants);
        return ResultVo.success( merchants);
    }
}
