package com.hoan.pagingexcel.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * /////////////////////////////////////////////////////
 *
 * @CREATE BY : seunghyunlee
 * @Company : Mqnic
 * @PROJECT : prototype
 * @PACKAGE : com.mqnic.prototype.common.domain.ErrorResponse
 * @CREATE : 11:48 AM
 * @DESC :
 * /////////////////////////////////////////////////////
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int code;
    private String message;
}
