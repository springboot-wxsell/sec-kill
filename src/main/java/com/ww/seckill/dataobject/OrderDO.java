package com.ww.seckill.dataobject;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDO {
    private String id;

    private Integer userId;

    private Integer itemId;

    private Integer promoId;

    private BigDecimal itemPrice;

    private Integer amount;

    private BigDecimal orderAmount;

}