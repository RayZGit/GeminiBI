package com.rayzhou.springboottemplate.model.dto.user;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserAddRequest implements Serializable {

    private String userName;

    private String userAccount;

    private String userAvatar;

    private String userRole;

    private static final long serialVersionUID = 1L;
}