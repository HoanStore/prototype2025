package com.hoan.pagingexcel.common.domain;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDetailVO {

    private String attflId; // 첨부파일_ID
    private long attflSeq; // 첨부파일_일련번호
    private String fileMgmtType; // 파일_관리_유형(확장자)
    private String fileMgmtDetlType; // 파일_관리_상세_유형
    private String fileSavePath; // 파일_저장_경로
    private String saveFileNm; // 저장_파일_명
    private String ortxFileNm; // 원본_파일_명
    private BigDecimal fileMg; // 파일_크기
    private String fileTypeInfo; // 파일_유형_정보
    private String attflIstc; // 첨부파일_설명
    private String useYn; // 파일_사용_여부
    private Timestamp rgstDttm; // 등록_날짜
    private Timestamp altrDttm; // 수정_날짜
    private boolean isImage;
}
