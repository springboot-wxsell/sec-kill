package com.ww.seckill.response;

import lombok.Data;

/**
 * @author WangWei
 * @Title: CommonReturnType
 * @ProjectName sec-kill
 * @date 2019/2/1 17:29
 * @description:
 */
@Data
public class CommonReturnType {

    // 表明对应的返回处理结果 'success' 或 'fail'
    private String status;

    // 若status=success, 则 data 内返回前端需要的json数据
    // 若status=fail, 则data内使用通用的错误码格式
    private Object data;

    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(result, "success");
    }

    public static CommonReturnType create(Object result, String status) {
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }
}
