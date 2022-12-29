package com.henry.libraryms.common;

import com.henry.libraryms.common.constants.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private T data;

    private String message;

    public BaseResponse(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(T data) {
        this.code = 0;
        this.data = data;
        this.message = "ok";
    }

    public BaseResponse(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.data = null;
        this.message = errorCode.getMessage();
    }
}
