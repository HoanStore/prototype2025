package com.hoan.likesearchoptimizer.search.service;

import com.hoan.likesearchoptimizer.search.domain.BoardVO;
import com.hoan.likesearchoptimizer.search.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    private final SearchMapper searchMapper;

    @Override
    public List<BoardVO> findAll() {
        return searchMapper.findAll();
    }

    @Override
    public List<BoardVO> findByTitle(String title) {
        return searchMapper.findByTitle(title);
    }
}
