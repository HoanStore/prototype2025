package com.hoan.pagingexcel.common.util.excel_module.style.border;

import org.apache.poi.ss.usermodel.CellStyle;

public interface ExcelBorder {

	void applyTop(CellStyle cellStyle);

	void applyRight(CellStyle cellStyle);

	void applyBottom(CellStyle cellStyle);

	void applyLeft(CellStyle cellStyle);

}
