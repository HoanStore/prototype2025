package com.hoan.likesearchoptimizer.search.service;


import com.hoan.likesearchoptimizer.search.domain.BoardVO;

import java.util.List;

public interface SearchService {
    List<BoardVO> findAll();

    List<BoardVO> findByTitle(String title);
}
