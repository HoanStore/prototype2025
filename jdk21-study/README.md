# jdk21의 주요 기능을 학습합니다.



# FMM (Native 코드를 사용)

- 최적화 옵션 O3을 주었음
```
gcc -shared -o libprime.dylib -fPIC -O3 -march=native -flto prime.c
```