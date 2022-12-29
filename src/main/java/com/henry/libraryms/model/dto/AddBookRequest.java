package com.henry.libraryms.model.dto;

import lombok.Data;

@Data
public class AddBookRequest {

    private String name;

    private String author;

    private String publisher;

    private Integer type;

    private Integer total;

    private Integer num;
}
