package com.hoan.pagingexcel.prototype;

import com.hoan.pagingexcel.common.domain.PageVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeVO;
import com.hoan.pagingexcel.prototype.service.PrototypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class PrototypeServiceTest {

    @Autowired
    private PrototypeService prototypeService;

    @Test
    public void getPrototypeList() {
        PageVO pageVO = new PageVO();
        pageVO.setPageCnt(5);

        HashMap<String, Object> prototypeList = prototypeService.getPrototypeList(pageVO);

        List<PrototypeVO> prototypeVOList = (List<PrototypeVO>) prototypeList.get("data");

        for (PrototypeVO prototypeVO : prototypeVOList) {
            System.out.println("prototypeVO = " + prototypeVO);
        }
        

    }
}
