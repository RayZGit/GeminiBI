package com.rayzhou.springboottemplate.service;

import com.rayzhou.springboottemplate.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Ray
* @createDate 2024-04-25 01:39:02
*/
public interface UserService extends IService<User> {

    /**
     * User Registration
     *
     * @param userAccount   user account
     * @param userPassword  user password
     * @param checkPassword checking password
     * @return new user id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

}
