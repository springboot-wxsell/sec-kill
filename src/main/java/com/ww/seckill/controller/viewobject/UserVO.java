package com.ww.seckill.controller.viewobject;

import lombok.Data;

/**
 * @author WangWei
 * @Title: UserVO
 * @ProjectName sec-kill
 * @date 2019/1/31 21:33
 * @description:
 */
@Data
public class UserVO {

    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telephone;
}
