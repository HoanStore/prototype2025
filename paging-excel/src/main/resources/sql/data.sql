INSERT INTO board (title, content) VALUES ('한국', '첫 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('햔국', '두 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('중국', '세 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('일본', '네 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('딸기', '다섯 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('오렌지', '여섯 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('오롄지', '일곱 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('한국', '첫 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('햔국', '두 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('중국', '세 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('일본', '네 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('딸기', '다섯 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('오렌지', '여섯 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('오롄지', '일곱 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('한국', '첫 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('햔국', '두 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('중국', '세 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('일본', '네 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('딸기', '다섯 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('오렌지', '여섯 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('오롄지', '일곱 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('한국', '첫 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('햔국', '두 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('중국', '세 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('일본', '네 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('딸기', '다섯 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('오렌지', '여섯 번째 게시글 내용');
INSERT INTO board (title, content) VALUES ('오롄지', '일곱 번째 게시글 내용');



-- 대분류 메뉴 삽입
INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES (1000, '제어망 조회', '/control-network', NULL, 1, TRUE),
       (2000, '통계 분석', '/statistics', NULL, 1, TRUE),
       (3000, '관리자', '/admin', NULL, 1, TRUE);

-- 제어망 조회 중분류 메뉴 삽입
INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (1100, '운영현황', '/operation-status', 1000, 2, TRUE),
    (1200, '운영이력', '/operation-hist', 1000, 2, TRUE),
    (1300, '도로상황모니터링', '/traffic-monitoring', 1000, 2, TRUE),
    (1400, '관리자', '/admin', 1000, 2, TRUE),
    (1500, '수용정보 등록', '/registration', 1000, 2, TRUE)
;

-- 통계 분석 중분류 메뉴 삽입
INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (2100, '장비 수집 정보', '/equipment-collection', 2000, 2, TRUE),
    (2200, '장비 운영 정보', '/equipment-operation', 2000, 2, TRUE),
    (2300, '장비 제어', '/equipment-control', 2000, 2, TRUE),
    (2400, '장비 교통 정보', '/equipment-traffic', 2000, 2, TRUE),
    (2500, '보고서', '/report', 2000, 2, TRUE),
    (2600, '모니터링', '/monitoring', 2000, 2, TRUE);


-- 관리자 중분류 메뉴 삽입
INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (3100, '관리자 페이지', '/', 3000, 2, TRUE)
;


INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (1110, '돌발상황 자동감지', '/vds', 1100, 3, TRUE),
    (1120, '문자정보조회', '/dsrc', 1100, 3, TRUE),
    (1130, 'VMS 현황', '/avc', 1100, 3, TRUE),
    (1140, 'DSRC 운영', '/vms', 1100, 3, TRUE),
    (1150, '연계 실시간 모니터링', '/detail', 1100, 3, TRUE),
    (1160, 'VSL 결빙취약구간 운영관리대장', '/detail', 1100, 3, TRUE),
    (1170, '웹캠 모니터링', '/detail', 1100, 3, TRUE),
    (1180, '서버현황', '/detail', 1100, 3, TRUE),
    (1190, 'VDS', '/detail', 1100, 3, TRUE),
    (1190-1, '긴급전화', '/detail', 1100, 3, TRUE)
;

INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (1210, '문자정보처리이력 조회', '/vds', 1200, 3, TRUE),
    (1220, 'VMS이력', '/dsrc', 1200, 3, TRUE),
    (1230, 'LCS이력', '/avc', 1200, 3, TRUE),
    (1240, 'VSL이력', '/vms', 1200, 3, TRUE),
    (1250, '연계정보 조회', '/detail', 1200, 3, TRUE),
    (1260, '전면차단 감지이력', '/detail', 1200, 3, TRUE),
    (1270, 'SMS발송이력 조회', '/detail', 1200, 3, TRUE),
    (1280, '기상 모니터링 이력 조회', '/detail', 1200, 3, TRUE),
    (1290, '어드바이저 이력', '/detail', 1200, 3, TRUE)
;


INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (1310, '인포그래픽 전자지도', '/vds', 1300, 3, TRUE),
    (1320, '터널정보(계통도)', '/dsrc', 1300, 3, TRUE)
;


INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (1410, '운영정보관리', '/vds', 1400, 3, TRUE),
    (1420, '자동돌발감지', '/dsrc', 1400, 3, TRUE),
    (1430, '안전주행지원콘텐츠', '/avc', 1400, 3, TRUE)
;

INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (1510, '본선장비 등록', '/vds', 1500, 3, TRUE),
    (1520, '터널장비 등록', '/dsrc', 1500, 3, TRUE)
;


-- 소분류 메뉴 삽입
-- [장비 수집 정보]
INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (2110, 'VDS 정보', '/vds', 2100, 3, TRUE),
    (2120, 'DSRC 정보', '/dsrc', 2100, 3, TRUE),
    (2130, 'AVC 정보', '/avc', 2100, 3, TRUE),
    (2140, 'VMS 정보', '/vms', 2100, 3, TRUE),
    (2150, '상세 분석', '/detail', 2100, 3, TRUE)
;


-- [장비 운영 정보]
INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (2210, '총괄정보', '/total', 2200, 3, TRUE),
    (2220, '설비가동률통계', '/equipment-availability', 2200, 3, TRUE),
    (2230, '설비상태현황', '/equipment-status', 2200, 3, TRUE),
    (2240, '통신 가동률 통계', '/communication-availability', 2200, 3, TRUE),
    (2250, '데이터 가동률 통계', '/data-availability', 2200, 3, TRUE),
    (2260, '상태항목 통계', '/status-category', 2200, 3, TRUE)
;

-- [장비 제어]
INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (2310, '장비제어이력', '/hist', 2300, 3, TRUE)
;

-- [장비 교통 정보]
INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (2410, '교통분석', '/hist', 2400, 3, TRUE),
    (2420, '돌발정보', '/hist', 2400, 3, TRUE),
    (2430, '사고감지', '/hist', 2400, 3, TRUE)
;


-- [보고서]
INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (2510, '교통정보', '/hist', 2500, 3, TRUE),
    (2520, '교통현황 상황보고', '/hist', 2500, 3, TRUE),
    (2530, 'VMS 운영', '/hist', 2500, 3, TRUE),
    (2540, 'VMS 정보', '/hist', 2500, 3, TRUE)
;

-- [모니터링]
INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (2610, 'VDS 모니터링', '/hist', 2600, 3, TRUE),
    (2620, 'AVC 모니터링', '/hist', 2600, 3, TRUE),
    (2630, 'DSRC 이상장비 품질지표 분석', '/hist', 2600, 3, TRUE),
    (2640, 'SMS 발송이력 조회', '/hist', 2600, 3, TRUE),
    (2650, 'exTMS 시스템 모니터링', '/hist', 2600, 3, TRUE),
    (2660, '서버 설정 관리', '/hist', 2600, 3, TRUE)
;


-- [관리자 소분류]
INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (3110, '사용자 관리', '/user', 3100, 3, TRUE),
    (3120, '이력 조회', '/statistics', 3100, 3, TRUE),
    (3130, '권한 관리', '/authority', 3100, 3, TRUE),
    (3140, '메인포탈 설정', '/setting', 3100, 3, TRUE)
;



--  제어망 / 운영현황 / 돌발상황 자동감지
INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (1111, '돌발상황 운영현황', '/control-network/operation-status', 1110, 4, TRUE),
    (1112, '돌발상황 정보분석', '/control-network/operation-status/getEmergencyAnalysis', 1110, 4, TRUE),
    (1113, '돌발상황자동감지 통계정보', '/control-network/operation-status/getEmergencyAutoDetectionInfo', 1110, 4, TRUE)
;
-- 1210
-- 1310

INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (1211, '문자정보처리이력 조회', '/control-network/operation-hist', 1210, 4, TRUE)
;

INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (1311, '인포그래픽 전자지도', '/control-network/traffic-monitoring', 1310, 4, TRUE)
;

INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (1411, '표준노드링크 조회', '/control-network/admin', 1410, 4, TRUE),
    (1412, '현장설비설치현황 조회', '/control-network/admin/getEquipmentInstallStatus', 1410, 4, TRUE)
;


INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (1511, 'AVC 수용 요청', '/control-network/registration', 1510, 4, TRUE)
;

-- 소메뉴 아래 - 실제 메뉴
-- [VDS]
INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (2111, '원시 데이터', '/statistics/equipment-collection', 2110, 4, TRUE),
    (2112, '시간대별 소통정보', '/traffic-info-by-time', 2110, 4, TRUE),
    (2113, '일/주/월별 소통정보', '/traffic-info-by-day-week-month', 2110, 4, TRUE),
    (2114, '램프 교통량 정보', '/ramp-traffic-info', 2110, 4, TRUE),
    (2115, 'VDS 집중관리', '/vds-focus-management', 2110, 4, TRUE)
;

INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (2211, '총괄정보', '/statistics/equipment-operation', 2210, 4, TRUE),
    (2221, '설비가동률통계', '/test', 2220, 4, TRUE),
    (2231, '설비상태현황', '/test', 2230, 4, TRUE),
    (2241, '통신가동이력(지사별)', '/branch', 2240, 4, TRUE),
    (2242, '통신가동이력(노선별)', '/line', 2240, 4, TRUE),
    (2243, '통신가동이력(시설물별)', '/facility', 2240, 4, TRUE)
;


INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (2311, 'VMS', '/statistics/equipment-control', 2310, 4, TRUE),
    (2312, 'LCS', '/test', 2310, 4, TRUE),
    (2313, '비상방송', '/test', 2310, 4, TRUE),
    (2314, '긴급전화', '/branch', 2310, 4, TRUE),
    (2315, '원클릭', '/line', 2310, 4, TRUE),
    (2316, '터널 프로세스', '/facility', 2310, 4, TRUE),
    (2317, '진입차단표지판', '/facility', 2310, 4, TRUE)
;


INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (2411, '국도소통정보', '/statistics/equipment-traffic', 2410, 4, TRUE),
    (2412, '콘존단위 정체변환 전망', '/test', 2410, 4, TRUE),
    (2413, '원시데이터 검증', '/test', 2410, 4, TRUE)
;


INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (2511, '유고상황 처리일지', '/statistics/report', 2510, 4, TRUE),
    (2512, '각길 가변차로제 운영일지', '/test', 2510, 4, TRUE),
    (2513, '문자정보 이력조회(건별)', '/test', 2510, 4, TRUE)
;

INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (2611, 'VDS 이상장비 조회', '/statistics/monitoring', 2610, 4, TRUE),
    (2612, 'VDS 품질지표 분석', '/test', 2610, 4, TRUE),
    (2613, 'VDS 이상장비 교통량 분석', '/test', 2610, 4, TRUE)
;


INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (3111, '사용자 조회', '/user/user-search', 3110, 4, TRUE)
;


INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (3121, '접속 로그관리', '/statistics/access-log', 3120, 4, TRUE),
    (3122, '사용자별 접속 통계', '/statistics/user', 3120, 4, TRUE),
    (3123, '메뉴별 이용현황 통계', '/statistics/menu', 3120, 4, TRUE)
;



INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (3131, '그룹별 관리', '/authority/group-management', 3130, 4, TRUE)
;

INSERT INTO menu (menu_id, menu_name, url, parent_id, level, is_used)
VALUES
    (3141, '표출 제어', '/setting/display-control', 3140, 4, TRUE)
;



INSERT INTO weather_info (region_code, weather_code) VALUES ('11', 'SU'); -- 서울 맑음
INSERT INTO weather_info (region_code, weather_code) VALUES ('26', 'RN'); -- 부산 비

INSERT INTO favorite_menu (menu_id, user_id, order_index)
VALUES ('1111', 'admin', '1'),
       ('1112', 'admin', '2'),
       ('1003', 'test', '1');