package com.hoan.pagingexcel.prototype.domain;

import com.hoan.pagingexcel.common.domain.excel.ExcelExportable;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Builder
@Data
public class PrototypeVer2VO implements ExcelExportable {

    private String id;
    private String title;
    private String content;
    private String createdAt;

    @Override
    public List<Object> toExcelOrderedList() {
        return Arrays.asList(id, title, content, createdAt);
    }
}
