package com.yj.study.spring.security.validator;

import com.yj.study.spring.security.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

    //可使用spring的注入
    @Autowired
    private HelloService helloService;

    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("my validator init");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(value);
        System.out.println(helloService.greeting("amen"));
        System.out.println("value: " + value);
        return false;
    }
}
