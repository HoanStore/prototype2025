package com.hoan.pagingexcel.common;



import com.hoan.pagingexcel.common.domain.FileDetailVO;
import com.hoan.pagingexcel.common.domain.MenuStructureCode;
import com.hoan.pagingexcel.common.domain.MenuVO;
import com.hoan.pagingexcel.common.service.CommonService;
import com.hoan.pagingexcel.common.util.file.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/cmm")
@Controller
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;
    private final FileUtil fileUtil;

    @PostMapping("/getMenuCode")
    public ResponseEntity<List<Object>> getMenuCode(@RequestBody(required = false) Map<String, String> param) {
        return ResponseEntity.ok(commonService.getAllMenuByEnum());
    }

    @PostMapping("/getMenuCodeByDB")
    public ResponseEntity<List<MenuVO>> getMenuCodeByDB(@RequestBody(required = false) Map<String, String> param) {
        return ResponseEntity.ok(commonService.getAllMenuByDB());
    }


    @GetMapping("/getFile")
    public ResponseEntity getFile(@RequestParam String attflId, @RequestParam Long attflSeq,
                                  @RequestParam(defaultValue = "N") String isThumbnail) throws IOException {
        FileDetailVO fileDetailVO = commonService.selectFileNmAndPath(attflId, attflSeq);
        return fileUtil.getFile(fileDetailVO, isThumbnail);
    }
}
