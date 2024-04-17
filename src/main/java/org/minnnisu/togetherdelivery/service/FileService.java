package org.minnnisu.togetherdelivery.service;

import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.UploadPathType;
import org.minnnisu.togetherdelivery.util.UniqueFileNameGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class FileService {
    public boolean checkImageFile(MultipartFile file) {
        return file.getContentType() != null && file.getContentType().startsWith("image");
    }

    public String saveFiles(UploadPathType uploadPathType, MultipartFile file) throws Exception {
        String uniqueFileName = UniqueFileNameGenerator.generateUniqueFileName(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = Paths.get(uploadPathType.getPath() + "/" + uniqueFileName);
        Files.copy(file.getInputStream(), filePath);
        return uniqueFileName;
    }

    public void deleteFiles(UploadPathType uploadPathType, List<String> fileNames) {
        if (fileNames != null) {
            for (String fileName : fileNames) {
                String filePath = uploadPathType.getPath() + "/" + fileName;
                File file = new File(filePath);
                if (!file.delete()) {
                    log.info("파일 삭제를 실패했습니다.");
                    log.info("파일 위치:" + filePath);
                }
            }
        }
    }
}
