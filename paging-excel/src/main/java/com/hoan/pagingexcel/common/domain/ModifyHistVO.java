package com.hoan.pagingexcel.common.domain;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyHistVO {

    private String mgmtType;
    private long mgmtNo;
    private String altrMgmtNo;
    private String rgsrNm;
    private String rgstDttm;
    private String modfrNm;
    private String altrDttm;
}
