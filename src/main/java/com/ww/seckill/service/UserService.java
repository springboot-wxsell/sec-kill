package com.ww.seckill.service;

import com.ww.seckill.dataobject.UserDO;
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
}
