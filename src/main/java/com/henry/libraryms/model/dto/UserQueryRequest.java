package com.henry.libraryms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henry.libraryms.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String userName;

    private String password;

    private String sex;

    private String email;

    private String phone;

    private String userRole;
}
