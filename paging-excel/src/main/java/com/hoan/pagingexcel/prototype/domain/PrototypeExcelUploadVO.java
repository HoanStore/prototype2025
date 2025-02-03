package com.hoan.pagingexcel.prototype.domain;

import com.hoan.pagingexcel.common.util.excel_module.DefaultBodyStyle;
import com.hoan.pagingexcel.common.util.excel_module.DefaultHeaderStyle;
import com.hoan.pagingexcel.common.util.excel_module.ExcelColumn;
import com.hoan.pagingexcel.common.util.excel_module.ExcelColumnStyle;
import com.hoan.pagingexcel.common.util.excel_module.style.DefaultExcelCellStyle;
import com.poiji.annotation.ExcelCell;
import lombok.Data;

@DefaultHeaderStyle(
        style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BLUE_HEADER")
)
@DefaultBodyStyle(
        style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BODY")
)
@Data
public class PrototypeExcelUploadVO {

    @ExcelCell(0)
    @ExcelColumn(headerName = "제목")
    private String title;
    @ExcelCell(1)
    @ExcelColumn(headerName = "내용")
    private String content;
}
