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



CREATE TABLE menu (
                          menu_id INT PRIMARY KEY AUTO_INCREMENT,
                          menu_name VARCHAR(255) NOT NULL,  -- 메뉴 이름
                          url VARCHAR(255),                -- 메뉴 URL
                          parent_id INT,                   -- 부모 메뉴 ID (대분류/중분류/소분류 관계)
                          level INT,                       -- 메뉴 레벨 (대분류=1, 중분류=2, 소분류=3)
                          is_used BOOLEAN,                 -- 메뉴 사용 여부
                          FOREIGN KEY (parent_id) REFERENCES menu(menu_id) ON DELETE CASCADE  -- 부모-자식 관계 설정 // -- 외래 키 제약이 존재하지만, 대분류 메뉴는 `parent_id`가 NULL이므로 예외가 발생하지 않음
);


CREATE TABLE weather_info (
                              region_code CHAR(2) NOT NULL,
                              weather_code CHAR(2) NOT NULL,
                              recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              PRIMARY KEY (region_code, recorded_at)
);


CREATE TABLE favorite_menu (
                               menu_id VARCHAR(255) NOT NULL,
                               user_id VARCHAR(255) NOT NULL,
                               order_index VARCHAR(10) NOT NULL,  -- 메뉴 순서 추가
                               PRIMARY KEY (menu_id, user_id)  -- 복합키 설정
);

