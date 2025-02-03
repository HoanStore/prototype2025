package com.hoan.pagingexcel.prototype.service;

import com.hoan.pagingexcel.common.domain.PageVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelUploadVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeExcelVO;
import com.hoan.pagingexcel.prototype.domain.PrototypeVO;
import com.hoan.pagingexcel.prototype.mapper.PrototypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PrototypeServiceImpl implements PrototypeService{

    private final PrototypeMapper prototypeMapper;


    @Override
    public HashMap<String, Object> getPrototypeList(PageVO requestPage) {
        HashMap<String, Object> response = new HashMap<>();
        PageVO pageVO = getpageVO(requestPage);

        pageVO.pageSetting(prototypeMapper.getPrototypeListCnt(pageVO));
        List<PrototypeVO> prototypeList = prototypeMapper.getPrototypeList(pageVO);
        response.put("page", pageVO);
        response.put("data", prototypeList);

        return response;
    }

    @Override
    public List<PrototypeExcelVO> getPrototypeListExcel() {
        return prototypeMapper.getPrototypeListExcel();
    }

    @Override
    public void registerExcel(List<PrototypeExcelUploadVO> prototypeExcelUploadVOS) {
        for (PrototypeExcelUploadVO prototypeExcelVO : prototypeExcelUploadVOS) {
            prototypeMapper.registerPrototypeData(prototypeExcelVO);
        }
    }

    public PageVO getpageVO(PageVO requestPage){

        PageVO pageVO = PageVO.builder()
                .pageSize(requestPage.getPageSize())
                .rangeSize(requestPage.getRangeSize())
                .curPage(requestPage.getCurPage())
                .searchType(requestPage.getSearchType())
                .lang(requestPage.getLang())
                .keyword(requestPage.getKeyword())
                .build();

        return pageVO;
    }
}
