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

    String uploadFiles(List<MultipartFile> multipartFiles, String attflId, String mgmtType);

    String registerFiles(List<MultipartFile> multipartFiles, FileVO fileMetaInfo);

    FileVO selectFilesByAttflId(String attflId);

    FileDetailVO selectFileNmAndPath(String attflId, Long attflSeq);

    void deleteTargetFiles(String attflId, List<Long> deleteFileList);

    void deleteAllFiles(String attflId);

    List<Object> getAllMenuByEnum();

    List<MenuVO> getAllMenuByDB();


}
