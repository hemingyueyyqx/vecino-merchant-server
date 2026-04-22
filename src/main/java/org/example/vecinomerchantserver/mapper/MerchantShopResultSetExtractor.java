package org.example.vecinomerchantserver.mapper;

import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.dto.MerchantShop;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MerchantShopResultSetExtractor implements ResultSetExtractor<List<MerchantShop>> {
@Override
    public List<MerchantShop> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<MerchantShop> merchantShops = new ArrayList<>();
        while (rs.next()) {

            MerchantShop merchantShop = MerchantShop.builder()
                    .userId(rs.getString("u.id"))
                    .shopId(rs.getString("s.id"))
                    .nickname(rs.getString("nickname"))
                    .shopName(rs.getString("shop_name"))
                    .account(rs.getString("account"))
                    .legalPerson(rs.getString("legal_person"))
                    .phone(rs.getString("phone"))
                    .shopType(rs.getString("shop_type"))
                    .address(rs.getString("address"))
                    .businessLicense(rs.getString("business_license"))
                    .status(rs.getInt("status"))
                    .auditReason(rs.getString("audit_reason"))
                    .updateTime(rs.getObject("update_time", LocalDateTime.class))
                    .build();
            merchantShops.add(merchantShop);
        }
        return merchantShops;
    }
}
