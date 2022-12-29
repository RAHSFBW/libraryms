package com.henry.libraryms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henry.libraryms.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface IUserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userName 用户名
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @param userRole 用户角色
     * @param sex 性别
     * @param email 邮箱
     * @param phone 手机
     * @return 新用户 id
     */
    long userRegister(String userName, String userPassword, String checkPassword, String userRole,String sex, String email, String phone);

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userName, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);
}
