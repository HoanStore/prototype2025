package com.hoan.pagingexcel.common.util.excel_module.style;

import com.hoan.pagingexcel.common.util.excel_module.style.configurer.ExcelCellStyleConfigurer;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class CustomExcelCellStyle implements ExcelCellStyle {

	private ExcelCellStyleConfigurer configurer = new ExcelCellStyleConfigurer();

	public CustomExcelCellStyle() {
		configure(configurer);
	}

	public abstract void configure(ExcelCellStyleConfigurer configurer);

	@Override
	public void apply(Workbook workbook, CellStyle cellStyle) {
		configurer.configure(cellStyle);
	}

}
