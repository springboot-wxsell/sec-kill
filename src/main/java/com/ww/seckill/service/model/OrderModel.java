package com.ww.seckill.service.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by wangwei on 2019/2/11.
 */
// 用户下单交易模型
@Data
public class OrderModel {

    private String id;

    // 购买的用户id
    private Integer userId;

    // 商品id
    private Integer itemId;

    // 若非空，则表示以秒杀商品方式下单
    private Integer promoId;

    // 商品单价, 若promoId非空，则表示秒杀商品价格
    private BigDecimal itemPrice;

    // 商品数量
    private Integer amount;

    // 购买金额, 若promoId非空，则表示秒杀商品购买金额
    private BigDecimal orderAmount;


}
