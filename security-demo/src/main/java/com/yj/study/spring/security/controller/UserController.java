package com.yj.study.spring.security.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.yj.study.spring.security.dto.User;
import com.yj.study.spring.security.dto.UserQueryCondition;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @JsonView(User.UserSimpleView.class)
    @GetMapping
    public List<User> query(UserQueryCondition condition, @PageableDefault(page = 1, size = 5, sort = "username,desc") Pageable pageable) {
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(ReflectionToStringBuilder.toString(pageable, ToStringStyle.MULTI_LINE_STYLE));
        User user = new User();
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user);
        userList.add(user);
        return userList;
    }

    @JsonView(User.UserDetailView.class)
    //正则限制: 只能接受一个数字
    @GetMapping(value = "/{id:\\d+}")
    public User getInfo(@PathVariable Long id) {
        User user = new User();
        user.setId(id);
        user.setUsername("tom");
        return user;
    }


}
