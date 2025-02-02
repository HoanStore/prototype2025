package com.hoan.pagingexcel.prototype.service;

import com.hoan.pagingexcel.common.domain.PageVO;

import java.util.HashMap;

public interface PrototypeService {

    HashMap<String, Object> getPrototypeList(PageVO requestPage);
}
