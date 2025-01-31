package com.hoan.likesearchoptimizer.search.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SearchResultDTO {

    private TitleSearchDTO searchDTO;
    private List<BoardVO> boardList;
}
