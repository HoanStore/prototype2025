package com.hoan.pagingexcel.common.util.excel_module.resource.collection;


import com.hoan.pagingexcel.common.util.excel_module.resource.DataFormatDecider;
import com.hoan.pagingexcel.common.util.excel_module.resource.ExcelCellKey;
import com.hoan.pagingexcel.common.util.excel_module.style.ExcelCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.Map;

/**
 * PreCalculatedCellStyleMap
 *
 * Determines cell's style
 * In currently, PreCalculatedCellStyleMap determines {org.apache.poi.ss.usermodel.DataFormat}
 *
 */
public class PreCalculatedCellStyleMap {

	private final DataFormatDecider dataFormatDecider;

	public PreCalculatedCellStyleMap(DataFormatDecider dataFormatDecider) {
		this.dataFormatDecider = dataFormatDecider;
	}

	private final Map<ExcelCellKey, CellStyle> cellStyleMap = new HashMap<>();

	public void put(Class<?> fieldType, ExcelCellKey excelCellKey, ExcelCellStyle excelCellStyle, Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		DataFormat dataFormat = wb.createDataFormat();
		cellStyle.setDataFormat(dataFormatDecider.getDataFormat(dataFormat, fieldType));
		excelCellStyle.apply(wb, cellStyle);
		cellStyleMap.put(excelCellKey, cellStyle);
	}

	public CellStyle get(ExcelCellKey excelCellKey) {
		return cellStyleMap.get(excelCellKey);
	}

	public boolean isEmpty() {
		return cellStyleMap.isEmpty();
	}

}
