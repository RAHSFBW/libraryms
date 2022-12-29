package com.henry.libraryms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henry.libraryms.common.constants.ErrorCode;
import com.henry.libraryms.exception.BusinessException;
import com.henry.libraryms.mapper.BorrowMapper;
import com.henry.libraryms.model.dto.BorrowRequest;
import com.henry.libraryms.model.dto.ReturnRquest;
import com.henry.libraryms.model.entity.Book;
import com.henry.libraryms.model.entity.Borrow;
import com.henry.libraryms.model.vo.BookVO;
import com.henry.libraryms.service.IBookService;
import com.henry.libraryms.service.IBorrowService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow> implements IBorrowService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IBookService bookService;

    @Autowired
    private Executor MyThreadExecutor;

    @Transactional
    @Override
    public Boolean borrowBooks(BorrowRequest borrowRequest, String userName) {

        LocalDateTime now = LocalDateTime.now();

        if (borrowRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        String bookIds = borrowRequest.getBookIds();
        Integer borrowDays = borrowRequest.getBorrowDays();
        if (StringUtils.isBlank(bookIds)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "借阅书籍为空");
        }
        if (borrowDays < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "借阅天数不能小于0天");
        }
        if (borrowDays > 30) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "借阅天数不能大于30天");
        }

        String[] bookIdArray = bookIds.split(",");
        HashSet<String> set = new HashSet<>();
        for (String bookId : bookIdArray) {
            if(set.contains(bookId)){
                throw new BusinessException(ErrorCode.OPERATION_ERROR,"不能同时借阅多本一样的书");
            }
            set.add(bookId);
        }
        List<CompletableFuture> futures = new ArrayList<>();
        for (String bookId : bookIdArray){
            futures.add(CompletableFuture.runAsync(() -> {
                Book book = bookService.getById(bookId);
                if(getBorrowingBooksNum(userName) >= 5){
                    log.info("user:{} borrow num equal or more than 5",userName);
                    throw new BusinessException(ErrorCode.OPERATION_ERROR,"借阅数量超过5本，请先归还后再借阅");
                }
                if(isBorrowedBook(bookId,userName)){
                    log.info("user:{} has borrowed book:{}",userName,book.getName());
                    throw new BusinessException(ErrorCode.OPERATION_ERROR,book.getName()+"已借阅，不可重复借阅");
                }
                if (book.getNum() > 0) {
                    BookVO bookVO = new BookVO();
                    BeanUtils.copyProperties(book, bookVO);
                    bookVO.setNum(bookVO.getNum() - 1);
                    bookService.updateBook(bookVO);
                    Borrow borrow = new Borrow();
                    borrow.setBookId(bookId);
                    borrow.setUserName(userName);
                    borrow.setBorrowTime(LocalDate.now());
                    borrow.setBorrowDays(borrowRequest.getBorrowDays());
                    borrow.setStatus(0);
                    borrow.setCreateTime(now);
                    borrow.setUpdateTime(now);
                    this.save(borrow);

                } else {
                    log.info("id:{} name:{} num less than 1",book.getId(),book.getName());
                    throw new BusinessException(ErrorCode.OPERATION_ERROR,"库存不足");
                }
            },MyThreadExecutor));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();


        return true;
    }

    @Override
    public Boolean returnBooks(ReturnRquest returnRquest, String userName) {
        LocalDateTime now = LocalDateTime.now();
        if(returnRquest == null || StringUtils.isBlank(returnRquest.getBookIds())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        QueryWrapper<Borrow> qw = new QueryWrapper<>();
        qw.lambda().eq(Borrow::getUserName,userName);
        qw.lambda().in(Borrow::getBookId,returnRquest.getBookIds().split(","));
        qw.lambda().in(Borrow::getStatus, new Integer[]{-1,0});
        List<Borrow> list = this.list(qw);
        if(CollectionUtils.isEmpty(list)){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无需归还书籍");
        }
        for (Borrow borrow : list) {
            CompletableFuture.supplyAsync(() -> {
                Book book = bookService.getById(Long.valueOf(borrow.getBookId()));
                if(book == null){
                    throw new BusinessException(ErrorCode.OPERATION_ERROR, "未找到该书籍，请到柜台人工归还");
                }
                BookVO bookVO = new BookVO();
                BeanUtils.copyProperties(book, bookVO);
                bookVO.setNum(bookVO.getNum() + 1);
                bookService.updateBook(bookVO);
                borrow.setReturnTime(LocalDate.now());
                borrow.setStatus(1);
                borrow.setUpdateTime(now);
                this.updateById(borrow);

                return true;
            },MyThreadExecutor);
        }

        return true;
    }


    private Long getBorrowingBooksNum(String userName){
        QueryWrapper<Borrow> qw = new QueryWrapper<>();
        qw.lambda().eq(Borrow::getUserName,userName);
        qw.lambda().in(Borrow::getStatus, new int[]{-1,0});
        long count = this.count(qw);

        return count;
    }

    private Boolean isBorrowedBook(String bookId, String userName){
        QueryWrapper<Borrow> qw = new QueryWrapper<>();
        qw.lambda().eq(Borrow::getBookId,bookId);
        qw.lambda().eq(Borrow::getUserName,userName);
        qw.lambda().in(Borrow::getStatus, new int[]{-1,0});
        List<Borrow> list = this.list(qw);

        return !CollectionUtils.isEmpty(list);
    }
}
