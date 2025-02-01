package com.hoan.pagingexcel.common.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AttachVO {
    private String attflId;
    private String mgmtType;
    private String rgstDttm;
    private String attflSeq;
    private String fileMgmtType;
    private String fileMgmtDetlType;
    private String fileSavePath;
    private String saveFileNm;
    private String ortxFileNm;
    private String fileMg;
    private String attflIstc;

}
