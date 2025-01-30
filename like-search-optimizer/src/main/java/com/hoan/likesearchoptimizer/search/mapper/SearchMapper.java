package com.hoan.likesearchoptimizer.search.mapper;

import com.hoan.likesearchoptimizer.search.domain.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchMapper {

    List<BoardVO> findAll();

    List<BoardVO> findByTitle(String title);
}
