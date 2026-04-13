package org.example.vecinomerchantserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.repository.ShopInfoRepository;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopService {
    private final ShopInfoRepository shopInfoRepository;
    //    店铺入驻申请
    @Transactional
    public ResultVo apply(ShopInfo shopInfo, String uid) {
        ShopInfo shopInfo1 = ShopInfo.builder()
                .shopName(shopInfo.getShopName())
                .shopType(shopInfo.getShopType())
                .businessId(uid)
                .address(shopInfo.getAddress())
                .businessLicense(shopInfo.getBusinessLicense())
                .legalPerson(shopInfo.getLegalPerson())
                .status(2)
                .build();
        shopInfoRepository.save(shopInfo1);
        return ResultVo.ok();
    }

}
