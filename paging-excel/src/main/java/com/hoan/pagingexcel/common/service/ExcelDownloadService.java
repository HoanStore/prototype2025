package com.hoan.pagingexcel.common.service;

import com.hoan.pagingexcel.common.domain.excel.ExcelExportable;
import org.apache.ibatis.cursor.Cursor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ExcelDownloadService {

    public <T extends ExcelExportable> ResponseEntity<byte[]> downloadExcel(Cursor<T> data, String excelTemplatePath, String filename) throws IOException {
        try (
                InputStream templateStream = new ClassPathResource(excelTemplatePath).getInputStream();
                XSSFWorkbook workbook = new XSSFWorkbook(templateStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
            Sheet sheet = workbook.getSheetAt(0);
            int startRow = findFirstEmptyRow(sheet);

            writeDataToSheet(sheet, data, startRow);

            workbook.write(baos);
            return createExcelResponse(baos.toByteArray(), filename);
        }
    }

    private int findFirstEmptyRow(Sheet sheet) {
        int rowNum = 0;
        for (Row row : sheet) {
            if (isRowEmpty(row)) {
                break;
            }
            rowNum++;
        }
        return rowNum;
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK && !cell.toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private <T extends ExcelExportable> void writeDataToSheet(Sheet sheet, Cursor<T> dataCursor, int startRow) {
        int rowNum = startRow;
        try (Cursor<T> cursor = dataCursor) {
            for (T item : cursor) {
                Row row = sheet.createRow(rowNum++);
                List<Object> values = item.toExcelOrderedList();
                for (int i = 0; i < values.size(); i++) {
                    row.createCell(i).setCellValue(String.valueOf(values.get(i)));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<byte[]> createExcelResponse(byte[] content, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + ".xlsx");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return ResponseEntity.ok()
                .headers(headers)
                .body(content);
    }

}

