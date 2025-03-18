#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <limits.h>
#include <stdint.h>
#include <sys/time.h>
#include <stdbool.h> // 추가


#define N 1000 // 배열 크기 정의

// 소수 판별 함수
int is_prime(int num) {
    if (num < 2) return 0;
    for (int i = 2; i * i <= num; i++) {
        if (num % i == 0) return 0;
    }
    return 1;
}

// 현재 시간을 나노초 단위로 반환하는 함수
uint64_t get_nanotime() {
    struct timespec ts;
    clock_gettime(CLOCK_MONOTONIC, &ts);
    return (uint64_t)ts.tv_sec * 1000000000LL + ts.tv_nsec;
}

int main() {
    int arr[N];
    srand(time(NULL)); // 랜덤 시드 초기화

    uint64_t start, end;
    double time_ns;

    start = get_nanotime();
    // 배열에 랜덤한 숫자 채우기 (0 ~ INT_MAX)
    for (int i = 0; i < N; i++) {
        arr[i] = (int)(((long long)rand() * RAND_MAX + rand()) % (INT_MAX + 1LL));
    }
    end = get_nanotime();
    time_ns = (double)(end - start) / 1000000000.0;
    printf("Time taken to generate random numbers: %f seconds\n", time_ns);

    start = get_nanotime();
    // 배열 출력 및 소수 판별
    printf("Generated numbers and their prime status:\n");
    for (int i = 0; i < N; i++) {
	bool a = is_prime(arr[i]);
    }
    end = get_nanotime();

    time_ns = (double)(end - start) ;
    printf("%f", time_ns);

    return 0;
}


