package com.hoan.pagingexcel.common;

import com.hoan.pagingexcel.common.domain.MenuVO;
import com.hoan.pagingexcel.common.service.CommonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommonServiceTest {

    @Autowired
    private CommonService commonService;
    
    @Test
    public void getAllMenuByEnum() {
        List<Object> allMenuByEnum = commonService.getAllMenuByEnum();
        for (Object o : allMenuByEnum) {
            System.out.println("o = " + o);
        }
    }
    
    @Test
    public void getAllMenu() {
        List<MenuVO> allMenuByDB = commonService.getAllMenuByDB();
        for (MenuVO menuVO : allMenuByDB) {
            System.out.println("menuVO = " + menuVO);
        }
    }
}
