package com.hoan.pagingexcel.prototype.mapper;

import com.hoan.pagingexcel.common.domain.PageVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrototypeMapper {
    int getPrototypeListCnt(PageVO pageVO);
    List<PrototypeVO> getPrototypeList(PageVO pageVO);
}
