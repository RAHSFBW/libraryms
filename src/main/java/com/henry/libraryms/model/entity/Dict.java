package com.henry.libraryms.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value = "dict")
@Data
public class Dict implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "dict_name")
    private String dictName;

    @TableField(value = "dict_code")
    private String dictCode;

    @TableField(value = "item_key")
    private Integer itemKey;

    @TableField(value = "item_value")
    private String itemValue;

    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDelete;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
