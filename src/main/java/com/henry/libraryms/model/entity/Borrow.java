package com.henry.libraryms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel
@Data
public class Borrow {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "借阅的书籍的id")
    @TableField(value = "book_id")
    private String bookId;

    @ApiModelProperty(value = "借阅用户")
    @TableField(value = "user_name")
    private String userName;

    @ApiModelProperty(value = "借阅时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    @TableField(value = "borrow_time")
    private LocalDate borrowTime;

    @ApiModelProperty(value = "借阅最长时间 单位：天")
    @TableField(value = "borrow_days")
    private Integer borrowDays;

    @ApiModelProperty(value = "归还时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    @TableField(value = "return_time")
    private LocalDate returnTime;

    @ApiModelProperty(value = "借阅状态：-1 超时 0 借阅中 1 已归还")
    private Integer status;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
