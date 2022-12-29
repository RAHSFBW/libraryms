package com.henry.libraryms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.henry.libraryms.annotation.AuthCheck;
import com.henry.libraryms.common.BaseResponse;
import com.henry.libraryms.common.ResultUtils;
import com.henry.libraryms.common.constants.ErrorCode;
import com.henry.libraryms.common.constants.UserConstant;
import com.henry.libraryms.exception.BusinessException;
import com.henry.libraryms.model.dto.BorrowQueryRequest;
import com.henry.libraryms.model.dto.BorrowRequest;
import com.henry.libraryms.model.dto.ReturnRquest;
import com.henry.libraryms.model.dto.UserQueryRequest;
import com.henry.libraryms.model.entity.Borrow;
import com.henry.libraryms.model.entity.User;
import com.henry.libraryms.model.vo.BorrowVO;
import com.henry.libraryms.model.vo.UserVO;
import com.henry.libraryms.service.IBorrowService;
import com.henry.libraryms.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "借还书籍", value = "借还书籍控制器")
@RestController
@RequestMapping("/br")
@AllArgsConstructor
public class BorrowController {

    private final IBorrowService borrowService;

    private final IUserService userService;

    @ApiOperation("借书")
    @PostMapping("/borrow")
    @AuthCheck(anyRole = {UserConstant.ADMIN_ROLE,UserConstant.DEFAULT_ROLE})
    public BaseResponse<Boolean> borrowBooks(@RequestBody BorrowRequest borrowRequest, HttpServletRequest httpRequest){
        User loginUser = userService.getLoginUser(httpRequest);
        Boolean borrowBooks = null;
        try {
            borrowBooks = borrowService.borrowBooks(borrowRequest, loginUser.getUserName());
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"操作失败");
        }
        return ResultUtils.success(borrowBooks);
    }

    @ApiOperation("还书")
    @PostMapping("/return")
    @AuthCheck(anyRole = {UserConstant.ADMIN_ROLE,UserConstant.DEFAULT_ROLE})
    public BaseResponse<Boolean> returnBooks(@RequestBody ReturnRquest returnRquest, HttpServletRequest httpRequest){
        User loginUser = userService.getLoginUser(httpRequest);
        Boolean returnBooks = borrowService.returnBooks(returnRquest, loginUser.getUserName());

        return ResultUtils.success(returnBooks);
    }

    @ApiOperation("更新借阅信息")
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateBorrow(@RequestBody BorrowVO borrowVO){
        Borrow borrow = new Borrow();
        BeanUtils.copyProperties(borrowVO,borrow);
        borrow.setUpdateTime(LocalDateTime.now());
        boolean updateById = borrowService.updateById(borrow);

        return ResultUtils.success(updateById);
    }

    @ApiOperation("根据Id获取借阅信息")
    @GetMapping("/get")
    @AuthCheck(anyRole = {UserConstant.ADMIN_ROLE,UserConstant.DEFAULT_ROLE})
    public BaseResponse<BorrowVO> getBorrowById(Long id){
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Borrow borrow = borrowService.getById(id);
        BorrowVO borrowVO = new BorrowVO();
        BeanUtils.copyProperties(borrow, borrowVO);
        return ResultUtils.success(borrowVO);
    }

    @ApiOperation("获取借阅列表")
    @PostMapping("/list")
    @AuthCheck(anyRole = {UserConstant.ADMIN_ROLE,UserConstant.DEFAULT_ROLE})
    public BaseResponse<List<BorrowVO>> listBorrow(@RequestBody BorrowQueryRequest borrowQueryRequest) {

        QueryWrapper<Borrow> qw = new QueryWrapper<>();
        if (borrowQueryRequest != null) {
            if(borrowQueryRequest.getId() != null){
                qw.lambda().eq(Borrow::getId, borrowQueryRequest.getId());
            }
            if(StringUtils.isNotBlank(borrowQueryRequest.getBookId())){
                qw.lambda().eq(Borrow::getBookId,borrowQueryRequest.getBookId());
            }
            if(StringUtils.isNotBlank(borrowQueryRequest.getUserName())){
                qw.lambda().likeRight(Borrow::getUserName,borrowQueryRequest.getUserName());
            }
            if(borrowQueryRequest.getBorrowStartTime() != null && borrowQueryRequest.getBorrowEndTime() != null){
                qw.lambda().between(Borrow::getBorrowTime,borrowQueryRequest.getBorrowStartTime(),borrowQueryRequest.getBorrowEndTime());
            }
            if(borrowQueryRequest.getReturnStartTime() != null && borrowQueryRequest.getReturnEndTime() != null){
                qw.lambda().between(Borrow::getReturnTime,borrowQueryRequest.getReturnStartTime(),borrowQueryRequest.getReturnEndTime());
            }
            if(borrowQueryRequest.getStatus() != null){
                qw.lambda().eq(Borrow::getStatus,borrowQueryRequest.getStatus());
            }
        }

        List<Borrow> borrowList = borrowService.list(qw);
        List<BorrowVO> borrowVOList = borrowList.stream().map(borrow -> {
            BorrowVO borrowVO = new BorrowVO();
            BeanUtils.copyProperties(borrow, borrowVO);
            return borrowVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(borrowVOList);
    }

    @ApiOperation("分页获取借阅列表")
    @PostMapping("/page")
    @AuthCheck(anyRole = {UserConstant.ADMIN_ROLE,UserConstant.DEFAULT_ROLE})
    public BaseResponse<Page<BorrowVO>> listBorrowByPage(@RequestBody BorrowQueryRequest borrowQueryRequest) {
        long current = 1;
        long size = 10;
        QueryWrapper<Borrow> qw = new QueryWrapper<>();
        if (borrowQueryRequest != null) {
            current = borrowQueryRequest.getCurrent();
            size = borrowQueryRequest.getPageSize();

            if(borrowQueryRequest.getId() != null){
                qw.lambda().eq(Borrow::getId, borrowQueryRequest.getId());
            }
            if(StringUtils.isNotBlank(borrowQueryRequest.getBookId())){
                qw.lambda().eq(Borrow::getBookId,borrowQueryRequest.getBookId());
            }
            if(StringUtils.isNotBlank(borrowQueryRequest.getUserName())){
                qw.lambda().likeRight(Borrow::getUserName,borrowQueryRequest.getUserName());
            }
            if(borrowQueryRequest.getBorrowStartTime() != null && borrowQueryRequest.getBorrowEndTime() != null){
                qw.lambda().between(Borrow::getBorrowTime,borrowQueryRequest.getBorrowStartTime(),borrowQueryRequest.getBorrowEndTime());
            }
            if(borrowQueryRequest.getReturnStartTime() != null && borrowQueryRequest.getReturnEndTime() != null){
                qw.lambda().between(Borrow::getReturnTime,borrowQueryRequest.getReturnStartTime(),borrowQueryRequest.getReturnEndTime());
            }
            if(borrowQueryRequest.getStatus() != null){
                qw.lambda().eq(Borrow::getStatus,borrowQueryRequest.getStatus());
            }
        }

        Page<Borrow> borrowPage = borrowService.page(new Page<>(current, size), qw);
        Page<BorrowVO> borrowVOPage = new PageDTO<>(borrowPage.getCurrent(), borrowPage.getSize(), borrowPage.getTotal());

        List<BorrowVO> borrowVOList = borrowPage.getRecords().stream().map(borrow -> {
            BorrowVO borrowVO = new BorrowVO();
            BeanUtils.copyProperties(borrow, borrowVO);
            return borrowVO;
        }).collect(Collectors.toList());

        borrowVOPage.setRecords(borrowVOList);
        return ResultUtils.success(borrowVOPage);
    }
}
