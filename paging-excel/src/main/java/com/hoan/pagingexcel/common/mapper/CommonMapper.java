package com.hoan.pagingexcel.common.mapper;

import com.hoan.pagingexcel.common.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonMapper {

    int insertFile(FileVO fileVO);

    int insertFileDetail(FileDetailVO fileDetailVO);

    String selectNewAttflId(String mgmtType);

    FileVO selectFileByAttflId(String attflId);

    List<FileDetailVO> selectFileDetailsByAttflId(String attflId);

    FileDetailVO selectFileNmAndPath(String attflId, Long attflSeq);

    void deleteFileDetail(String attflId, Long attflSeq);

    void deleteFile(String attflId);

    void deleteAllFileDetail(String attflId);

    List<MenuVO> getAllMenuByDB();

}

