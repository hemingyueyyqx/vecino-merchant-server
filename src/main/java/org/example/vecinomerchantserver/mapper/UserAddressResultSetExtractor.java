package org.example.vecinomerchantserver.mapper;

import org.example.vecinomerchantserver.dto.UserAddress;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserAddressResultSetExtractor implements ResultSetExtractor<List<UserAddress>> {
    @Override
    public List<UserAddress> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<UserAddress> userAddresses = new ArrayList<>();
        while (rs.next()) {
            UserAddress userAddress = UserAddress.builder()
                    .userId(rs.getString("u.id"))
                    .address(rs.getString("address"))
                    .phone(rs.getString("phone"))
                    .nickname(rs.getString("nickname"))
                    .addressId(rs.getString("a.id"))
                    .updateTime(rs.getObject("a.update_time", LocalDateTime.class))
                    .build();
                    userAddresses.add(userAddress);
        }
        return userAddresses;
    }
}
