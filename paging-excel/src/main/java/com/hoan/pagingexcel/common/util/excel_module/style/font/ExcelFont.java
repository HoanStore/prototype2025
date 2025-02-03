package com.hoan.pagingexcel.common.util.excel_module.style.font;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelFont {

    void apply(Workbook workbook, CellStyle cellStyle);

}
