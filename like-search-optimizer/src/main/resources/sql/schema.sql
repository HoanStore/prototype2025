CREATE TABLE board (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 게시글 ID (자동 증가)
                       title VARCHAR(255) NOT NULL,          -- 게시글 제목
                       content TEXT NOT NULL,                -- 게시글 내용
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 작성 시간 (기본값: 현재 시간)
);


