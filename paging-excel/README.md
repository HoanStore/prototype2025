# 페이징 기능과 엑셀 다운로드, 엑셀 업로드 기능의 프로토타입입니다. 


1. 페이징과 엑셀 다운로드
2. 이미지 업로드 및 다운로드
3. 파일 업로드 및 다운로드
4. 삭제(삭제 시 파일도 모두 삭제) 
5. 수정 => 실제 수정 기능 동작하는 작업 진행해야 함. 
6. 리팩토링 => 파일 업로드 및 다운로드 부분 리팩토링 필요

# http/2 적용 (application.properties)
```
server.http2.enabled=true
```

# 셀프 서명한 TLS 적용

1. localhost-key.pem(개인 키), localhost-cert.pem(인증 서) 생성
```
openssl req -newkey rsa:2048 -nodes -keyout localhost-key.pem \
    -x509 -days 365 -out localhost-cert.pem
```
2. .p12 파일로 변환
```
openssl pkcs12 -export -in localhost-cert.pem -inkey localhost-key.pem \
    -out keystore.p12 -name tomcat -password pass:changeit
```

3. keystore.p12 파일을 resources 아래에 두기
4. 설정 변경 (application.properties)
```
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-type=PKCS12
server.ssl.key-store-password=changeit
```


# DB로 메뉴 구성
1. DB를 통해 메뉴를 구성하는 메소드를 추가하였음


# 즐겨찾기 기능 (향후 추가 예정)
1. 즐겨찾기 기능 추가 예정











