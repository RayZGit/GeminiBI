package com.rayzhou.springboottemplate.service;

import com.rayzhou.springboottemplate.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rayzhou.springboottemplate.model.vo.LoginUserVO;
import com.rayzhou.springboottemplate.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * User Login
     *
     * @param userAccount  userAccount
     * @param userPassword userPassword
     * @param request
     * @return User info w/o PII
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * Get Current Login User
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * User Logout
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);


    /**
     * get User info w/o PII
     *
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    LoginUserVO getLoginUserVO(User user);

    /**
     * Check if it's Admin
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * Check if it's Admin
     *
     * @param user
     * @return
     */
     boolean isAdmin(User user);
}
