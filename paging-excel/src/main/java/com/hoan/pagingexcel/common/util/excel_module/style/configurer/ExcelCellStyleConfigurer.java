package com.hoan.pagingexcel.common.util.excel_module.style.configurer;

import com.hoan.pagingexcel.common.util.excel_module.style.align.ExcelAlign;
import com.hoan.pagingexcel.common.util.excel_module.style.align.NoExcelAlign;
import com.hoan.pagingexcel.common.util.excel_module.style.border.ExcelBorders;
import com.hoan.pagingexcel.common.util.excel_module.style.border.NoExcelBorders;
import com.hoan.pagingexcel.common.util.excel_module.style.color.DefaultExcelColor;
import com.hoan.pagingexcel.common.util.excel_module.style.color.ExcelColor;
import com.hoan.pagingexcel.common.util.excel_module.style.color.NoExcelColor;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelCellStyleConfigurer {

	private ExcelAlign excelAlign = new NoExcelAlign();
	private ExcelColor foregroundColor = new NoExcelColor();
	private ExcelBorders excelBorders = new NoExcelBorders();

	public ExcelCellStyleConfigurer() {

	}

	public ExcelCellStyleConfigurer excelAlign(ExcelAlign excelAlign) {
		this.excelAlign = excelAlign;
		return this;
	}

	public ExcelCellStyleConfigurer foregroundColor(int red, int blue, int green) {
		this.foregroundColor = DefaultExcelColor.rgb(red, blue, green);
		return this;
	}

	public ExcelCellStyleConfigurer excelBorders(ExcelBorders excelBorders) {
		this.excelBorders = excelBorders;
		return this;
	}

	public void configure(CellStyle cellStyle) {
		excelAlign.apply(cellStyle);
		foregroundColor.applyForeground(cellStyle);
		excelBorders.apply(cellStyle);
	}

}
