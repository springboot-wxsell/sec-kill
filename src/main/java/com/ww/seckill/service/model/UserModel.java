package com.ww.seckill.service.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotNull(message = "性别不能不填写")
    private Byte gender;

    @NotNull(message = "年龄不能不填写")
    @Min(value = 0, message = "年龄必须大于0")
    @Max(value = 150, message = "年龄必须小于150")
    private Integer age;

    @NotBlank(message = "手机号不能为空")
    private String telephone;

    private String registerMode;

    private String thirdPartyId;

    @NotBlank(message = "密码不能为空")
    private String encrptPassword;
}
