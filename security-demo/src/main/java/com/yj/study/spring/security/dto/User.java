package com.yj.study.spring.security.dto;

import com.fasterxml.jackson.annotation.JsonView;

public class User {

    public interface UserSimpleView {}

    public interface UserDetailView extends UserSimpleView {}

    @JsonView(UserDetailView.class)
    private Long id;

    @JsonView(UserSimpleView.class)
    private String username;

    @JsonView(UserDetailView.class)
    private String password;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
