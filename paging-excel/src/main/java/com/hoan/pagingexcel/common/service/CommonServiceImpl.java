package com.hoan.pagingexcel.common.service;


import com.hoan.pagingexcel.common.domain.*;
import com.hoan.pagingexcel.common.mapper.CommonMapper;
import com.hoan.pagingexcel.common.util.file.FileUtil;
import com.hoan.pagingexcel.exception.InvalidFileTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommonServiceImpl implements CommonService{

    private final FileUtil fileUtil;
    private final CommonMapper commonMapper;

    /**
     *  [AS-IS]
     *  attflId로 현재 null이 전달될 수 있는 상태임.
     *  attflId가 null일때, 처음으로 파일이 업로드 된다고 판단하고, 이력을 DB에도 넣고 있음. (commonMapper.insertFile)
     *
     *  [권장]
     *  generateAttflId, saveFiles 사용해서 파일 업로드 기능 구현하는거 권장
     *
     *
     * @param multipartFiles
     * @param attflId
     * @param mgmtType
     * @return
     */
    @Deprecated
    @Override
    public String uploadFiles(List<MultipartFile> multipartFiles, String attflId, String mgmtType)  {
        String attachFileId = attflId;

        if (multipartFiles == null || multipartFiles.isEmpty()) {
            return attflId;
        }

        if (StringUtils.isEmpty(attflId) || "undefined".equals(attflId)) {
            attachFileId = generateAttflId(mgmtType);
            commonMapper.insertFile(createFileVO(attachFileId, mgmtType));
        }

        for (MultipartFile file : multipartFiles) {
            FileDetailVO fileDetailVO = uploadFile(file);
            fileDetailVO.setAttflId(attachFileId);
            insertFileDetails(fileDetailVO);
        }

        return attachFileId;
    }



    /**
     * 처음으로 파일 업로드할 떄 사용하는 메서드
     * @param multipartFiles
     * @param fileMetaInfo
     * @return
     * @throws InvalidFileTypeException
     */
    @Override
    public String registerFiles(List<MultipartFile> multipartFiles, FileVO fileMetaInfo) throws InvalidFileTypeException{
        if (multipartFiles == null || multipartFiles.isEmpty()) {
            return null;
        }

        String attachFileId = generateAttflId(fileMetaInfo.getMgmtType());
        commonMapper.insertFile(createFileVO(attachFileId, fileMetaInfo.getMgmtType()));

        String[] attflIstcArray = (fileMetaInfo.getAttflIstc() != null) ? fileMetaInfo.getAttflIstc().split(",") : new String[0];
        for (int i = 0; i < multipartFiles.size(); i++) {
            FileDetailVO fileDetailVO = uploadFile(multipartFiles.get(i));

            fileDetailVO.setAttflId(attachFileId);
            fileDetailVO.setFileMgmtDetlType(fileMetaInfo.getFileMgmtDetailType().getValue());
            if (attflIstcArray.length > i && attflIstcArray[i] != null) {
                fileDetailVO.setAttflIstc(attflIstcArray[i]);
            }

            insertFileDetails(fileDetailVO);
        }

        return attachFileId;
    }



    private FileVO createFileVO(String attflId, String mgmtType) {
        return FileVO.builder().attflId(attflId).mgmtType(mgmtType).build();
    }

    private FileDetailVO uploadFile(MultipartFile file)  {
        try {
            return fileUtil.uploadFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FileDetailVO uploadNationalFlag(MultipartFile file)  {
        try {
            return fileUtil.uploadNationalFlag(file);
        } catch (IOException e) {
            log.info("e : "+e);
            return new FileDetailVO();
        }
    }

    private void insertFileDetails(FileDetailVO fileDetailVO) {
        log.info("[파일 업로드] - PATH: {}/{} SIZE: {}", fileDetailVO.getFileSavePath(), fileDetailVO.getOrtxFileNm(), fileDetailVO.getFileMg());
        commonMapper.insertFileDetail(fileDetailVO);
    }




    @Override
    public String generateAttflId (String mgmtType) {
        return commonMapper.selectNewAttflId(mgmtType);
    }


    @Override
    public FileVO selectFilesByAttflId(String attflId){
        FileVO fileVO = commonMapper.selectFileByAttflId(attflId);
        if (fileVO != null) {
            List<FileDetailVO> fileDetails = commonMapper.selectFileDetailsByAttflId(attflId);

            // 이미지 여부 확인
            fileDetails = fileDetails.stream().peek(file -> {
                Optional<String> fileTypeInfoOpt = Optional.ofNullable(file.getFileTypeInfo());
                file.setImage(fileTypeInfoOpt.map(s -> s.startsWith("image")).orElse(false));
            }).collect(Collectors.toList());

            fileVO.setFileDetailList(fileDetails);
        }
        return fileVO;
    }



    @Override
    public FileDetailVO selectFileNmAndPath(String attflId, Long attflSeq) {
        FileDetailVO fileDetailVO = new FileDetailVO();
        fileDetailVO.setAttflId(attflId);
        fileDetailVO.setAttflSeq(attflSeq);
        return commonMapper.selectFileNmAndPath(attflId, attflSeq);
    }

    /**
     * 수정 버튼 : 수정 시 삭제할 파일들이 있으면 삭제한다.
     * @param attflId
     * @param deleteFileList
     */
    @Override
    @Transactional
    public void deleteTargetFiles(String attflId, List<Long> deleteFileList) {

        if(deleteFileList != null){
            deleteFileList.forEach(attflSeq -> {
                FileDetailVO fileDetailVO = commonMapper.selectFileNmAndPath(attflId, attflSeq);
                fileUtil.deleteFiles(fileDetailVO);
                commonMapper.deleteFileDetail(attflId, attflSeq);
            });
        }

        /**
         위 연산 결과 attflId에 해당하는 데이터가 attfl_mgmt_detl에서 모두 삭제되면, attfl_mgmt에서도 데이터를 삭제한다.
         */
        if(commonMapper.selectFileDetailsByAttflId(attflId).isEmpty()){
            commonMapper.deleteFile(attflId);
        }
    }


    /**
     * 삭제 버튼 : attflId에 해당하는 파일을 파일 경로와 DB에서 삭제한다.
     * @param attflId
     */
    @Override
    @Transactional
    public void deleteAllFiles(String attflId) {
        List<FileDetailVO> fileDetailVOs = commonMapper.selectFileDetailsByAttflId(attflId);
        fileDetailVOs.forEach(fileDetailVO -> fileUtil.deleteFiles(commonMapper.selectFileNmAndPath(fileDetailVO.getAttflId(), fileDetailVO.getAttflSeq())));

        commonMapper.deleteFile(attflId);
        commonMapper.deleteAllFileDetail(attflId);
    }

    @Override
    public List<MenuVO> getAllMenuByDB() {
        return commonMapper.getAllMenuByDB();
    }


}
