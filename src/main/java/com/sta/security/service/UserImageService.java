package com.sta.security.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserImageService {

    private final String tempUploadDir = "uploads/temp/";
    private final String finalUploadDir = "uploads/final/";
    
    
    public String tempImage(MultipartFile file) throws IOException {
        // 이미지 파일 이름 생성 (고유한 이름을 생성)
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        // 임시 디렉토리에 이미지 저장
        Path tempFilePath = Paths.get(tempUploadDir, uniqueFileName);
        Files.write(tempFilePath, file.getBytes());

      
        // 최종 이미지 URL 반환
        return "/uploads/temp/" + uniqueFileName;
    }
    public String finalImage(String uniqueFileName) throws IOException {
    	Path tempFilePath = Paths.get(tempUploadDir, uniqueFileName);
        Path finalFilePath = Paths.get(finalUploadDir, uniqueFileName);
        Files.move(tempFilePath, finalFilePath);

    	return "/uploads/final/" + uniqueFileName;
    }
}

