package com.ww.seckill.service.impl;

import com.ww.seckill.dao.OrderDOMapper;
import com.ww.seckill.dao.SequenceDOMapper;
import com.ww.seckill.dataobject.OrderDO;
import com.ww.seckill.dataobject.SequenceDO;
import com.ww.seckill.error.EmBusinessError;
import com.ww.seckill.exception.BusinessException;
import com.ww.seckill.service.ItemService;
import com.ww.seckill.service.OrderService;
import com.ww.seckill.service.UserService;
import com.ww.seckill.service.model.ItemModel;
import com.ww.seckill.service.model.OrderModel;
import com.ww.seckill.service.model.UserModel;
import com.ww.seckill.utils.KeyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by wangwei on 2019/2/11.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;
    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        // 1.校验下单状态， 下单的商品是否存在， 用户是否合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.ITEM_NOT_EXIST);
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.ITEM_AMOUNT_ERROR);
        }

        // 校验活动信息
        if (promoId == null) {
            // a. 校验对应的活动是否存在这个适用的商品
            if (promoId.intValue() != itemModel.getId()) {
                throw new BusinessException(EmBusinessError.PROMO_INFO_ERROR);
            }
            // b. 校验活动是否正在进行中
            if (itemModel.getPromoModel().getStatus().intValue() != 2) {
                throw new BusinessException(EmBusinessError.PROMO_NOT_STARTED);
            }
        }

        // 2. 落单减库存，支付减库存
        boolean result = itemService.decreaseStock(amount, itemId);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        // 3. 订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if (promoId != null) {
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setPromoId(promoId);
        orderModel.setOrderAmount(orderModel.getItemPrice().multiply(new BigDecimal(amount)));

        // 生成交易流水号，订单号
        orderModel.setId(KeyUtils.genUniqueKey(genSequence()));
        OrderDO orderDO = convertDOFromModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        // 加上商品销量
        itemService.increaseSales(amount, itemId);
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String genSequence() {
        StringBuffer sb = new StringBuffer();
        // 获取当前的sequence
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.selectByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKey(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            sb.append(0);
        }
        return sb.append(sequenceStr).toString();
    }

    private OrderDO convertDOFromModel(OrderModel orderModel) {
            if (orderModel == null) {
                return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        return orderDO;
    }
}
