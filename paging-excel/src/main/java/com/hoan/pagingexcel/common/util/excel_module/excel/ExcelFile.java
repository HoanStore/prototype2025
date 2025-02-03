package com.hoan.pagingexcel.common.util.excel_module.excel;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ExcelFile<T> {

	void write(OutputStream stream) throws IOException;
	void write(HttpServletResponse response) throws IOException;
	void write(String title, HttpServletResponse response) throws IOException;



	void addRows(List<T> data);

}
