package com.hoan.pagingexcel.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuVO {
    private int menuId;        // 메뉴 ID
    private String menuName;   // 메뉴 이름
    private String url;        // 메뉴 URL
    private String fullUrl;        // 전체 URL
    private Integer parentId;  // 부모 메뉴 ID (NULL 가능)
    private int level;         // 메뉴 레벨 (대분류=1, 중분류=2, 소분류=3)
    private boolean isUsed;    // 메뉴 사용 여부

    private String orderIndex;
}
