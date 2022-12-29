package com.henry.libraryms.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class DictVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dictName;

    private String dictCode;
}
