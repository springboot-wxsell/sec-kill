package com.ww.seckill.controller;

import com.ww.seckill.controller.viewobject.UserVO;
import com.ww.seckill.error.EmBusinessError;
import com.ww.seckill.exception.BusinessException;
import com.ww.seckill.response.CommonReturnType;
import com.ww.seckill.service.UserService;
import com.ww.seckill.service.model.UserModel;
import com.ww.seckill.utils.MD5Encrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author WangWei
 * @Title: UserController
 * @ProjectName sec-kill
 * @date 2019/1/31 20:49
 * @description:
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam("telephone") String telephone,
                                  @RequestParam("password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // 入参校验
        if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // 用户登录服务，校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telephone, MD5Encrypt.encodeByMD5(password));

        // 将登录凭证加入到用户登录成功的session内
        this.request.getSession().setAttribute("IS_LOGIN", true);
        this.request.getSession().setAttribute("LOGIN_USER", userModel);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam("telephone") String telephone,
                                     @RequestParam("otpCode") String otpCode,
                                     @RequestParam("name") String name,
                                     @RequestParam("gender") Byte gender,
                                     @RequestParam("age") Integer age,
                                     @RequestParam("password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // 验证手机号和对应的otpcode 相符合
        String inSessionOtpCode = (String) request.getSession().getAttribute(telephone);
        if (!com.alibaba.druid.util.StringUtils.equals(otpCode, inSessionOtpCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }
        // 用户注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setTelephone(telephone);
        userModel.setEncrptPassword(MD5Encrypt.encodeByMD5(password));
        userModel.setRegisterMode("byPhone");
        userService.register(userModel);

        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/getOtp", method = RequestMethod.POST, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    // 用户获取otp短信
    public CommonReturnType getOtp(@RequestParam("telephone") String telephone) {
        // 按照一定的规则生成OTP验证码
        Random num = new Random();
        int randomInt = num.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        // 将OTP验证码对应用户手机关联,使用httpsession 的方式绑定他的手机号和otpCode
        request.getSession().setAttribute(telephone, otpCode);

        // 将OTP验证码通过短信通道发送给用户
        System.out.println("telephone: " + telephone + "&otpCode:" + otpCode);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam("id") Integer id) throws BusinessException {
        // 获取用户信息返回给前端
        UserModel userModel = userService.getUserById(id);
        if (userModel == null) {
            //userModel.setEncrptPassword("123");
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        // 将核心领域模型用户对象转化为可供UI使用的 ViewObject
        UserVO userVO = convertFromModel(userModel);
        // 返回通用对象
        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel) {
        UserVO userVO = new UserVO();
        if (StringUtils.isEmpty(userModel)) {
            return null;
        }
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }

}
