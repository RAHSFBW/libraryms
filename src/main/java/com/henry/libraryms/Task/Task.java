package com.henry.libraryms.Task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.henry.libraryms.model.entity.Borrow;
import com.henry.libraryms.service.IBorrowService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class Task implements CommandLineRunner {

    private final IBorrowService borrowService;

    public Task(IBorrowService borrowService) {
        this.borrowService = borrowService;
    }


    @Scheduled(cron = "0 0 1 * * ?")
    public void timeOutCheck(){
        log.info("开始检查是否有超时借阅的记录");
        List<Borrow> timeOutList = new ArrayList<>();
        LocalDate now = LocalDate.now();
        QueryWrapper<Borrow> qw = new QueryWrapper<>();
        qw.lambda().in(Borrow::getStatus,new Integer[]{-1,0});
        try {
            List<Borrow> list = borrowService.list(qw);
            if(!CollectionUtils.isEmpty(list)){
                for (Borrow borrow : list) {
                    LocalDate borrowTime = borrow.getBorrowTime();
                    Integer borrowDays = borrow.getBorrowDays();
                    LocalDate plus = borrowTime.plusDays(borrowDays);
                    if(now.compareTo(plus) < 0){
                        timeOutList.add(borrow);
                    }
                }
            }
        } catch (Exception e) {
            log.error("超时记录检查任务执行异常:"+ ExceptionUtils.getStackTrace(e));
        }
        //todo 超时提醒用户
        //code here
        log.info("检查借阅超时记录结束");
    }

    @Override
    public void run(String... args) throws Exception {
        timeOutCheck();
    }
}
