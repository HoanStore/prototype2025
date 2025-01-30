package com.hoan.likesearchoptimizer.search.controller;

import com.hoan.likesearchoptimizer.search.domain.BoardVO;
import com.hoan.likesearchoptimizer.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class SearchController {

    private final SearchService searchService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {

        List<BoardVO> boardList = searchService.findAll();
        model.addAttribute("boardList", boardList);

        return "home";
    }
}
