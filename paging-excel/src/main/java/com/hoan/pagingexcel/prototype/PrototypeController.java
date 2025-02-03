package com.hoan.pagingexcel.prototype;


import com.hoan.pagingexcel.common.domain.PageVO;
import com.hoan.pagingexcel.common.mapper.CommonMapper;
import com.hoan.pagingexcel.common.util.excel_module.excel.ExcelFile;
import com.hoan.pagingexcel.common.util.excel_module.excel.onesheet.OneSheetExcelFile;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelUploadVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelVO;
import com.hoan.pagingexcel.prototype.service.PrototypeService;
import com.poiji.bind.Poiji;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Controller
public class PrototypeController {

    private final PrototypeService prototypeService;
    private final CommonMapper commonMapper;

    @GetMapping({"", "/"})
    public String redirectToPrototype() {
        return "redirect:/prototype1-1";
    }

    @GetMapping("/prototype1-1")
    public String getPrototype() {
        return "prototype";
    }

    @GetMapping( {"/prototype1-2","/prototype1-3","/prototype1-4"})
    public String getDummyPage() {
        return "dummy";
    }

    @PostMapping("/getPrototypeList")
    public ResponseEntity getPrototypeList(@RequestBody PageVO requestPage) {
        HashMap<String,Object> response = prototypeService.getPrototypeList(requestPage);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getPrototypeExcelTemplate")
    public void downloadStatisticsExcel(HttpServletResponse response) throws IOException {
        List<PrototypeExcelUploadVO> excelTemplate = new ArrayList<>();
        ExcelFile<PrototypeExcelUploadVO> excelFile = new OneSheetExcelFile<>(excelTemplate, PrototypeExcelUploadVO.class, commonMapper);

        excelFile.write(makeTitleWithDate("PROTOTYPE_UPLOAD_TEMPLATE"), response);
    }

    @GetMapping("/getPrototypeList/excel")
    public void downloadPrototypeListExcel(HttpServletResponse response) throws IOException {
        List<PrototypeExcelVO> statistics = prototypeService.getPrototypeListExcel();
        ExcelFile<PrototypeExcelVO> excelFile = new OneSheetExcelFile<>(statistics, PrototypeExcelVO.class, commonMapper);
        excelFile.write(makeTitleWithDate("PROTOTYPE_"), response);
    }

    @PostMapping("/getPrototypeList/uploadExcel")
    public ResponseEntity uploadExcel(MultipartHttpServletRequest request) throws IOException {

        HashMap<String, Object> response = new HashMap<>();
        List<PrototypeExcelUploadVO> prototypeExcelUploadVOS = null;
        try {
            File file = transferFile(request);
            prototypeExcelUploadVOS = Poiji.fromExcel(file, PrototypeExcelUploadVO.class);

            prototypeService.registerExcel(prototypeExcelUploadVOS);

            response.put("isSuccess", true);
        } catch (Exception e) {
            response.put("isSuccess", false);
        }

        return ResponseEntity.ok(response);
    }


    private String makeTitleWithDate(String title) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return "attachment;filename="+title+currentDate.format(formatter)+".xlsx";
    }

    private File transferFile(MultipartHttpServletRequest request) throws IOException {
        MultipartFile file = null;
        Iterator<String> mIterator = request.getFileNames();

        if(mIterator.hasNext()) {
            file = request.getFile(mIterator.next());
        }

        File tempFile = File.createTempFile("temp", ".xlsx");

        file.transferTo(tempFile);

        return tempFile;
    }

}
