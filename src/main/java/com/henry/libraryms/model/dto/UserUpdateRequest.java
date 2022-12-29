package com.henry.libraryms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class UserUpdateRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String userName;

    private String password;

    private String sex;

    private String email;

    private String phone;

    private String userRole;
}
