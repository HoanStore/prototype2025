package com.hoan.pagingexcel.common.mapper;

import com.hoan.pagingexcel.common.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonMapper {

    int insertFile(FileVO fileVO);

    void insertFlagFile(NationalCodeVO nationalCodeVO);


    int insertFileDetail(FileDetailVO fileDetailVO);

    FileVO selectFile(FileVO fileVO);

    List<FileDetailVO> selectFileDetails(FileVO fileVO);


    String selectNewAttflId(String mgmtType);

    FileVO selectFileByAttflId(String attflId);

    List<FileDetailVO> selectFileDetailsByAttflId(String attflId);

    List<FileDetailVO> selectLogoDetailsByAttflId(String attflId);

    FileDetailVO selectFileNmAndPath(String attflId, Long attflSeq);

    void deleteFileDetail(String attflId, Long attflSeq);

    void deleteFile(String attflId);

    void deleteAllFileDetail(String attflId);

    List<NationalCodeVO> selectPbanTypeCode();

    List<NationalCodeVO> selectNationalCode();

    List<ModifyHistVO> selectModifyHists(ModifyHistVO modifyHistVO);

    void registerModifyHist(ModifyHistVO modifyHistVO);

    void deleteModifyHist(ModifyHistVO modifyHistVO);

    NationalCodeVO getNationalCodeByNtnlNm(String ntnlNm);

    NationalCodeVO getContinentCodeByCnttNm(String cnttNm);

    CmmnCodeVO getBizCode(String detlCdNm);

    List<CmmnCodeVO> selectCmmnCodeListByGroupId(String cdGrpId);

    String getDetlCdByDetlCdNm(String detlCdNm, String detlCdClmnNm);

    List<String> getAllCnttNm();

    List<String> getAllNtnlNm();

    List<String> getAllBizTypeNm();

    List<String> getAllBizFormTypeNm();

    List<String> getAllMnbdTypeNm();

    List<String> getAllCllbrTypeNm();

    List<String> getAllPrmtTypeNm();

    List<String> getAllRTypeNm();

}

