package com.hoan.pagingexcel.common.util.excel_module.style.font;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class NoExcelFont implements ExcelFont{

    @Override
    public void apply(Workbook workbook, CellStyle cellStyle) {
        // do nothing
    }
}
