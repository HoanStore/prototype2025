package com.hoan.pagingexcel.common.domain.excel;

import java.util.List;

public interface ExcelExportable {
    List<Object> toExcelOrderedList();
}
