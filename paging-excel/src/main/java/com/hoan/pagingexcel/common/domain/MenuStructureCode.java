package com.hoan.pagingexcel.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@AllArgsConstructor
public enum MenuStructureCode {
    EXPORT(MenuCodeVO.builder()
            .menuIdx(0)
            .menuId("중메뉴1")
            .menuTitle("프로토타입")
            .pathPrefix("/")
            .children(Arrays.asList(
                    MenuSubCodeVO.builder()
                            .index(0)
                            .parentMenuId("중메뉴1")
                            .menuId("소메뉴1")
                            .menuTitle("소메뉴1")
                            .url("/prototype1-1")
                            .build(),
                    MenuSubCodeVO.builder()
                            .index(1)
                            .parentMenuId("중메뉴1")
                            .menuId("소메뉴2")
                            .menuTitle("소메뉴2")
                            .url("/prototype1-2")
                            .build(),
                    MenuSubCodeVO.builder()
                            .index(2)
                            .parentMenuId("중메뉴1")
                            .menuId("소메뉴3")
                            .menuTitle("소메뉴3")
                            .url("/prototype1-3")
                            .build(),
                    MenuSubCodeVO.builder()
                            .index(3)
                            .parentMenuId("중메뉴1")
                            .menuId("소메뉴4")
                            .menuTitle("소메뉴4")
                            .url("/prototype1-4")
                            .build()
            ))
            .build()),
    EVENT(MenuCodeVO.builder()
            .menuIdx(1)
            .menuId("중메뉴2")
            .menuTitle("프로토타입2")
            .pathPrefix("/")
            .children(Arrays.asList(
                    MenuSubCodeVO.builder()
                            .index(0)
                            .parentMenuId("중메뉴2")
                            .menuId("소메뉴1")
                            .menuTitle("소메뉴122")
                            .url("/prototype2-1")
                            .build(),
                    MenuSubCodeVO.builder()
                            .index(1)
                            .parentMenuId("중메뉴2")
                            .menuId("소메뉴2")
                            .menuTitle("소메뉴2")
                            .url("/prototype2-2")
                            .build(),
                    MenuSubCodeVO.builder()
                            .index(2)
                            .parentMenuId("중메뉴2")
                            .menuId("소메뉴3")
                            .menuTitle("소메뉴3")
                            .url("/prototype2-3")
                            .build(),
                    MenuSubCodeVO.builder()
                            .index(3)
                            .parentMenuId("중메뉴2")
                            .menuId("소메뉴4")
                            .menuTitle("소메뉴4")
                            .url("/prototype2-4")
                            .build(),
                    MenuSubCodeVO.builder()
                            .index(4)
                            .parentMenuId("중메뉴2")
                            .menuId("소메뉴5")
                            .menuTitle("소메뉴5")
                            .url("/prototype2-5")
                            .build()
            ))
            .build());

    private final MenuCodeVO menu;

}
