package com.henry.libraryms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henry.libraryms.mapper.DictMapper;
import com.henry.libraryms.model.dto.DictInfoQueryRequest;
import com.henry.libraryms.model.dto.DictQueryRequest;
import com.henry.libraryms.model.entity.Dict;
import com.henry.libraryms.model.vo.DictInfoVO;
import com.henry.libraryms.model.vo.DictVO;
import com.henry.libraryms.service.IDictService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    @Override
    public List<DictVO> getDictList(DictQueryRequest dictQueryRequest) {

        Dict dict = new Dict();
        if(dictQueryRequest != null){
            BeanUtils.copyProperties(dictQueryRequest,dict);
        }

        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>(dict);
        List<Dict> list = this.list(queryWrapper);
        List<DictVO> dictVOList = list.stream().map(dict1 -> {
            DictVO dictVO = new DictVO();
            BeanUtils.copyProperties(dict1,dictVO);
            return dictVO;
        }).collect(Collectors.toList());
        return dictVOList;
    }

    @Override
    public List<DictInfoVO> getDictInfoList(DictInfoQueryRequest dictInfoQueryRequest) {

        Dict dict = new Dict();
        if(dictInfoQueryRequest != null){
            BeanUtils.copyProperties(dictInfoQueryRequest,dict);
        }

        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>(dict);
        List<Dict> list = this.list(queryWrapper);
        List<DictInfoVO> dictInfoVOList = list.stream().map(dict1 -> {
            DictInfoVO dictInfoVO = new DictInfoVO();
            BeanUtils.copyProperties(dict1,dictInfoVO);
            return dictInfoVO;
        }).collect(Collectors.toList());
        return dictInfoVOList;
    }
}
