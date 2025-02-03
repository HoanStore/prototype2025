package com.hoan.pagingexcel.common.util.excel_module.excel.onesheet;



import com.hoan.pagingexcel.common.mapper.CommonMapper;
import com.hoan.pagingexcel.common.util.excel_module.excel.SXSSFExcelFile;
import com.hoan.pagingexcel.common.util.excel_module.resource.DataFormatDecider;
import com.hoan.pagingexcel.common.util.excel_module.resource.DefaultDataFormatDecider;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.util.List;

/**
 * OneSheetExcelFile
 *
 * - support Excel Version over 2007
 * - support one sheet rendering
 * - support different DataFormat by Class Type
 * - support Custom CellStyle according to (header or body) and data field
 */
public final class OneSheetExcelFile<T> extends SXSSFExcelFile<T> {

	private static final int ROW_START_INDEX = 0;
	private static final int COLUMN_START_INDEX = 0;
	private int currentRowIndex = ROW_START_INDEX;

	public OneSheetExcelFile(Class<T> type) {
		super(type);
	}

	public OneSheetExcelFile(List<T> data, Class<T> type, CommonMapper commonMapper) {
		super(data, type, new DefaultDataFormatDecider(), commonMapper);
	}

	public OneSheetExcelFile(List<T> data, Class<T> type, DataFormatDecider dataFormatDecider, CommonMapper commonMapper) {
		super(data, type, dataFormatDecider, commonMapper);
	}

	@Override
	protected void validateData(List<T> data) {
		int maxRows = supplyExcelVersion.getMaxRows();
		if (data.size() > maxRows) {
			throw new IllegalArgumentException(
					String.format("This concrete ExcelFile does not support over %s rows", maxRows));
		}
	}

//	@Override
//	public void renderExcel(List<T> data) {
//		// 1. Create sheet and renderHeader
//		sheet = wb.createSheet();
//		renderHeadersWithNewSheet(sheet, currentRowIndex++, COLUMN_START_INDEX);
//
//		if (data.isEmpty()) {
//			return;
//		}
//
//		// 2. Render Body
//		for (Object renderedData : data) {
//			renderBody(renderedData, currentRowIndex++, COLUMN_START_INDEX);
//		}
//	}

	@Override
	protected void renderExcel(List<T> data) {
		this.sheet = this.wb.createSheet();
		renderHeadersWithNewSheet(sheet, 0, 0);

		if (data.isEmpty()) return;

		for (int i = 0; i < data.size(); i++) {
			renderBody(data.get(i), i + 1, 0);
		}

		int columnCount = data.get(0).getClass().getDeclaredFields().length;
		if (sheet instanceof SXSSFSheet) {
			// 모든 열을 트래킹하도록 설정
			((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
		}
		// 모든 데이터를 작성한 후에 열의 크기를 자동으로 조정
		for (int k = 0; k < columnCount; k++) {
			sheet.autoSizeColumn(k); // 열 너비 자동 조정
			int maxColumnWidth = 255 * 256; // POI에서 열 너비는 1/256의 단위로 설정됨
			int currentColumnWidth = sheet.getColumnWidth(k) + 1024; // 너비를 추가로 넓힘

			// 열 너비가 최대 값을 초과하지 않도록 제한
			if (currentColumnWidth > maxColumnWidth) {
				currentColumnWidth = maxColumnWidth;
			}
			sheet.setColumnWidth(k, currentColumnWidth);
		}
		applyDataValidation((SXSSFSheet) this.sheet);
	}

	@Override
	public void addRows(List<T> data) {
		for (T item : data) {
			renderBody(item, currentRowIndex++, COLUMN_START_INDEX);
		}
	}

}
