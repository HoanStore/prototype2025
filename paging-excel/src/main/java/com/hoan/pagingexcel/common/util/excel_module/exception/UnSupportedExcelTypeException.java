package com.hoan.pagingexcel.common.util.excel_module.exception;


import com.hoan.pagingexcel.common.util.excel_module.ExcelException;

public class UnSupportedExcelTypeException extends ExcelException {

	public UnSupportedExcelTypeException(String message) {
		super(message, null);
	}

}
