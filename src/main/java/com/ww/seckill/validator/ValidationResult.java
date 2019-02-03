package com.ww.seckill.validator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * Created by wangwei on 2019/2/4.
 */
@Data
public class ValidationResult {

    // 检验结果是否有错
    private boolean hasErrors = false;

    // 存放错误信息的map
    private HashMap<String, Object> errorMsgMap = new HashMap<>();

    // 实现通用的通过格式化字符串信息获取错误结果的msg方法
    public String getErrorMsg() {
        return StringUtils.join(errorMsgMap.values().toArray(), ",");
    }
}
