package com.hoan.pagingexcel.common.util.excel_module.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class NoExcelCellStyle implements ExcelCellStyle {

	@Override
	public void apply(Workbook workbook, CellStyle cellStyle) {
		// Do nothing
	}

}
