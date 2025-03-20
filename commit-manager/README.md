# 커밋 매니저

## 주요 기능
1. 커밋을 모아서 특정 시점에 보낼 수 있는 기능

```
[작업흐름]
1. commit을 patch 파일로 모아 둡니다. 
2. 실제로 이 patch 파일이 저희 서비스에 올라갑니다. 
3. 저희 서비스에 올라온 patch 파일을 바탕으로 매일 한 개씩 커밋이 이루어집니다. 
```

```
git add .
git commit -m "test: README.md"
git format-patch -1 -o patches/
git apply patches/0001-*.patch
git commit -m "예약된 커밋"
git push origin main
```


test1

