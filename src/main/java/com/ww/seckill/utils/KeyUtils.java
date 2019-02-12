package com.ww.seckill.utils;

import com.ww.seckill.dao.SequenceDOMapper;
import com.ww.seckill.service.OrderService;
import com.ww.seckill.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by wangwei on 2019/2/11.
 */
public class KeyUtils {

    @Autowired
    private OrderServiceImpl orderService;

    public static String genUniqueKey(String sequence) {
        StringBuffer sb = new StringBuffer();
        // 订单号16位
        // 前8位为时间信息，年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        sb.append(nowDate);
        // 中间6位为自增序列
        sb.append(sequence);
        // 最后两位为分库分表位，暂时写死
        sb.append("00");
        return sb.toString();
    }
}
