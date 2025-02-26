package com.hoan.pagingexcel.common.domain;

import com.hoan.pagingexcel.common.enums.FileMgmtDetailType;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {

    private String attflId; // 첨부파일_ID
    private String mgmtType; // 관리_유형
    private String attflIstc; // 첨부파일_설명
    private String useYn; // 사용_여부
    private Timestamp rgstDttm; // 등록_일시
    private Timestamp altrDttm; // 수정_일시

    private List<FileDetailVO> fileDetailList;
    private FileMgmtDetailType fileMgmtDetailType;
    private String fileMgmtDetail;



}
