package com.henry.libraryms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.henry.libraryms.model.dto.BookQueryRequest;
import com.henry.libraryms.model.entity.Book;
import com.henry.libraryms.model.vo.BookVO;

import java.util.List;

public interface IBookService extends IService<Book> {

    /**
     * 新增书籍（类型）
     *
     * @param name 书名
     * @param author 作者
     * @param publisher 出版社
     * @param type 类型
     * @param num 数量
     * @return
     */
    Long addBook(String name, String author, String publisher, Integer type, Integer total, Integer num);

    /**
     * 删除书籍
     *
     * @param id 书籍唯一id
     * @return
     */
    Boolean delBook(Long id);

    /**
     * 修改书籍信息
     *
     * @param bookVO
     * @return
     */
    Boolean updateBook(BookVO bookVO);

    /**
     * 分页获取书籍列表
     *
     * @param bookQueryRequest
     * @return
     */
    IPage<BookVO> listBookByPage(BookQueryRequest bookQueryRequest);

    /**
     * 获取书籍列表
     *
     * @param bookQueryRequest
     * @return
     */
    List<BookVO> listBook(BookQueryRequest bookQueryRequest);
}
