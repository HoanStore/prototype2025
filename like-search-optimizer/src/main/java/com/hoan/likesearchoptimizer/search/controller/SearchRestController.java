package com.hoan.likesearchoptimizer.search.controller;

import com.hoan.likesearchoptimizer.search.domain.BoardVO;
import com.hoan.likesearchoptimizer.search.domain.SearchResultDTO;
import com.hoan.likesearchoptimizer.search.domain.TitleSearchDTO;
import com.hoan.likesearchoptimizer.search.service.SearchService;
import com.hoan.likesearchoptimizer.search.util.NaverApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchRestController {

    private final SearchService searchService;
    private final NaverApiService naverApiService;

    @GetMapping("/findAll")
    public List<BoardVO> findAll() {
        return searchService.findAll();
    }

    @GetMapping("/findByTitle")
    public SearchResultDTO findByTitle(@RequestParam String title) {
        String correctedTitle = naverApiService.getCorrectSpelling(title);
        TitleSearchDTO titleSearchDTO = TitleSearchDTO.builder().originalTitle(title).correctedTitle(correctedTitle).build();

        if(!correctedTitle.isBlank()) {
            return SearchResultDTO.builder().searchDTO(titleSearchDTO).boardList(searchService.findByTitle(correctedTitle)).build();
        }
        return SearchResultDTO.builder().searchDTO(titleSearchDTO).boardList(searchService.findByTitle(title)).build();
    }

    @GetMapping("/findStrict")
    public List<BoardVO> findStrict(@RequestParam String title) {
        return searchService.findByTitle(title);
    }
}
