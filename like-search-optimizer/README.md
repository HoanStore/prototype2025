
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
docker run --cap-add=IPC_LOCK -d \
  --name vault-dev \
  -e VAULT_DEV_ROOT_TOKEN_ID=root \
  -e VAULT_ADDR="http://0.0.0.0:8200" \
  -p 8200:8200 \
  hashicorp/vault:latest server -dev
```

2. 루트 토큰 확인 
```
docker logs vault-dev | grep "Root Token"
```

3. 접속 확인
```
http://127.0.0.1:8200
```

4. 선수 작업
```
export VAULT_ADDR='http://127.0.0.1:8200'
export VAULT_TOKEN=[TOKEN 값]
```

5. 값 추가

```
vault kv put secret/like-search-optimizer naver.url=https://openapi.naver.com/v1/search/errata.json?query= naver.id=OZyjoBCzXlk2nkK9nyVs naver.secret=xwHBCEPXLZ
```

6. 값 확인
```
vault kv get secret/like-search-optimizer
```

7. Spring 설정 추가
```
spring.cloud.vault.enabled=true

spring.cloud.vault.token=root
spring.cloud.vault.scheme=http
spring.cloud.vault.kv.enabled=true
spring.config.import:  vault://
```
