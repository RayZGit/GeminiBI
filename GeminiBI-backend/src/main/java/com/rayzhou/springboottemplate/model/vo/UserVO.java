package com.rayzhou.springboottemplate.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVO implements Serializable {

    /**
     * id
     */
    private Long id;

    private String userName;

    private String userAvatar;

    private String userProfile;

    private String userRole;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}