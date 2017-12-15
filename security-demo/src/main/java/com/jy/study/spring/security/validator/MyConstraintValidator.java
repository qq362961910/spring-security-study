package com.jy.study.spring.security.validator;

import com.jy.study.spring.security.service.HelloService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {

    private HelloService helloService;

    public MyConstraintValidator(HelloService helloService) {
        this.helloService = helloService;
    }

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
