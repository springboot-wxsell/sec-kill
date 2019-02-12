package com.ww.seckill.service;

import com.ww.seckill.exception.BusinessException;
import com.ww.seckill.service.model.OrderModel;

/**
 * Created by wangwei on 2019/2/11.
 */
public interface OrderService {
    OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;
}
