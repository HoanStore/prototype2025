package com.hoan.pagingexcel.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NationalCodeVO {

    private String cnttCd;
    private String ntnlCd;
    private String cnttNm;
    private String cnttEnglNm;
    private String ntnlNm;
    private String ntnlEnglNm;
    private String ntnlUrl;
}
