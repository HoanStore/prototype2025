package com.hoan.likesearchoptimizer.search.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TitleSearchDTO {
    private String originalTitle;
    private String correctedTitle;
}
