package com.henry.libraryms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henry.libraryms.model.entity.Borrow;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BorrowMapper extends BaseMapper<Borrow> {
}
