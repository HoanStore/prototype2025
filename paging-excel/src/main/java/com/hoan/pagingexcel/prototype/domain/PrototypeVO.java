package com.hoan.pagingexcel.prototype.domain;

import com.hoan.pagingexcel.common.domain.FileVO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PrototypeVO {

    private String id;
    private String title;
    private String content;
    private String createdAt;
    private String attflId;
    private String attflIstc;

    private List<MultipartFile> fileLists;
    private List<Long> deleteFileList;
    private FileVO fileVO;
}
