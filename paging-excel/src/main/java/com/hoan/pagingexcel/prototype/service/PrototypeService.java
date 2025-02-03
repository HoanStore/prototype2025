package com.hoan.pagingexcel.prototype.service;

import com.hoan.pagingexcel.common.domain.PageVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelUploadVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelVO;

import java.util.HashMap;
import java.util.List;

public interface PrototypeService {

    HashMap<String, Object> getPrototypeList(PageVO requestPage);

    List<PrototypeExcelVO> getPrototypeListExcel();

    void registerExcel(List<PrototypeExcelUploadVO> prototypeExcelUploadVOS);
}
