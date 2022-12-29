package com.henry.libraryms.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BorrowVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "借阅的书籍的id")
    private String bookId;

    @ApiModelProperty(value = "借阅用户")
    private String userName;

    @ApiModelProperty(value = "借阅时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate borrowTime;

    @ApiModelProperty(value = "借阅最长时间 单位：天")
    private Integer borrowDays;

    @ApiModelProperty(value = "归还时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate returnTime;

    @ApiModelProperty(value = "借阅状态：-1 超时 0 借阅中 1 已归还")
    private Integer status;
}
