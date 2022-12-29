package com.henry.libraryms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henry.libraryms.model.dto.DictInfoQueryRequest;
import com.henry.libraryms.model.dto.DictQueryRequest;
import com.henry.libraryms.model.entity.Dict;
import com.henry.libraryms.model.vo.DictInfoVO;
import com.henry.libraryms.model.vo.DictVO;

import java.util.List;

public interface IDictService extends IService<Dict> {

    List<DictVO> getDictList(DictQueryRequest dictQueryRequest);

    List<DictInfoVO> getDictInfoList(DictInfoQueryRequest dictInfoQueryRequest);
}
