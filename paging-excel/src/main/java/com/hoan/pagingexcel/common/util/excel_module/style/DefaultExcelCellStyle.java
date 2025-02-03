package com.hoan.pagingexcel.common.util.excel_module.style;


import com.hoan.pagingexcel.common.util.excel_module.style.align.DefaultExcelAlign;
import com.hoan.pagingexcel.common.util.excel_module.style.align.ExcelAlign;
import com.hoan.pagingexcel.common.util.excel_module.style.border.DefaultExcelBorders;
import com.hoan.pagingexcel.common.util.excel_module.style.border.ExcelBorderStyle;
import com.hoan.pagingexcel.common.util.excel_module.style.color.DefaultExcelColor;
import com.hoan.pagingexcel.common.util.excel_module.style.color.ExcelColor;
import com.hoan.pagingexcel.common.util.excel_module.style.font.DefaultExcelFont;
import com.hoan.pagingexcel.common.util.excel_module.style.font.ExcelFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Example of using ExcelCellStyle as Enum
 */
public enum DefaultExcelCellStyle implements ExcelCellStyle {

	GREY_HEADER(DefaultExcelColor.rgb(217, 217, 217),
			DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER,
			DefaultExcelFont.BOLD),
	BLUE_HEADER(DefaultExcelColor.rgb(223, 235, 246),
			DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.CENTER_CENTER,
			DefaultExcelFont.BOLD),
	BODY(DefaultExcelColor.rgb(255, 255, 255),
			DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN), DefaultExcelAlign.LEFT_CENTER,
			DefaultExcelFont.DEFAULT);

	private final ExcelColor backgroundColor;
	/**
	 * like CSS margin or padding rule,
	 * List<DefaultExcelBorder> represents rgb TOP RIGHT BOTTOM LEFT
	 */
	private final DefaultExcelBorders borders;
	private final ExcelAlign align;
	private final ExcelFont font;

	DefaultExcelCellStyle(ExcelColor backgroundColor, DefaultExcelBorders borders, ExcelAlign align, ExcelFont font) {
		this.backgroundColor = backgroundColor;
		this.borders = borders;
		this.align = align;
		this.font = font;
	}

	@Override
	public void apply(Workbook workbook, CellStyle cellStyle) {
		backgroundColor.applyForeground(cellStyle);
		borders.apply(cellStyle);
		align.apply(cellStyle);
		font.apply(workbook, cellStyle);

		cellStyle.setWrapText(true); // 텍스트 줄바꿈
	}

}
