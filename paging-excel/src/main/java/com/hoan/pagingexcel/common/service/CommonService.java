package com.hoan.pagingexcel.common.service;

import com.hoan.pagingexcel.common.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommonService {

    /**
     * [AS-IS]
     * uploadFiles, registerFiles,
     * appendFiles, uploadFilesWithDetailType, uploadFilesForBanner.
     * uploadLogos 등
     *
     * [권장]
     * 파일 업로드 기능은 아래 메서드 활용하여 처리하는것 권장
     * generateAttflId, saveFiles, insertFileInfoToDB
     *
     */

    String generateAttflId (String mgmtType);

    void saveFiles(List<MultipartFile> multipartFiles, FileVO fileMetaInfo);

    void insertFileInfoToDB(FileVO fileVO);


    String uploadFiles(List<MultipartFile> multipartFiles, String attflId, String mgmtType);

    void uploadNationalFlag(MultipartFile multipartFiles, NationalCodeVO nationalCodeVO);


    String registerFiles(List<MultipartFile> multipartFiles, FileVO fileMetaInfo);

    String appendFiles(List<MultipartFile> multipartFiles, FileVO fileMetaInfo);

    FileVO selectFilesByAttflId(String attflId);

    FileVO selectLogoFileByAttflId(String attflId);

    FileDetailVO selectFileNmAndPath(String attflId, Long attflSeq);

    void deleteTargetFiles(String attflId, List<Long> deleteFileList);
    void deleteAllFiles(String attflId);

    List<NationalCodeVO> selectPbanTypeCode();

    List<NationalCodeVO> selectNationalCode();

    List<ModifyHistVO> selectModifyHists(ModifyHistVO modifyHistVO);

    void registerModifyHist(ModifyHistVO modifyHistVO);

    void deleteModifyHist(ModifyHistVO build);

    List<CmmnCodeVO> selectCmmnCodeListByGroupId(String cdGrpId);
    NationalCodeVO getNationalCodeByNtnlNm(String ntnlNm);

    NationalCodeVO getContinentCodeByCnttNm(String cnttNm);

    CmmnCodeVO getBizCode(String detlCdNm);


}
