package com.hoan.likesearchoptimizer.util;

import com.hoan.likesearchoptimizer.search.util.NaverApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NaverApiServiceTest {

    @Autowired
    private NaverApiService naverApiService;

    @Test
    public void correctWordApiTest() {
        String targetWord = "햔국";
        String correctWord = "한국";

        String apiReturnWord = naverApiService.getCorrectSpelling(targetWord);

        assertEquals(correctWord, apiReturnWord);
    }

}
