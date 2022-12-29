package com.henry.libraryms.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;

    private String password;

    private String checkPassword;

    private String sex;

    private String email;

    private String phone;
}
