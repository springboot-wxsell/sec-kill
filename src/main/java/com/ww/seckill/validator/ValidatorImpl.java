package com.ww.seckill.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created by wangwei on 2019/2/4.
 */
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    // 实现校验方法并返回校验结果
    public ValidationResult validate(Object bean) {
        final ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if (constraintViolationSet.size() > 0) {
            // 有错误信息
            result.setHasErrors(true);
            // 遍历集合内所有的错误信息
            constraintViolationSet.forEach(constraintViolation -> {
                String errorMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName, errorMsg);
            });
        }
        return result;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        // 将 hibernate validator 通过工厂的初始化方式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
