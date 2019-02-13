package com.ww.seckill.service;

import com.ww.seckill.service.model.PromoModel;

/**
 * Created by wangwei on 2019/2/12.
 */
public interface PromoService {

    /**
     * 根据itemId获取即将进行或正在进行的秒杀活动
     * @param itemId
     * @return
     */
    PromoModel getPromoByItemId(Integer itemId);
}
