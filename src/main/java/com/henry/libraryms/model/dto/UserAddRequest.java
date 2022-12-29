package com.henry.libraryms.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 * @author https://github.com/liyupi
 */
@Data
public class UserAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;

    private String password;

    private String sex;

    private String email;

    private String phone;

    private String userRole;
}