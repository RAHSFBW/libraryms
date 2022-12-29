package com.henry.libraryms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.henry.libraryms.annotation.AuthCheck;
import com.henry.libraryms.common.BaseResponse;
import com.henry.libraryms.common.ResultUtils;
import com.henry.libraryms.common.constants.ErrorCode;
import com.henry.libraryms.common.constants.UserConstant;
import com.henry.libraryms.exception.BusinessException;
import com.henry.libraryms.model.dto.DictDelRequest;
import com.henry.libraryms.model.dto.DictInfoQueryRequest;
import com.henry.libraryms.model.dto.DictQueryRequest;
import com.henry.libraryms.model.entity.Dict;
import com.henry.libraryms.model.vo.DictInfoVO;
import com.henry.libraryms.model.vo.DictVO;
import com.henry.libraryms.service.IDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "字典", value = "字典控制器")
@RestController
@RequestMapping("/dict")
@AllArgsConstructor
public class DictController {

    private final IDictService dictService;

    private final RedisTemplate redisTemplate;

    @ApiOperation("字典项信息")
    @PostMapping("/info")
    @AuthCheck(anyRole = {UserConstant.ADMIN_ROLE,UserConstant.DEFAULT_ROLE})
    public BaseResponse<String> getDictInfo(@RequestBody DictInfoQueryRequest dictInfoQueryRequest){
        String value = (String) redisTemplate.opsForValue().get("librarymsdict-"+dictInfoQueryRequest.getDictCode() + "-" + dictInfoQueryRequest.getItemKey());
        if(value == null){
            QueryWrapper<Dict> qw = new QueryWrapper<>();
            qw.lambda().eq(Dict::getDictCode,dictInfoQueryRequest.getDictCode());
            qw.lambda().eq(Dict::getItemKey,dictInfoQueryRequest.getItemKey());
            Dict dict = dictService.getOne(qw);
            if(dict != null){
                value = dict.getItemValue();
                redisTemplate.opsForValue().set("librarymsdict-"+dictInfoQueryRequest.getDictCode() + "-" + dictInfoQueryRequest.getItemKey(),value);
            }
        }

        return ResultUtils.success(value);
    }

    @ApiOperation("字典信息列表")
    @PostMapping("/dictList")
    @AuthCheck(anyRole = {UserConstant.ADMIN_ROLE,UserConstant.DEFAULT_ROLE})
    public BaseResponse<List<DictVO>> getDictList(@RequestBody DictQueryRequest dictQueryRequest){
        List<DictVO> dictList = dictService.getDictList(dictQueryRequest);
        return ResultUtils.success(dictList);
    }

    @ApiOperation("字典项信息列表")
    @PostMapping("/infoList")
    @AuthCheck(anyRole = {UserConstant.ADMIN_ROLE,UserConstant.DEFAULT_ROLE})
    public BaseResponse<List<DictInfoVO>> getDictInfoList(@RequestBody DictInfoQueryRequest dictInfoQueryRequest){
        List<DictInfoVO> dictInfoList = dictService.getDictInfoList(dictInfoQueryRequest);
        return ResultUtils.success(dictInfoList);
    }

    @ApiOperation("新增字典项信息")
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> addDictInfo(@RequestBody DictInfoVO dictInfoVO){
        LocalDateTime now = LocalDateTime.now();
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictInfoVO,dict);
        dict.setCreateTime(now);
        dict.setUpdateTime(now);
        boolean save = dictService.save(dict);

        redisTemplate.opsForValue().set("librarymsdict-"+dictInfoVO.getDictCode()+"-"+dictInfoVO.getItemKey(),dictInfoVO.getItemValue());
        return ResultUtils.success(save);
    }

    @ApiOperation("修改字典项信息")
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateDictInfo(@RequestBody DictInfoVO dictInfoVO){
        LocalDateTime now = LocalDateTime.now();
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictInfoVO,dict);
        dict.setUpdateTime(now);
        boolean update = dictService.updateById(dict);

        return ResultUtils.success(update);
    }

    @ApiOperation("删除字典项信息")
    @PostMapping("/del")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> delDictInfo(@RequestBody DictDelRequest dictDelRequest){
        if(dictDelRequest == null || dictDelRequest.getId() == null || StringUtils.isBlank(dictDelRequest.getDictCode())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        boolean del = dictService.removeById(dictDelRequest.getId());
        redisTemplate.delete("librarymsdict-"+dictDelRequest.getDictCode()+"-"+dictDelRequest.getItemKey());
        return ResultUtils.success(del);
    }
}
