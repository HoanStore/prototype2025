package com.hoan.pagingexcel.common.util.excel_module.resource;

import com.hoan.pagingexcel.common.mapper.CommonMapper;
import com.hoan.pagingexcel.common.util.excel_module.DefaultBodyStyle;
import com.hoan.pagingexcel.common.util.excel_module.DefaultHeaderStyle;
import com.hoan.pagingexcel.common.util.excel_module.ExcelColumn;
import com.hoan.pagingexcel.common.util.excel_module.ExcelColumnStyle;
import com.hoan.pagingexcel.common.util.excel_module.exception.InvalidExcelCellStyleException;
import com.hoan.pagingexcel.common.util.excel_module.exception.NoExcelColumnAnnotationsException;
import com.hoan.pagingexcel.common.util.excel_module.resource.collection.PreCalculatedCellStyleMap;
import com.hoan.pagingexcel.common.util.excel_module.style.ExcelCellStyle;
import com.hoan.pagingexcel.common.util.excel_module.style.NoExcelCellStyle;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.hoan.pagingexcel.common.util.excel_module.utils.SuperClassReflectionUtils.getAllFields;
import static com.hoan.pagingexcel.common.util.excel_module.utils.SuperClassReflectionUtils.getAnnotation;


/**
 * ExcelRenderResourceFactory
 *
 */
@Slf4j
public final class ExcelRenderResourceFactory {

	public static ExcelRenderResource prepareRenderResource(Class<?> type, SXSSFWorkbook wb,
															DataFormatDecider dataFormatDecider,
															CommonMapper commonMapper) {
		PreCalculatedCellStyleMap styleMap = new PreCalculatedCellStyleMap(dataFormatDecider);
		Map<String, String> headerNamesMap = new LinkedHashMap<>();
		List<String> fieldNames = new ArrayList<>();
		Map<String, List<String>> columnDataValidationOptions = new LinkedHashMap<>();

		ExcelColumnStyle classDefinedHeaderStyle = getHeaderExcelColumnStyle(type);
		ExcelColumnStyle classDefinedBodyStyle = getBodyExcelColumnStyle(type);

		for (Field field : getAllFields(type)) {
			if (field.isAnnotationPresent(ExcelColumn.class)) {
				ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
				styleMap.put(
						String.class,
						ExcelCellKey.of(field.getName(), ExcelRenderLocation.HEADER),
						getCellStyle(decideAppliedStyleAnnotation(classDefinedHeaderStyle, annotation.headerStyle())), wb);
				Class<?> fieldType = field.getType();
				styleMap.put(
						fieldType,
						ExcelCellKey.of(field.getName(), ExcelRenderLocation.BODY),
						getCellStyle(decideAppliedStyleAnnotation(classDefinedBodyStyle, annotation.bodyStyle())), wb);
				fieldNames.add(field.getName());
				headerNamesMap.put(field.getName(), annotation.headerName());

			}
		}

		if (styleMap.isEmpty()) {
			throw new NoExcelColumnAnnotationsException(String.format("Class %s has not @ExcelColumn at all", type));
		}

		applyDataValidation(wb, columnDataValidationOptions, fieldNames);

		return new ExcelRenderResource(styleMap, headerNamesMap, fieldNames, columnDataValidationOptions, wb);
	}



	private static ExcelColumnStyle getHeaderExcelColumnStyle(Class<?> clazz) {
		Annotation annotation = getAnnotation(clazz, DefaultHeaderStyle.class);
		if (annotation == null) {
			return null;
		}
		return ((DefaultHeaderStyle) annotation).style();
	}

	private static ExcelColumnStyle getBodyExcelColumnStyle(Class<?> clazz) {
		Annotation annotation = getAnnotation(clazz, DefaultBodyStyle.class);
		if (annotation == null) {
			return null;
		}
		return ((DefaultBodyStyle) annotation).style();
	}

	private static ExcelColumnStyle decideAppliedStyleAnnotation(ExcelColumnStyle classAnnotation,
																 ExcelColumnStyle fieldAnnotation) {
		if (fieldAnnotation.excelCellStyleClass().equals(NoExcelCellStyle.class) && classAnnotation != null) {
			return classAnnotation;
		}
		return fieldAnnotation;
	}

	private static ExcelCellStyle getCellStyle(ExcelColumnStyle excelColumnStyle) {
		Class<? extends ExcelCellStyle> excelCellStyleClass = excelColumnStyle.excelCellStyleClass();
		// 1. Case of Enum
		if (excelCellStyleClass.isEnum()) {
			String enumName = excelColumnStyle.enumName();
			return findExcelCellStyle(excelCellStyleClass, enumName);
		}

		// 2. Case of Class
		try {
			return excelCellStyleClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new InvalidExcelCellStyleException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	private static ExcelCellStyle findExcelCellStyle(Class<?> excelCellStyles, String enumName) {
		try {
			return (ExcelCellStyle) Enum.valueOf((Class<Enum>) excelCellStyles, enumName);
		} catch (NullPointerException e) {
			throw new InvalidExcelCellStyleException("enumName must not be null", e);
		} catch (IllegalArgumentException e) {
			throw new InvalidExcelCellStyleException(
					String.format("Enum %s does not name %s", excelCellStyles.getName(), enumName), e);
		}
	}

	private static void applyDataValidation(SXSSFWorkbook workbook, Map<String, List<String>> columnDataValidationOptions, List<String> dataFieldNames) {
		XSSFWorkbook xssfWorkbook = (XSSFWorkbook)workbook.getXSSFWorkbook();
		if (xssfWorkbook.getNumberOfSheets() > 0) {
			XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
			DataValidationHelper helper = new XSSFDataValidationHelper(sheet);

			columnDataValidationOptions.forEach((columnName, options) -> {
				int colIndex = getColumnIndex(columnName, dataFieldNames);
				if (colIndex != -1) {
					String[] optionsArray = options.toArray(new String[0]);
					DataValidationConstraint constraint = helper.createExplicitListConstraint(optionsArray);
					CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, colIndex, colIndex);
					DataValidation validation = helper.createValidation(constraint, addressList);
					validation.setSuppressDropDownArrow(false);
					sheet.addValidationData(validation);
				}
			});

		}
	}

	private static int getColumnIndex(String columnName, List<String> dataFieldNames) {
		return dataFieldNames.indexOf(columnName);
	}

}
