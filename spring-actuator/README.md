# Spring Actuator를 학습하기 위한 용도의 프로젝트입니다. 

### [기초 작업] 아래 참조함
https://positive-impactor.tistory.com/100


### Prometheus 
https://americanopeople.tistory.com/449

1. [스프링 부트] 의존성 추가
```
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

2. [스프링 부트] application.properties에 설정 추가
```
management.endpoints.web.exposure.include=health,info,metrics,prometheus
```

3. [스프링 부트] Security 설정 (Option: 시큐리티 설정이 되어 있는 경우)
```
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/prometheus").permitAll()  // 인증 없이 허용
                        .anyRequest().authenticated()
                )
                .build();
    }
}
```

4. [프로메테우스] 설치 (맥북 기준)
```
brew install prometheus
```

5. [프로메테우스] 설정변경

```
vi /opt/homebrew/etc/prometheus.yml
```

```
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: "spring-boot-apps"
    metrics_path: "/actuator/prometheus"
    static_configs:
    - targets: ["localhost:8080"]
```

6. [프로메테우스] 기동
```
prometheus --config.file=/opt/homebrew/etc/prometheus.yml
```
또는
```
brew services start prometheus
```

## [Grafana]

1. 맥에 그라파나 설치 
```
brew install grafana
```

2. 그라파나 기동
```
brew services start grafana
```

3. 데이터 소스에 프로메테우스 설정

4. 아래 템플릿 사용해서 시각화
```
https://grafana.com/grafana/dashboards/4701-jvm-micrometer/
```