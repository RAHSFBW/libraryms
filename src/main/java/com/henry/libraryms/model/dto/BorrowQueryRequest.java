package com.henry.libraryms.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.henry.libraryms.common.PageRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BorrowQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(value = "借阅的书籍的id")
    private String bookId;

    @ApiModelProperty(value = "借阅用户")
    private String userName;

    @ApiModelProperty(value = "借阅开始时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate borrowStartTime;

    @ApiModelProperty(value = "借阅结束时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate borrowEndTime;

    @ApiModelProperty(value = "归还开始时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate returnStartTime;

    @ApiModelProperty(value = "归还开始时间")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate returnEndTime;

    @ApiModelProperty(value = "借阅状态：-1 超时 0 借阅中 1 已归还")
    private Integer status;
}
