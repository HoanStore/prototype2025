package com.hoan.likesearchoptimizer.search.util;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
public class NaverApiService {

    private static final String CLIENT_ID = "OZYjoBCzXlk2nkK9nyVs";
    private static final String CLIENT_SECRET = "xwHBCEPXLZ";
    private static final String API_URL = "https://openapi.naver.com/v1/search/webkr.json?query=%EB%95%B0%EA%B8%B0&display=10&start=1";

    public static void main(String[] args) {

        String clientId = "OZYjoBCzXlk2nkK9nyVs";
        String clientSecret = "xwHBCEPXLZ";


        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/webkr.json")
                .queryParam("query", "주식")
                .queryParam("display", 10)
                .queryParam("start", 1)
                .encode()
                .build()
                .toUri();

        // Spring 요청 제공 클래스
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();
        // Spring 제공 restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        String body = resp.getBody();
        System.out.println("body = " + body);
    }


}
