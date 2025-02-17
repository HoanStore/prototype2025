package com.hoan.pagingexcel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * /////////////////////////////////////////////////////
 *
 * @CREATE BY : seunghyun
 * @Company : Mqnic
 * @PROJECT : MolitIntergrationSystem
 * @PACKAGE : com.mqnic.molit.util
 * @CREATE : 2020/06/04 1:41 오후
 * @DESC :
 * /////////////////////////////////////////////////////
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

}
