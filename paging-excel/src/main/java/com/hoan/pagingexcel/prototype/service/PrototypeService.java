package com.hoan.pagingexcel.prototype.service;

import com.hoan.pagingexcel.common.domain.PageVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelUploadVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeVer2VO;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface PrototypeService {

    PrototypeVO getPrototype(PageVO pageVO);

    HashMap<String, Object> getPrototypeList(PageVO requestPage);

    List<PrototypeExcelVO> getPrototypeListExcel();
    Cursor<PrototypeVer2VO> getPrototypeVer2Excel();

    void registerExcel(List<PrototypeExcelUploadVO> prototypeExcelUploadVOS);

    void registerPrototype(PrototypeVO exampleVO);

    void deletePrototype(String id, String value, String attflId);

    void modifyPrototype(PrototypeVO prototypeVO);

    HashMap<String, Object> getPrototypeVer2List(Pageable pageable);
}
