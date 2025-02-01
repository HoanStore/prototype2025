package com.hoan.pagingexcel.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * /////////////////////////////////////////////////////
 * <p>
 * #CREATED BY : seunghyunlee
 * #Company : Mqnic
 * #PROJECT : ntic_admin_new
 * #CREATE : 10:45 AM
 * #DESC :
 * /////////////////////////////////////////////////////
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuCodeVO {
    private int menuIdx;
    private String menuId;
    private String menuTitle;
    private String pathPrefix;
    private List<MenuSubCodeVO> children = Collections.emptyList();
}
