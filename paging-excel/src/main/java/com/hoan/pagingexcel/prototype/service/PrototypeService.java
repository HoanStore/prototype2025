package com.hoan.pagingexcel.prototype.service;

import com.hoan.pagingexcel.common.domain.PageVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelUploadVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeVO;

import java.util.HashMap;
import java.util.List;

public interface PrototypeService {

    PrototypeVO getPrototype(PageVO pageVO);

    HashMap<String, Object> getPrototypeList(PageVO requestPage);

    List<PrototypeExcelVO> getPrototypeListExcel();

    void registerExcel(List<PrototypeExcelUploadVO> prototypeExcelUploadVOS);

    void registerPrototype(PrototypeVO exampleVO);
}
