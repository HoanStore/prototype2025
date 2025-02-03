package com.hoan.pagingexcel.common.util.excel_module.excel;

import com.hoan.pagingexcel.common.mapper.CommonMapper;
import com.hoan.pagingexcel.common.util.excel_module.exception.ExcelInternalException;
import com.hoan.pagingexcel.common.util.excel_module.resource.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static com.hoan.pagingexcel.common.util.excel_module.utils.SuperClassReflectionUtils.getField;


public abstract class SXSSFExcelFile<T> implements ExcelFile<T> {

	protected static final SpreadsheetVersion supplyExcelVersion = SpreadsheetVersion.EXCEL2007;

	protected SXSSFWorkbook wb;
	protected Sheet sheet;
	protected ExcelRenderResource resource;

	/**
	 *SXSSFExcelFile
	 * @param type Class type to be rendered
	 */
	public SXSSFExcelFile(Class<T> type) {
		this(Collections.emptyList(), type, new DefaultDataFormatDecider(), null);
	}

	/**
	 * SXSSFExcelFile
	 * @param data List Data to render excel file. data should have at least one @ExcelColumn on fields
	 * @param type Class type to be rendered
	 */
	public SXSSFExcelFile(List<T> data, Class<T> type) {
		this(data, type, new DefaultDataFormatDecider(), null);
	}

	/**
	 * SXSSFExcelFile
	 * @param data List Data to render excel file. data should have at least one @ExcelColumn on fields
	 * @param type Class type to be rendered
	 * @param dataFormatDecider Custom DataFormatDecider
	 */
	public SXSSFExcelFile(List<T> data, Class<T> type, DataFormatDecider dataFormatDecider, CommonMapper commonMapper) {
		validateData(data);
		this.wb = new SXSSFWorkbook();
		this.resource = ExcelRenderResourceFactory.prepareRenderResource(type, wb, dataFormatDecider, commonMapper);
		renderExcel(data);
	}

	protected void validateData(List<T> data) { }

	protected abstract void renderExcel(List<T> data);

	protected void renderHeadersWithNewSheet(Sheet sheet, int rowIndex, int columnStartIndex) {
		Row row = sheet.createRow(rowIndex);
		int columnIndex = columnStartIndex;
		for (String dataFieldName : resource.getDataFieldNames()) {
			Cell cell = row.createCell(columnIndex++);
			cell.setCellStyle(resource.getCellStyle(dataFieldName, ExcelRenderLocation.HEADER));
			cell.setCellValue(resource.getExcelHeaderName(dataFieldName));
		}
		applyDataValidation(sheet);
	}

	protected void renderBody(Object data, int rowIndex, int columnStartIndex) {
		Row row = sheet.createRow(rowIndex);
		int columnIndex = columnStartIndex;
		for (String dataFieldName : resource.getDataFieldNames()) {
			Cell cell = row.createCell(columnIndex++);
			try {
				Field field = getField(data.getClass(), (dataFieldName));
				field.setAccessible(true);
				cell.setCellStyle(resource.getCellStyle(dataFieldName, ExcelRenderLocation.BODY));
				Object cellValue = field.get(data);
				renderCellValue(cell, cellValue);
			} catch (Exception e) {
				throw new ExcelInternalException(e.getMessage(), e);
			}
		}
	}

	private void renderCellValue(Cell cell, Object cellValue) {
		if (cellValue instanceof Number) {
			Number numberValue = (Number) cellValue;
			cell.setCellValue(numberValue.doubleValue());
			return;
		}
		cell.setCellValue(cellValue == null ? "" : cellValue.toString());
	}

	public void write(OutputStream stream) throws IOException {
		wb.write(stream);
		wb.close();
		wb.dispose();
		stream.close();
	}

	public void write(HttpServletResponse response) throws IOException {
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment;filename=ExcelDown.xlsx");

		wb.write(response.getOutputStream());
		wb.close();
		wb.dispose();
		response.getOutputStream().close();
	}


	public void write(String title, HttpServletResponse response) throws IOException {
		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition", title);

		wb.write(response.getOutputStream());
		wb.close();
		wb.dispose();
		response.getOutputStream().close();
	}

	protected void applyDataValidation(Sheet sheet) {
		if (sheet instanceof SXSSFSheet) {
			resource.applyDataValidation((SXSSFSheet) sheet);
		}
	}
}
