package com.ww.seckill.service.impl;

import com.ww.seckill.dao.PromoDOMapper;
import com.ww.seckill.dataobject.PromoDO;
import com.ww.seckill.service.PromoService;
import com.ww.seckill.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangwei on 2019/2/12.
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        // 获取对应商品的秒杀活动信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        // dataobject -> model
        PromoModel promoModel = convertModelFromDO(promoDO);
        if (promoModel == null) {
            return null;
        }
        // 判断当前时间是否秒杀活动即将开始或者正在进行
        if (promoModel.getStartDate().isAfterNow()) {
            promoModel.setStatus(1);
        }else if (promoModel.getEndDate().isBeforeNow()) {
            promoModel.setStatus(3);
        }else{
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    private PromoModel convertModelFromDO(PromoDO promoDO) {
        if (promoDO == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO, promoModel);
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }
}
