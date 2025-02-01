package com.hoan.pagingexcel.common;



import com.hoan.pagingexcel.common.domain.MenuStructureCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/cmm")
@Controller
@RequiredArgsConstructor
public class CommonController {


    @PostMapping("/getMenuCode")
    public ResponseEntity<List<Object>> getMenuCode(@RequestBody(required = false) Map<String, String> param) {
        return ResponseEntity.ok(Arrays.asList(MenuStructureCode.values()));
    }

}
