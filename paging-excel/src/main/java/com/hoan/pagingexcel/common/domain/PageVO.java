package com.hoan.pagingexcel.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * /////////////////////////////////////////////////////
 *
 * @CREATE BY : seunghyun
 * @Company : Mqnic
 * @PROJECT : MolitIntergrationSystem
 * @CREATE : 2020/09/03 1:08 오후
 * @DESC :
 * /////////////////////////////////////////////////////
 */
@Data
@Builder
@Alias("PageVO")
@NoArgsConstructor
@AllArgsConstructor
public class PageVO {
    /**
     * 한 페이지당 게시글 수
     **/
    private int pageSize = 10;

    /**
     * 한 블럭(range)당 페이지 수
     **/
    private int rangeSize = 10;

    /**
     * 현재 페이지
     **/
    private int curPage = 1;

    /**
     * 현재 블럭(range)
     **/
    private int curRange = 0;

    /**
     * 총 게시글 수
     **/
    private int listCnt = 0;

    /**
     * 총 페이지 수
     **/
    private int pageCnt = 0;

    /**
     * 총 블럭(range) 수
     **/
    private int rangeCnt = 0;

    /**
     * 총 게시물의 시작 페이지
     */
    private int firstPage = 1;

    /**
     * 총 게시물의 끝 페이지
     */
    private int lastPage = 1;

    /**
     * 시작 페이지
     **/
    private int startPage = 1;

    /**
     * 끝 페이지
     **/
    private int endPage = 1;

    /**
     * 시작 index
     **/
    private int startIndex = 0;

    /**
     * 이전 페이지
     **/
    private int prevPage = 0;

    /**
     * 다음 페이지
     **/
    private int nextPage = 0;

    private List<Integer> pagesList;


    // 검색처리를 위해 추가.
    private String bno = "0";
    private String searchType = "";
    private String lang = "kr";
    private int langType = 0;
    private String keyword = "";
    private int seqNo;
    private String mgmtType;
    private String keywordEname;
    private String keywordNation;
    private String keywordYear;
    private String keywordCompany;

    private String startDate = "";
    private String endDate = "";

    public void pageSetting(int listCnt) {
        /**
         * 페이징 처리 순서
         * 1. 총 페이지수
         * 2. 총 블럭(range)수
         * 3. range setting
         */
        // 총 게시물 수와 현재 페이지를 Controller로 부터 받아온다.

        /** 총 게시물 수 **/
        setListCnt(listCnt);

        /** 1. 총 페이지 수 **/
        setPageCnt(listCnt);
        /** 2. 총 블럭(range)수 **/
        setRangeCnt(pageCnt);
        /** 3. 블럭(range) setting **/
        rangeSetting(curPage);

        /** DB 질의를 위한 startIndex 설정 **/
        setStartIndex(curPage);

        setPageList();
    }

    public void setPageCnt(int listCnt) {
        this.pageCnt = (int) Math.ceil(listCnt * 1.0 / pageSize);
    }

    public void setRangeCnt(int pageCnt) {
        this.rangeCnt = (int) Math.ceil(pageCnt * 1.0 / rangeSize);
    }

    public void rangeSetting(int curPage) {

        setCurRange(curPage);

        this.firstPage = 1;
        this.lastPage = this.pageCnt;

        this.startPage = (curRange - 1) * rangeSize + 1;
        this.endPage = startPage + rangeSize - 1;


        if (endPage > pageCnt) {
            this.endPage = pageCnt;
        }

        this.prevPage = (curPage - 1) <= 0 ? 1 : curPage - 1;
        this.nextPage = curPage + 1;
        if (listCnt < (int) Math.ceil(nextPage * 1.0 * pageSize)) {
            this.nextPage = pageCnt;
        }
    }

    public void setCurRange(int curPage) {
        this.curRange = (int) ((curPage - 1) / rangeSize) + 1;
    }

    public void setStartIndex(int curPage) {
        this.startIndex = (curPage - 1) * pageSize;
    }

    public void setPageList() {
        this.pagesList = new ArrayList<>();
        IntStream.range(this.startPage, (this.startPage + this.rangeSize)).forEach(i -> {
            if (i <= this.lastPage) {
                this.pagesList.add(i);
            }
        });
    }
}
