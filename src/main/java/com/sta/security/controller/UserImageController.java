package com.sta.security.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sta.security.service.UserImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class UserImageController {
	private final UserImageService imageService;
	
	@PostMapping("/upload")
	public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String imageUrl = imageService.tempImage(file);
        return imageUrl;
    }
}
