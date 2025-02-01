package com.hoan.pagingexcel.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * /////////////////////////////////////////////////////
 * <p>
 * #CREATED BY : seunghyunlee
 * #Company : Mqnic
 * #PROJECT : ntic_admin_new
 * #PACKAGE : com.mqnic.admin.common.model.CommonSideMenu
 * #CREATE : 3:15 PM
 * #DESC :
 * /////////////////////////////////////////////////////
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuSubCodeVO {
    int index;
    String parentMenuId;
    String menuId;
    String menuTitle;
    String url;
}
