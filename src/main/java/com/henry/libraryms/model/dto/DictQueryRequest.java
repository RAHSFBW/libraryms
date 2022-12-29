package com.henry.libraryms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class DictQueryRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String dictName;

    private String dictCode;
}
