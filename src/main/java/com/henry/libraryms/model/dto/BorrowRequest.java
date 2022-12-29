package com.henry.libraryms.model.dto;

import lombok.Data;

@Data
public class BorrowRequest {

    private String bookIds;

    private Integer borrowDays;
}
