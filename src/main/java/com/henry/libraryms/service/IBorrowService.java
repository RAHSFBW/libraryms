package com.henry.libraryms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henry.libraryms.model.dto.BorrowRequest;
import com.henry.libraryms.model.dto.ReturnRquest;
import com.henry.libraryms.model.entity.Borrow;

public interface IBorrowService extends IService<Borrow> {

    Boolean borrowBooks(BorrowRequest borrowRequest, String userName);

    Boolean returnBooks(ReturnRquest returnRquest, String userName);
}
