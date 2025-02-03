package com.hoan.pagingexcel.common.util.excel_module;


import com.hoan.pagingexcel.common.util.excel_module.style.ExcelCellStyle;

public @interface ExcelColumnStyle {

	/**
	 * Enum implements {@link ExcelCellStyle}
	 * Also, can use just class.
	 * If not use Enum, enumName will be ignored
	 * @see com.hoan.pagingexcel.common.util.excel_module.style.DefaultExcelCellStyle
	 * @see com.hoan.pagingexcel.common.util.excel_module.style.CustomExcelCellStyle
	 */
	Class<? extends ExcelCellStyle> excelCellStyleClass();

	/**
	 * name of Enum implements {@link ExcelCellStyle}
	 * if not use Enum, enumName will be ignored
	 */
	String enumName() default "";

}
