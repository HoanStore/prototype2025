package com.hoan.pagingexcel.common.domain;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileSaveVO {

    private String fileType;
    private String saveFileNm;
    private String fileSavePath;
    private BigDecimal fileMg;

}
