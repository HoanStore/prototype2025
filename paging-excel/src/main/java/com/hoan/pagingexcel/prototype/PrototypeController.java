package com.hoan.pagingexcel.prototype;


import com.hoan.pagingexcel.common.domain.PageVO;
import com.hoan.pagingexcel.common.enums.MgmtTypes;
import com.hoan.pagingexcel.common.mapper.CommonMapper;
import com.hoan.pagingexcel.common.service.ExcelDownloadService;
import com.hoan.pagingexcel.common.util.excel_module.excel.ExcelFile;
import com.hoan.pagingexcel.common.util.excel_module.excel.onesheet.OneSheetExcelFile;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelUploadVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeVer2VO;
import com.hoan.pagingexcel.prototype.service.PrototypeService;
import com.poiji.bind.Poiji;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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

    private final ExcelDownloadService excelDownloadService;
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


    @GetMapping("/prototype2-1")
    public String getPrototype2() {
        return "prototype";
    }

    @GetMapping( {"/prototype1-2","/prototype1-3","/prototype1-4"})
    public String getDummyPage() {
        return "prototype-ver2";
    }

    @GetMapping("/prototype1-1/{id}")
    public String getPrototype(@PathVariable String id,
                              ModelMap modelMap) {
        PageVO pageVO = PageVO.builder()
                .bno(id)
                .build();

        PrototypeVO prototypeVO = prototypeService.getPrototype(pageVO);

        modelMap.addAttribute("infos", prototypeVO);

        return "prototypeDetail";
    }

    @GetMapping("/register")
    public String getPrototypeRegister(ModelMap modelMap) {
        return "prototypeRegister";
    }

    @GetMapping("/modify/{bno}")
    public String getPrototypeModify(@PathVariable String bno,
                                   ModelMap modelMap) {

        PageVO pageVO = PageVO.builder()
                .bno(bno)
                .build();

        PrototypeVO prototype = prototypeService.getPrototype(pageVO);
        modelMap.addAttribute("infos", prototype);

        return "prototypeModify";
    }


    @PostMapping("/getPrototypeList")
    public ResponseEntity getPrototypeList(@RequestBody PageVO requestPage) {
        HashMap<String,Object> response = prototypeService.getPrototypeList(requestPage);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/getPrototypeVer2List")
    public ResponseEntity getPrototypeVer2List(Pageable pageable) {
        HashMap<String,Object> response = prototypeService.getPrototypeVer2List(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity registerExample(@ModelAttribute PrototypeVO exampleVO) {
        HashMap<String,Object> response = new HashMap<>();

        try {
            prototypeService.registerPrototype(exampleVO);
            response.put("isSuccess", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("isSuccess", false);
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/modify")
    public ResponseEntity modifyPrototype(@ModelAttribute PrototypeVO prototypeVO) {
        HashMap<String,Object> response = new HashMap<>();

        try {
            prototypeService.modifyPrototype(prototypeVO);
            response.put("isSuccess", true);
        } catch (Exception e) {
            response.put("isSuccess", false);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/prototype1-1/{id}")
    public ResponseEntity deleteExample(@PathVariable String id, @RequestParam String attflId) {
        HashMap<String,Object> response = new HashMap<>();

        try {
            prototypeService.deletePrototype(id, MgmtTypes.EXAMPLES.getValue(), attflId);
            response.put("isSuccess", true);
        } catch (Exception e) {
            response.put("isSuccess", false);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getPrototypeExcelTemplate")
    public void downloadStatisticsExcel(HttpServletResponse response) throws IOException {
        List<PrototypeExcelUploadVO> excelTemplate = new ArrayList<>();
        ExcelFile<PrototypeExcelUploadVO> excelFile = new OneSheetExcelFile<>(excelTemplate, PrototypeExcelUploadVO.class, commonMapper);

        excelFile.write(makeTitleWithDate("PROTOTYPE_UPLOAD_TEMPLATE"), response);
    }

    @GetMapping("/getPrototypeExcelVer2")
    public void downloadExcelVer2(HttpServletResponse response) throws IOException {
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

    @Transactional
    @GetMapping("/getPrototypeList/excel-ver2")
    public ResponseEntity<byte[]> downloadPrototypeListExcelVer2(HttpServletResponse response) throws IOException {
        Cursor<PrototypeVer2VO> prototypeVer2Excel = prototypeService.getPrototypeVer2Excel();
        return excelDownloadService.downloadExcel(prototypeVer2Excel, "/excel-templates/PROTOTYPE_VER2_TEMPLATE.xlsx", "TEST_EXCEL_VER2");
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
