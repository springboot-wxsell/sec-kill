package com.ww.seckill.service.impl;

import com.ww.seckill.dao.UserDOMapper;
import com.ww.seckill.dao.UserPasswordDOMapper;
import com.ww.seckill.dataobject.UserDO;
import com.ww.seckill.dataobject.UserPasswordDO;
import com.ww.seckill.service.UserService;
import com.ww.seckill.service.model.UserModel;
import cucumber.api.java.cs.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author WangWei
 * @Title: UserServiceImpl
 * @ProjectName sec-kill
 * @date 2019/1/31 20:54
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);

        if (userDO == null) {
            return null;
        }

        // 通过用户id获取对应的用户加密密码信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO, userPasswordDO);
    }


    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
        UserModel userModel = new UserModel();
        if (StringUtils.isEmpty(userDO)) {
            return null;
        }
        BeanUtils.copyProperties(userDO, userModel);

        if (!StringUtils.isEmpty(userPasswordDO)) {
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }
}
