package com.henry.libraryms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henry.libraryms.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class BookQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String name;

    private String author;

    private String publisher;

    private Integer type;

}
