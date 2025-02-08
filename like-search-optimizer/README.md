
## 사용 URL
http://localhost:8080

## 인증키 (네이버 검색 API)
OZyjoBCzXlk2nkK9nyVs 


## 아래 API를 이용해야 함.

- X-Naver-Client-Id : OZyjoBCzXlk2nkK9nyVs
- X-Naver-Client-Secret : xwHBCEPXLZ

https://openapi.naver.com/v1/search/webkr.json?query=%EB%95%B0%EA%B8%B0&display=10&start=1


## Vault 사용

1. 도커로 실행 (brew로 설치 안 됨)
```
docker run --cap-add=IPC_LOCK -d --name=dev-vault -p 8200:8200 hashicorp/vault:1.15.3 server -dev
```

2. 루트 토큰 확인 
```
docker logs dev-vault | grep "Root Token"
```

3. 접속 확인
```
http://127.0.0.1:8200
```

4. 값 추가

```
export VAULT_ADDR='http://127.0.0.1:8200'
export VAULT_TOKEN=[TOKEN 값]

vault kv put secret/[APPLICATION_NAME] [KEY]=[VALUE]

vault kv get secret/APPLICATION_NAME
```

5. Spring 설정 추가