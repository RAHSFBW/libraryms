package com.henry.libraryms.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class DictInfoQueryRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String dictCode;

    private Integer itemKey;

}
