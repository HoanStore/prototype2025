package com.hoan.pagingexcel.prototype.service;

import com.hoan.pagingexcel.common.domain.FileVO;
import com.hoan.pagingexcel.common.domain.PageVO;
import com.hoan.pagingexcel.common.enums.MgmtTypes;
import com.hoan.pagingexcel.common.service.CommonService;
import com.hoan.pagingexcel.common.util.file.FileUtil;
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
    private final CommonService commonService;
    private final FileUtil fileUtil;


    @Override
    public PrototypeVO getPrototype(PageVO pageVO) {

        PrototypeVO prototypeVO = prototypeMapper.getPrototype(pageVO);

        if(prototypeVO.getAttflId() != null){
            FileVO fileVO = commonService.selectFilesByAttflId(prototypeVO.getAttflId());
            prototypeVO.setFileVO(fileVO);
        }


        return prototypeVO;
    }

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

    @Override
    public void registerPrototype(PrototypeVO prototypeVO) {
        String attflId = commonService.registerFiles(prototypeVO.getFileLists(), FileVO.builder().mgmtType(MgmtTypes.EXAMPLES.getValue())
                .attflIstc(prototypeVO.getAttflIstc())
                .build());

        prototypeVO.setAttflId(attflId);

        prototypeMapper.registerPrototype(prototypeVO);
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
