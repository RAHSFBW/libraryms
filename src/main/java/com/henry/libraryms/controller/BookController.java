package com.henry.libraryms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.henry.libraryms.annotation.AuthCheck;
import com.henry.libraryms.common.BaseResponse;
import com.henry.libraryms.common.ResultUtils;
import com.henry.libraryms.common.constants.ErrorCode;
import com.henry.libraryms.common.constants.UserConstant;
import com.henry.libraryms.exception.BusinessException;
import com.henry.libraryms.model.dto.AddBookRequest;
import com.henry.libraryms.model.dto.BookQueryRequest;
import com.henry.libraryms.model.dto.IdRequest;
import com.henry.libraryms.model.vo.BookVO;
import com.henry.libraryms.service.IBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "书籍", value = "书籍控制器")
@RestController
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {

    private final IBookService bookService;

    @ApiOperation("添加书籍")
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addBook(@RequestBody AddBookRequest addBookRequest, HttpServletRequest httpRequest){
        if(addBookRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long addBook = bookService.addBook(addBookRequest.getName(), addBookRequest.getAuthor(),
                addBookRequest.getPublisher(), addBookRequest.getType(), addBookRequest.getTotal(),
                addBookRequest.getNum());
        return ResultUtils.success(addBook);
    }

    @ApiOperation("删除书籍")
    @PostMapping("/del")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> delBook(@RequestBody IdRequest idRequest, HttpServletRequest httpRequest){
        if(idRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean delBook = bookService.delBook(idRequest.getId());
        return ResultUtils.success(delBook);
    }

    @ApiOperation("更新书籍")
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateBook(@RequestBody BookVO bookVO, HttpServletRequest httpRequest){
        if(bookVO == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean updateBook = bookService.updateBook(bookVO);
        return ResultUtils.success(updateBook);
    }

    @ApiOperation("分页获取书籍列表")
    @PostMapping("/page")
    @AuthCheck(anyRole = {UserConstant.ADMIN_ROLE,UserConstant.DEFAULT_ROLE})
    public BaseResponse<IPage<BookVO>> listBookByPage(@RequestBody BookQueryRequest bookQueryRequest, HttpServletRequest httpRequest){
        if(bookQueryRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        IPage<BookVO> listBookByPage = bookService.listBookByPage(bookQueryRequest);
        return ResultUtils.success(listBookByPage);
    }

    @ApiOperation("获取书籍列表")
    @PostMapping("/list")
    @AuthCheck(anyRole = {UserConstant.ADMIN_ROLE,UserConstant.DEFAULT_ROLE})
    public BaseResponse<List<BookVO>> listBook(@RequestBody BookQueryRequest bookQueryRequest, HttpServletRequest httpRequest){

        if(bookQueryRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<BookVO> listBookByPage = bookService.listBook(bookQueryRequest);
        return ResultUtils.success(listBookByPage);
    }

}
