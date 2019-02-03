package com.ww.seckill.service;

import com.ww.seckill.dataobject.UserDO;
import com.ww.seckill.exception.BusinessException;
import com.ww.seckill.service.model.UserModel;

/**
 * @author WangWei
 * @Title: UserService
 * @ProjectName sec-kill
 * @date 2019/1/31 20:53
 * @description:
 */
public interface UserService {

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    UserModel getUserById(Integer id);

    /**
     * 用户注册
     * @param userModel
     */
    void register(UserModel userModel) throws BusinessException;

    /**
     * 用户登录
     * @param telephone : 用户注册的手机
     * @param encryptPassword : 加密后的密码
     * @return UserModel
     */
    UserModel validateLogin(String telephone, String encryptPassword) throws BusinessException;
}
