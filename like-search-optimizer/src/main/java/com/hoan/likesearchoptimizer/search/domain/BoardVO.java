package com.hoan.likesearchoptimizer.search.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BoardVO {

    private Long id;           // 게시글 ID
    private String title;      // 게시글 제목
    private String content;    // 게시글 내용
    private Timestamp createdAt;  // 작성 시간
}
