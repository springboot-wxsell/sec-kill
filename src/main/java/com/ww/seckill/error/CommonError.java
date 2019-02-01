package com.ww.seckill.error;

/**
 * @author WangWei
 * @Title: CommonError
 * @ProjectName sec-kill
 * @date 2019/2/1 20:52
 * @description: 通用错误接口
 */
public interface CommonError {

    int getErrCode();

    String  getErrMsg();

    CommonError setErrMsg(String errMsg);
}
