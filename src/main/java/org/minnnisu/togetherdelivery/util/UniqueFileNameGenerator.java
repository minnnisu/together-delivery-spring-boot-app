package org.minnnisu.togetherdelivery.util;

import java.util.UUID;

public class UniqueFileNameGenerator {


    public static String generateUniqueFileName(String originalFileName) {
        // 파일 확장자 추출
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // UUID 생성 및 하이픈 제거
        String uniqueID = UUID.randomUUID().toString().replaceAll("-", "");

        // 고유한 파일 이름 생성
        return uniqueID + fileExtension;
    }
}
