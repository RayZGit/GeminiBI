package com.rayzhou.springboottemplate.model.dto.user;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
