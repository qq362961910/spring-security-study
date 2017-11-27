package com.jy.study.spring.security.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.jy.study.spring.security.dto.User;
import com.jy.study.spring.security.dto.UserQueryCondition;
import com.jy.study.spring.security.exception.UserNotException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @ApiOperation("用户查询服务")
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
    public User getInfo(@ApiParam("用户id") @PathVariable Long id) {
        User user = new User();
        user.setId(id);
        user.setUsername("tom");
        return user;
    }

    /**
     * 前后端传递birthday参数时都使用时间戳
     * @Valid启用参数校验与BindingResult配合使用
     * */
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {

//        if(errors.hasErrors()) {
//            errors.getAllErrors().forEach(System.out::println);
//        }

        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());
        user.setId(1L);
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user, BindingResult errors) {
        if(errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());
        user.setId(id);
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable("id") Long userId) {
        System.out.println("delete: " + userId);
    }

    @RequestMapping("/e")
    public void error() {
        throw new RuntimeException("server error");
    }

    @RequestMapping("/not_exist/{id:\\d+}")
    public User notExist(@PathVariable("id") Long userId) {
        throw new UserNotException(userId);
    }

}
