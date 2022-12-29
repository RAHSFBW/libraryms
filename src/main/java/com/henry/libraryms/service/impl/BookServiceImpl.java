package com.henry.libraryms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.CaseFormat;
import com.henry.libraryms.common.ResultUtils;
import com.henry.libraryms.common.constants.ErrorCode;
import com.henry.libraryms.exception.BusinessException;
import com.henry.libraryms.mapper.BookMapper;
import com.henry.libraryms.model.dto.BookQueryRequest;
import com.henry.libraryms.model.entity.Book;
import com.henry.libraryms.model.entity.User;
import com.henry.libraryms.model.vo.BookVO;
import com.henry.libraryms.model.vo.UserVO;
import com.henry.libraryms.service.IBookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Override
    public Long addBook(String name, String author, String publisher, Integer type, Integer total, Integer num) {
        if(StringUtils.isBlank(name)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "书名不能为空");
        }
        Book book = new Book();
        book.setName(name);
        book.setAuthor(StringUtils.isBlank(author)?"未知":author);
        book.setPublisher(StringUtils.isBlank(publisher)?"未知":publisher);
        book.setType(Optional.ofNullable(type).orElse(99));
        book.setTotal(Optional.ofNullable(num).orElse(0));
        book.setNum(Optional.ofNullable(num).orElse(0));
        book.setCreateTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());

        boolean saveResult = this.save(book);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加失败，数据库错误");
        }
        return book.getId();
    }

    @Override
    public Boolean delBook(Long id) {
        boolean remove = this.removeById(id);
        return remove;
    }

    @Override
    public Boolean updateBook(BookVO bookVO) {

        if (bookVO == null || bookVO.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookVO, book);
        book.setUpdateTime(LocalDateTime.now());

        boolean updateById = this.updateById(book);
        return updateById;

    }

    @Override
    public IPage<BookVO> listBookByPage(BookQueryRequest bookQueryRequest) {
        Long current = 1L;
        Long size = 10L;
        QueryWrapper<Book> qw = new QueryWrapper<>();
        if(bookQueryRequest != null){
            if(bookQueryRequest.getId() != null){
                qw.lambda().eq(Book::getId,bookQueryRequest.getId());
            }
            if(StringUtils.isNotBlank(bookQueryRequest.getName())){
                qw.lambda().likeRight(Book::getName,bookQueryRequest.getName());
            }
            if(StringUtils.isNotBlank(bookQueryRequest.getAuthor())){
                qw.lambda().eq(Book::getAuthor,bookQueryRequest.getAuthor());
            }
            if(StringUtils.isNotBlank(bookQueryRequest.getPublisher())){
                qw.lambda().eq(Book::getPublisher,bookQueryRequest.getPublisher());
            }
            if(bookQueryRequest.getType() != null){
                qw.lambda().eq(Book::getType,bookQueryRequest.getType());
            }
            if(StringUtils.isNotBlank(bookQueryRequest.getSortField())){
                boolean isAsc = true;
                if(StringUtils.isNotBlank(bookQueryRequest.getSortOrder())){
                    if(!"ASC".equals(bookQueryRequest.getSortOrder())){
                        isAsc = false;
                    }
                }
                qw.orderBy(true,isAsc,CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, bookQueryRequest.getSortField()));
            }
            current = bookQueryRequest.getCurrent();
            size = bookQueryRequest.getPageSize();
        }
        Page<Book> bookPage = this.page(new Page<>(current, size), qw);
        Page<BookVO> bookVOPage = new PageDTO<>(bookPage.getCurrent(), bookPage.getSize(), bookPage.getTotal());
        List<BookVO> bookVOList = bookPage.getRecords().stream().map(book -> {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            return bookVO;
        }).collect(Collectors.toList());
        bookVOPage.setRecords(bookVOList);

        return bookVOPage;
    }

    @Override
    public List<BookVO> listBook(BookQueryRequest bookQueryRequest) {

        QueryWrapper<Book> qw = new QueryWrapper<>();
        if(bookQueryRequest != null){
            if(bookQueryRequest.getId() != null){
                qw.lambda().eq(Book::getId,bookQueryRequest.getId());
            }
            if(StringUtils.isNotBlank(bookQueryRequest.getName())){
                qw.lambda().likeRight(Book::getName,bookQueryRequest.getName());
            }
            if(StringUtils.isNotBlank(bookQueryRequest.getAuthor())){
                qw.lambda().eq(Book::getAuthor,bookQueryRequest.getAuthor());
            }
            if(StringUtils.isNotBlank(bookQueryRequest.getPublisher())){
                qw.lambda().eq(Book::getPublisher,bookQueryRequest.getPublisher());
            }
            if(bookQueryRequest.getType() != null){
                qw.lambda().eq(Book::getType,bookQueryRequest.getType());
            }
            if(StringUtils.isNotBlank(bookQueryRequest.getSortField())){
                boolean isAsc = true;
                if(StringUtils.isNotBlank(bookQueryRequest.getSortOrder())){
                    if(!"ASC".equals(bookQueryRequest.getSortOrder())){
                        isAsc = false;
                    }
                }
                qw.orderBy(true,isAsc,CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, bookQueryRequest.getSortField()));
            }
        }
        List<Book> bookList = this.list(qw);
        List<BookVO> bookVOList = bookList.stream().map(book -> {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            return bookVO;
        }).collect(Collectors.toList());

        return bookVOList;
    }
}
