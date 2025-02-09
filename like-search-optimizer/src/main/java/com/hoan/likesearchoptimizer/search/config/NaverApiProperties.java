package com.hoan.likesearchoptimizer.search.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@ConfigurationProperties("naver")
public class NaverApiProperties {

    private String url;
    private String id;
    private String secret;

}
