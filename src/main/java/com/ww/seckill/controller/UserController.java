package com.ww.seckill.controller;

import com.ww.seckill.controller.viewobject.UserVO;
import com.ww.seckill.service.UserService;
import com.ww.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author WangWei
 * @Title: UserController
 * @ProjectName sec-kill
 * @date 2019/1/31 20:49
 * @description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    @ResponseBody
    public UserVO getUser(@RequestParam("id") Integer id) {
        // 获取用户信息返回给前端
        UserModel userModel = userService.getUserById(id);
        // 将核心领域模型用户对象转化为可供UI使用的 ViewObject
        return convertFromModel(userModel);
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
