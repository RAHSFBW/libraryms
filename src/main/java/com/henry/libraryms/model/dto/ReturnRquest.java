package com.henry.libraryms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henry.libraryms.common.PageRequest;
import lombok.Data;

@Data
public class ReturnRquest {

    private String userNam;
    private String bookIds;
}
