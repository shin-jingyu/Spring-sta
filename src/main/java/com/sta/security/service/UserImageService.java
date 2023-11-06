package com.sta.security.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserImageService {

	private final String finalUploadDir = "C:/Spring/sta/src/main/resources/static/uploads/final";
	private final String tempUploadDir = "C:/Spring/sta/src/main/resources/static/uploads/temp";

	public String finalImage(String uniqueFileName) throws IOException {
		Path sourcePath = Paths.get(tempUploadDir, uniqueFileName); // 임시 폴더에서 파일 경로
		Path targetPath = Paths.get(finalUploadDir, uniqueFileName); // 최종 업로드 폴더로 파일 경로

		Files.move(sourcePath, targetPath); // 파일 이동

		return finalUploadDir+"/"+uniqueFileName; // 이동된 파일의 경로 반환
	}

	public String tempImage(MultipartFile file) throws IOException {

		String originalFileName = file.getOriginalFilename();
		String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
		// 고유한 파일 이름 생성
		String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

		Path tempFilePath = Paths.get(tempUploadDir, uniqueFileName);
		Files.write(tempFilePath, file.getBytes());
		return uniqueFileName;
	}
}
