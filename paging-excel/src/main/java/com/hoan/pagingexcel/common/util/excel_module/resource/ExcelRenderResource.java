package com.hoan.pagingexcel.common.util.excel_module.resource;

import com.hoan.pagingexcel.common.util.excel_module.resource.collection.PreCalculatedCellStyleMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelRenderResource {

	private PreCalculatedCellStyleMap styleMap;

	// TODO dataFieldName -> excelHeaderName Map Abstraction
	private Map<String, String> excelHeaderNames;
	private List<String> dataFieldNames;
	private Map<String, List<String>> dataValidationListMap;
	private Workbook workbook;

	public ExcelRenderResource(PreCalculatedCellStyleMap styleMap,
							   Map<String, String> excelHeaderNames,
							   List<String> dataFieldNames,
							   Map<String, List<String>> dataValidationListMap,
							   Workbook workbook) {
		this.styleMap = styleMap;
		this.excelHeaderNames = excelHeaderNames;
		this.dataFieldNames = dataFieldNames;
		this.dataValidationListMap = dataValidationListMap;
		this.workbook = workbook;
	}

	public CellStyle getCellStyle(String dataFieldName, ExcelRenderLocation excelRenderLocation) {
		return styleMap.get(ExcelCellKey.of(dataFieldName, excelRenderLocation));
	}

	public String getExcelHeaderName(String dataFieldName) {
		return excelHeaderNames.get(dataFieldName);
	}

	public List<String> getDataFieldNames() {
		return dataFieldNames;
	}

	public Map<String, List<String>> getDataValidationOptions() {
		return dataValidationListMap;
	}

	public void applyDataValidation(SXSSFSheet sheet) {
		if (dataValidationListMap != null) {
			DataValidationHelper validationHelper = sheet.getDataValidationHelper();

			dataValidationListMap.forEach((key, value) -> {
				int columnIndex = getColumnIndex(key);
				if (columnIndex != -1) {
					if (value.size() > 50) {
						addDataValidationFromNamedRange(sheet, validationHelper, value, key, columnIndex);
					} else {
						DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(value.toArray(new String[0]));
						CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, columnIndex, columnIndex);
						DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
						sheet.addValidationData(dataValidation);
					}
				}
			});
		}
	}

	private int getColumnIndex(String key) {
		return dataFieldNames.indexOf(key);
	}

	private void addDataValidationFromNamedRange(Sheet sheet, DataValidationHelper validationHelper, List<String> value, String name, int columnIndex) {

		XSSFWorkbook xssfWorkbook = null;
		if (workbook instanceof XSSFWorkbook) {
			xssfWorkbook = (XSSFWorkbook) workbook;
		} else if (workbook instanceof SXSSFWorkbook) {
			xssfWorkbook = (XSSFWorkbook) ((SXSSFWorkbook) workbook).getXSSFWorkbook();
		}

		if (xssfWorkbook != null) {
			String hiddenSheetName = getUniqueSheetName(xssfWorkbook, name + "_hidden");

			XSSFSheet hiddenSheet = xssfWorkbook.createSheet(hiddenSheetName);

			for (int i = 0; i < value.size(); i++) {
				Row row = hiddenSheet.createRow(i);
				Cell cell = row.createCell(0);
				cell.setCellValue(value.get(i));
			}

			XSSFName existingName = xssfWorkbook.getName(name + "_range");
			if (existingName != null) {
				xssfWorkbook.removeName(existingName);
			}

			Name namedRange = xssfWorkbook.createName();
			namedRange.setNameName(name + "_range");
//			namedRange.setRefersToFormula(name + "_hidden!$A$1:$A$" + value.size());
			namedRange.setRefersToFormula(hiddenSheet.getSheetName() + "!$A$1:$A$" + value.size());

			DataValidationConstraint constraint = validationHelper.createFormulaListConstraint(name + "_range");
			CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, columnIndex, columnIndex);
			DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);

			dataValidation.setSuppressDropDownArrow(true);
			dataValidation.setShowErrorBox(true);

			sheet.addValidationData(dataValidation);
			xssfWorkbook.setSheetHidden(xssfWorkbook.getSheetIndex(hiddenSheet), true);
		}
	}

	private void applyDataValidationForList(SXSSFSheet sheet, DataValidationHelper validationHelper, String key, List<String> value) {
		applyDataValidationForList(sheet, validationHelper, key, value, 1);
	}

	private void applyDataValidationForList(SXSSFSheet sheet, DataValidationHelper validationHelper, String key, List<String> value, int column) {
		DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(value.toArray(new String[0]));
		CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, column, column); // Example range
		DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);
		sheet.addValidationData(dataValidation);
	}

	private String getUniqueSheetName(XSSFWorkbook workbook, String baseName) {
		String sheetName = baseName;
		int suffix = 1;
		while (workbook.getSheet(sheetName) != null) {
			sheetName = baseName + "_" + suffix;
			suffix++;
		}
		return sheetName;
	}


}
