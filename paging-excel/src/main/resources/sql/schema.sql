CREATE TABLE board (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 게시글 ID (자동 증가)
                       title VARCHAR(255) NOT NULL,          -- 게시글 제목
                       content TEXT NOT NULL,                -- 게시글 내용
                       attflId VARCHAR(255),           -- 게시글 파일 ID
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 작성 시간 (기본값: 현재 시간)
);



CREATE TABLE attfl_mgmt (
                            attfl_id VARCHAR(50) PRIMARY KEY,     -- 파일 ID, 기본키로 설정
                            mgmt_type VARCHAR(50),        -- 관리 유형
                            use_yn CHAR(1),               -- 사용 여부 ('Y' 또는 'N')
                            rgst_dttm TIMESTAMP           -- 등록 일시
);

CREATE TABLE attfl_mgmt_detl (
                                 attfl_id VARCHAR(50),                  -- 파일 ID (참조)
                                 attfl_seq INT PRIMARY KEY,      -- 파일 시퀀스, 기본키
                                 file_mgmt_type VARCHAR(50),     -- 파일 관리 유형
                                 file_mgmt_detl_type VARCHAR(50), -- 파일 관리 세부 유형
                                 file_save_path VARCHAR(255),    -- 파일 저장 경로
                                 save_file_nm VARCHAR(255),     -- 저장된 파일 이름
                                 ortx_file_nm VARCHAR(255),     -- 원본 파일 이름
                                 file_mg VARCHAR(50),           -- 파일 관리 정보
                                 file_type_info VARCHAR(100),   -- 파일 유형 정보
                                 attfl_istc VARCHAR(50),        -- 파일 관련 기타 정보
                                 use_yn CHAR(1),                -- 사용 여부 ('Y' 또는 'N')
                                 rgst_dttm TIMESTAMP,           -- 등록 일시
                                 FOREIGN KEY (attfl_id) REFERENCES attfl_mgmt(attfl_id) -- 부모 테이블 참조
);




