package com.hoan.pagingexcel.prototype;


import com.hoan.pagingexcel.common.domain.PageVO;
import com.hoan.pagingexcel.prototype.service.PrototypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@RequiredArgsConstructor
@Controller
public class PrototypeController {

    private final PrototypeService prototypeService;

    @PostMapping("/getPrototypeList")
    public ResponseEntity getStatistics(@RequestBody PageVO requestPage) {
        HashMap<String,Object> response = prototypeService.getPrototypeList(requestPage);
        return ResponseEntity.ok(response);
    }
}
