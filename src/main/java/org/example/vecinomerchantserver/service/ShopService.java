package org.example.vecinomerchantserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.exception.Code;
import org.example.vecinomerchantserver.repository.ShopInfoRepository;
import org.example.vecinomerchantserver.repository.UserRepository;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopService {
    private final ShopInfoRepository shopInfoRepository;
    private final UserRepository userRepository;

    //    店铺入驻申请
    @Transactional
    public ResultVo apply(ShopInfo shopInfo, String uid) {
        if(shopInfo.getId() != null && !shopInfo.getId().isEmpty()) {
            ShopInfo s = shopInfoRepository.findById(shopInfo.getId()).get();
            s.setShopName(shopInfo.getShopName());
            s.setFirstCategory(shopInfo.getFirstCategory());
            s.setSecondCategory(shopInfo.getSecondCategory());
            s.setAddress(shopInfo.getAddress());
            s.setBusinessLicense(shopInfo.getBusinessLicense());
            s.setLegalPerson(shopInfo.getLegalPerson());
            s.setStatus(2);
            s.setAuditReason("");
            shopInfoRepository.save(s);
        }else {
            ShopInfo shopInfo1 = ShopInfo.builder()
                    .shopName(shopInfo.getShopName())
                    .firstCategory(shopInfo.getFirstCategory())
                    .secondCategory(shopInfo.getSecondCategory())
                    .businessId(uid)
                    .address(shopInfo.getAddress())
                    .businessLicense(shopInfo.getBusinessLicense())
                    .legalPerson(shopInfo.getLegalPerson())
                    .auditReason("")
                    .status(2)
                    .build();
            shopInfoRepository.save(shopInfo1);
        }

        return ResultVo.ok();
    }
//    审核通过
    @Transactional
    public ResultVo auditPass(ShopInfo shopInfo) {
        ShopInfo shopInfo1 = shopInfoRepository.findById(shopInfo.getId()).get();
        shopInfo1.setStatus(0);
        shopInfoRepository.save(shopInfo1);
        return ResultVo.ok();
    }
//    审核驳回
    @Transactional
    public ResultVo auditReject(ShopInfo shopInfo) {
//        打印店铺信息
        log.info("shopInfo:{}",shopInfo);
        ShopInfo shopInfo1 = shopInfoRepository.findById(shopInfo.getId()).get();
        log.info("shopInfo1:{}",shopInfo1);
        shopInfo1.setStatus(1);
        shopInfo1.setAuditReason(shopInfo.getAuditReason());
        shopInfoRepository.save(shopInfo1);
        return ResultVo.ok();
    }

}
