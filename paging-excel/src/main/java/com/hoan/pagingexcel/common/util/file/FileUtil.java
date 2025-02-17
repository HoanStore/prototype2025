package com.hoan.pagingexcel.common.util.file;

import com.hoan.pagingexcel.common.domain.FileDetailVO;
import com.hoan.pagingexcel.common.domain.FileSaveVO;
import com.hoan.pagingexcel.exception.InvalidFileTypeException;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileUtil {

    @Value("${file.uploadPath}")
    private String defaultUploadPath;

    @Value("${file.uploadFlagPath}")
    private String uploadFlagPath;

    private final List<String> allowedMimeTypes = Arrays.asList(
            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",  //excel
            "application/vnd.ms-word", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", //word
            "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", //ppt
            "application/x-tika-ooxml", // .xlsx, .pptx, .docx
            "application/pdf", //pdf
            "application/x-hwp", "application/haansofthwp", "application/vnd.hancom", //hwp
            "image", //image
            "application/octet-stream", // nsm
            "text/plain",
            "video",
            "application/zip",
            "application/x-zip-compressed" // additional zip MIME type
            );

    public ResponseEntity<Resource> getFile(FileDetailVO fileDetailVO, String isThumbnail) throws IOException {
        String saveFileNm = isThumbnail.equals("Y") ? "s_" + fileDetailVO.getSaveFileNm() : fileDetailVO.getSaveFileNm();
        String filePath = fileDetailVO.getFileSavePath() + "/" + saveFileNm;
        Resource resource = new FileSystemResource(filePath);

        if(!resource.isReadable()){
            throw new NoSuchFileException("File does not exist");
        }

        HttpHeaders headers = new HttpHeaders();
        try{
            Tika tika = new Tika();
            String mimeType = tika.detect(resource.getInputStream()); // 파일의 MIME 타입을 감지
            // 파일 이름을 URL 인코딩하여 공백을 '%20'으로 대체
            String encodedFileName = URLEncoder.encode(fileDetailVO.getOrtxFileNm(), StandardCharsets.UTF_8).replace("+", "%20");

            headers.add("Content-Type", mimeType);
            headers.add("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }


    public ResponseEntity<Resource> getFile(String filePath) throws IOException {
        Resource resource = new FileSystemResource(filePath);

        if(!resource.isReadable()){
            throw new NoSuchFileException("File does not exist");
        }

        HttpHeaders headers = new HttpHeaders();
        try{
            Tika tika = new Tika();
            String mimeType = tika.detect(resource.getInputStream()); // 파일의 MIME 타입을 감지
            // 파일 이름을 URL 인코딩하여 공백을 '%20'으로 대체
            String encodedFileName = URLEncoder.encode(filePath, StandardCharsets.UTF_8).replace("+", "%20");

            headers.add("Content-Type", mimeType);
            headers.add("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }


    /**
     * 허용된 MIME 타입인지 검사합니다.
     *
     * @param file
     * @return
     * @throws IOException
     */
    private boolean isValidFileType(MultipartFile file) throws IOException{
        Tika tika = new Tika();

        if(!allowedMimeTypes.stream().anyMatch(tika.detect(file.getInputStream())::startsWith)) {
            return false;
        }
        return true;
    }

    /**
     * 탐지된 파일의 타입을 반환합니다.
     *
     * @param file
     * @return
     * @throws IOException
     */
    private String getDetectedType(MultipartFile file) throws IOException {
        Tika tika = new Tika();
        return tika.detect(file.getInputStream());
    }


    /**
     * 파일 저장 전에 필요한 작업을 수행합니다.
     *
     * @param file
     * @return FileSaveVO
     */
    private FileSaveVO prepareFileSave(MultipartFile file) {
        String ortxFileNm = file.getOriginalFilename();
        double fileSizeInMB = file.getSize() / (1024.0 * 1024.0);

        UUID uuid = UUID.randomUUID();
        String saveFileNm = uuid + "_" + ortxFileNm;
        String fileType = ortxFileNm.substring(ortxFileNm.lastIndexOf('.') + 1);
        BigDecimal fileMg = BigDecimal.valueOf(fileSizeInMB).setScale(2, RoundingMode.HALF_UP);
        String fileSavePath = makeDir(defaultUploadPath);

        return FileSaveVO.builder()
                .saveFileNm(saveFileNm)
                .fileType(fileType)
                .fileMg(fileMg)
                .fileSavePath(fileSavePath)
                .build();
    }

    /**
     * 파일 저장 전에 필요한 작업을 수행합니다.
     * filePath를 파라미터로 전달해서 파일 저장 위치를 지정할 수 있습니다.
     *
     * @param file
     * @param filePath
     * @return
     */
    private FileSaveVO prepareFileSave(MultipartFile file, String filePath) {
        String ortxFileNm = file.getOriginalFilename();
        double fileSizeInMB = file.getSize() / (1024.0 * 1024.0);

        UUID uuid = UUID.randomUUID();
        String saveFileNm = uuid + "_" + ortxFileNm;
        String fileType = ortxFileNm.substring(ortxFileNm.lastIndexOf('.') + 1);
        BigDecimal fileMg = BigDecimal.valueOf(fileSizeInMB).setScale(2, RoundingMode.HALF_UP);
        String fileSavePath = filePath;

        return FileSaveVO.builder()
                .saveFileNm(saveFileNm)
                .fileType(fileType)
                .fileMg(fileMg)
                .fileSavePath(fileSavePath)
                .build();
    }


    /**
     * [2024-03-28]
     *  1. FileUtil의 uploadFiles 메서드와 같은 기능을 수행합니다.
     *  2. 코드 가독성을 약간 높였습니다.
     *
     * @param file
     * @return FileDetailVO
     * @throws IOException
     */
    public FileDetailVO uploadFile(MultipartFile file) throws IOException {

        if(isValidFileType(file) == false) {
            throw new InvalidFileTypeException("Invalid file type: " + file.getOriginalFilename() + ", " + getDetectedType(file));
        }

        FileSaveVO fileSaveVO = prepareFileSave(file);

        /**
         * 1. 디렉토리 경로에 파일 저장
         */
        File saveFile = new File(fileSaveVO.getFileSavePath(), fileSaveVO.getSaveFileNm());
        FileCopyUtils.copy(file.getBytes(), saveFile);


        /**
         * 2. 탐지된 파일 타입이 이미지면 썸네일 이미지 저장
         */
        if (getDetectedType(file).startsWith("image")) {
            FileOutputStream thumbnail = new FileOutputStream(new File(fileSaveVO.getFileSavePath(), "s_" + fileSaveVO.getSaveFileNm()));
            InputStream is = new FileInputStream(saveFile);
            Thumbnailator.createThumbnail(is, thumbnail, 100, 100);
        }

        return FileDetailVO.builder()
                .fileMgmtType(fileSaveVO.getFileType())
                .fileSavePath(fileSaveVO.getFileSavePath())
                .ortxFileNm(file.getOriginalFilename())
                .saveFileNm(fileSaveVO.getSaveFileNm())
                .fileMg(fileSaveVO.getFileMg())
                .fileTypeInfo(getDetectedType(file))
                .build();
    }

    public FileDetailVO uploadNationalFlag(MultipartFile file) throws IOException {

        if(isValidFileType(file) == false) {
            throw new InvalidFileTypeException("Invalid file type: " + file.getOriginalFilename() + ", " + getDetectedType(file));
        }

        FileSaveVO fileSaveVO = prepareFileSave(file, uploadFlagPath);

        /**
         * 1. 디렉토리 경로에 파일 저장
         */
        File saveFile = new File(fileSaveVO.getFileSavePath(), fileSaveVO.getSaveFileNm());
        FileCopyUtils.copy(file.getBytes(), saveFile);


        /**
         * 2. 탐지된 파일 타입이 이미지면 썸네일 이미지 저장
         */
        if (getDetectedType(file).startsWith("image")) {
            FileOutputStream thumbnail = new FileOutputStream(new File(fileSaveVO.getFileSavePath(), "s_" + fileSaveVO.getSaveFileNm()));
            InputStream is = new FileInputStream(saveFile);
            Thumbnailator.createThumbnail(is, thumbnail, 100, 100);
        }

        return FileDetailVO.builder()
                .fileMgmtType(fileSaveVO.getFileType())
                .fileSavePath(fileSaveVO.getFileSavePath())
                .ortxFileNm(file.getOriginalFilename())
                .saveFileNm(fileSaveVO.getSaveFileNm())
                .fileMg(fileSaveVO.getFileMg())
                .fileTypeInfo(getDetectedType(file))
                .build();
    }

    public FileDetailVO uploadLogo(MultipartFile file) throws IOException {
        if(isValidFileType(file) == false) {
            throw new InvalidFileTypeException("Invalid file type: " + file.getOriginalFilename() + ", " + getDetectedType(file));
        }

        FileSaveVO fileSaveVO = prepareFileSave(file);

        /**
         * - 디렉토리 경로에 파일 저장
         */
        File saveFile = new File(fileSaveVO.getFileSavePath(), fileSaveVO.getSaveFileNm());
        FileCopyUtils.copy(file.getBytes(), saveFile);


        return FileDetailVO.builder()
                .fileSavePath(fileSaveVO.getFileSavePath())
                .ortxFileNm(file.getOriginalFilename())
                .saveFileNm(fileSaveVO.getSaveFileNm())
                .fileMg(fileSaveVO.getFileMg())
                .fileTypeInfo(getDetectedType(file))
                .build();
    }



    private String makeDir(String defaultUploadPath) {
        // 년/월/일 디렉토리 경로 생성
        String path = (defaultUploadPath + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                .replace("/", File.separator);

        File directory = new File(path);

        if (!directory.exists()) {
            log.info(directory.mkdirs() ? "Directory created" : "Directory already exists");
        }
        return path;
    }


    public void deleteFiles(FileDetailVO fileDetailVO) {
        String saveFileNm = fileDetailVO.getSaveFileNm();
        String fileSavePath = fileDetailVO.getFileSavePath();

        if(!StringUtils.hasLength(saveFileNm)){
            return;
        }
        String thumbnail_file_name = "s_" + saveFileNm;
        Path thumbnailPath = Paths.get(fileSavePath, thumbnail_file_name);
        Path filePath = Paths.get(fileSavePath, saveFileNm);

        try{
            Files.deleteIfExists(filePath);
            Files.deleteIfExists(thumbnailPath);
            log.info("Delete Success =========>: {}", saveFileNm);
        }catch (IOException ie){
            log.info("Delete Fail");
            throw new RuntimeException(ie.getMessage());
        }
    }
}
