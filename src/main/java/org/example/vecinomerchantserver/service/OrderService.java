package org.example.vecinomerchantserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.Order;
import org.example.vecinomerchantserver.dox.ProductSku;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.exception.Code;
import org.example.vecinomerchantserver.exception.XException;
import org.example.vecinomerchantserver.repository.OrderRepository;
import org.example.vecinomerchantserver.repository.ProductSkuRepository;
import org.example.vecinomerchantserver.repository.ShopInfoRepository;
import org.example.vecinomerchantserver.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ShopInfoRepository shopInfoRepository;
    private final ProductSkuRepository productSkuRepository;
    public static String createOrderNo(){
        // 年月日时分秒
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(new Date());
        // 4位随机数
        int random = (int)(Math.random() * 9000) + 1000;
        return "LS" + time + random;
    }
//    添加订单
    @Transactional
    public void addOrder(String uid, Order order) {
        User user = userRepository.findById(uid).orElse(null);
        if (user == null) {
            throw XException.builder().code(Code.UNAUTHORIZED).build();
        }

        String orderNo = createOrderNo();

        Order newOrder = Order.builder()
                .orderNo(orderNo)
                .customerId(uid)
                .shopId(order.getShopId())
                .shopName(order.getShopName())
                .spuId(order.getSpuId())
                .spuName(order.getSpuName())
                .mainImage(order.getMainImage())
                .skuId(order.getSkuId())
                .specAttr(order.getSpecAttr())
                .price(order.getPrice())
                .quantity(order.getQuantity())
                .totalAmount(order.getTotalAmount())
                .address(order.getAddress())
                .orderStatus(1)
                .build();

        user.setBalance(user.getBalance() - order.getTotalAmount());
        userRepository.save(user);
        ProductSku productSku = productSkuRepository.findProductSkuById(order.getSkuId());
        productSku.setStockNum(productSku.getStockNum() - order.getQuantity());
        productSkuRepository.save(productSku);
        orderRepository.save(newOrder);

        log.info("订单创建成功: orderId={}, orderNo={}, customerId={}, totalAmount={}",
                 orderNo, uid, order.getTotalAmount());
    }
//    获取订单列表
    @Transactional
    public List<Order> getOrderList(String uid, String role) {
        if(role.equals(User.CUSTOMER)){
            return orderRepository.findAllByCustomerId(uid);
        }else{
            ShopInfo shopInfo = shopInfoRepository.findByBusinessId(uid);
            return orderRepository.findAllByShopId(shopInfo.getId());
        }

    }
//    取消订单
    @Transactional
    public void cancelOrder(String uid, String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        User user = userRepository.findById(uid).orElse(null);
        user.setBalance(user.getBalance() + order.getTotalAmount());
        userRepository.save(user);
        ProductSku productSku = productSkuRepository.findProductSkuById(order.getSkuId());
        productSku.setStockNum(productSku.getStockNum() + order.getQuantity());
        productSkuRepository.save(productSku);
        order.setOrderStatus(0);
        orderRepository.save(order);
    }
//    确认收货
    @Transactional
    public void confirmOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        order.setOrderStatus(4);
        orderRepository.save(order);
    }
//    修改订单状态
    @Transactional
    public void updateOrderStatus(String orderId,Integer status) {
        Order oldOrder = orderRepository.findById(orderId).orElse(null);
        oldOrder.setOrderStatus(status);
        orderRepository.save(oldOrder);
    }
}
