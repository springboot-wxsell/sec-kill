package com.ww.seckill.error;

/**
 * @author WangWei
 * @Title: EmBusinessError
 * @ProjectName sec-kill
 * @date 2019/2/1 20:55
 * @description:
 */
public enum EmBusinessError implements CommonError {

    // 通用的错误类型 10000
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    UNKNOWN_ERROR(10002, "未知错误"),

    // 20001开头为用户信息相关错误定义
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_FAIL(20002, "用户手机号或密码不正确"),
    USER_NOT_LOGIN(20003, "用户还未登录"),
    // 20011开头为商品信息相关错误定义
    ITEM_NOT_EXIST(20011, "商品信息不存在"),
    ITEM_AMOUNT_ERROR(20012, "商品数量不合法"),
    // 20021开头为交易信息相关错误定义
    STOCK_NOT_ENOUGH(20021, "库存不足"),
    ;

    private int errCode;

    private String errMsg;

    private EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
