package com.hoan.pagingexcel.prototype.mapper;

import com.hoan.pagingexcel.common.domain.PageVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelUploadVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrototypeMapper {

    int getPrototypeListCnt(PageVO pageVO);

    PrototypeVO getPrototype(PageVO pageVO);

    List<PrototypeVO> getPrototypeList(PageVO pageVO);

    List<PrototypeExcelVO> getPrototypeListExcel();

    void registerPrototypeData(PrototypeExcelUploadVO prototypeExcelVO);

    void registerPrototype(PrototypeVO prototypeVO);

    void deletePrototype(String id);

    void modifyPrototype(PrototypeVO prototypeVO);
}
