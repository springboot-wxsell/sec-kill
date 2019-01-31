package com.ww.seckill.service.model;

import lombok.Data;

/**
 * @author WangWei
 * @Title: UserModel
 * @ProjectName sec-kill
 * @date 2019/1/31 21:02
 * @description:
 */
@Data
public class UserModel {

    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telephone;

    private String registerMode;

    private String thirdPartyId;

    private String encrptPassword;
}
