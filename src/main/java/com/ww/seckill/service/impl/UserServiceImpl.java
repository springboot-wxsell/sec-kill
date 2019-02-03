package com.ww.seckill.service.impl;

import com.ww.seckill.dao.UserDOMapper;
import com.ww.seckill.dao.UserPasswordDOMapper;
import com.ww.seckill.dataobject.UserDO;
import com.ww.seckill.dataobject.UserPasswordDO;
import com.ww.seckill.error.EmBusinessError;
import com.ww.seckill.exception.BusinessException;
import com.ww.seckill.service.UserService;
import com.ww.seckill.service.model.UserModel;
import com.ww.seckill.validator.ValidationResult;
import com.ww.seckill.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private ValidatorImpl validator;

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

    @Override
    public UserModel validateLogin(String telephone, String encryptPassword) throws BusinessException {
        // 通过用户手机获取用户信息
        UserDO userDO = userDOMapper.selectByTelephone(telephone);
        if (userDO == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        // 获取用户密码信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

        UserModel userModel = convertFromDataObject(userDO, userPasswordDO);

        // 比对用户信息内加密的密码是否和传输进来的密码相匹配
        if (org.apache.commons.lang3.StringUtils.equals(encryptPassword, userModel.getEncrptPassword())) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    @Transactional
    @Override
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        /*
        if (StringUtils.isEmpty(userModel.getName()) || userModel.getGender() == null ||
                userModel.getAge() == null || StringUtils.isEmpty(userModel.getTelephone())) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        */
        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrorMsg());
        }
        UserDO userDO = convertFromModel(userModel);
        userDOMapper.insertSelective(userDO);
        try {
            userModel.setId(userDO.getId());
        }catch (DuplicateKeyException e) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号已重复注册");
        }
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    // model -> dataobject
    private UserDO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }


    // dataobject -> model
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
