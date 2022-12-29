package com.henry.libraryms.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;

import java.time.LocalDateTime;

@TableName(value = "app_user")
@Data
public class User {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("user_name")
    private String userName;

    @TableField("password")
    private String password;

    @TableField("sex")
    private String sex;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("user_role")
    private String userRole;

    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDelete;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
