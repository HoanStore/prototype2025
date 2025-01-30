package com.hoan.likesearchoptimizer.search.controller;

import com.hoan.likesearchoptimizer.search.domain.BoardVO;
import com.hoan.likesearchoptimizer.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchRestController {

    private final SearchService searchService;

    @GetMapping("/findAll")
    public List<BoardVO> findAll() {
        return searchService.findAll();
    }

    @GetMapping("/findByTitle")
    public List<BoardVO> findByTitle(@RequestParam String title) {
        return searchService.findByTitle(title);
    }


}
