package com.sta.security.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sta.board.domain.Ripple;
import com.sta.board.domain.RippleRequestDTO;
import com.sta.security.domain.JoinRequest;
import com.sta.security.domain.LoginRequest;
import com.sta.security.domain.User;
import com.sta.security.domain.UserUpdateDTO;
import com.sta.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder encoder;
	private final String finalUploadDir = "C:/Spring/sta/src/main/resources/static/uploads/final";
	private final String tempUploadDir = "C:/Spring/sta/src/main/resources/static/uploads/temp";

	public boolean checkLoginIdDuplicate(String userid) {
		return userRepository.existsByUserid(userid);
	}

	public boolean checkNicknameDuplicate(String nickname) {
		return userRepository.existsByNickname(nickname);
	}

	public void join(JoinRequest req) {
		userRepository.save(req.toEntity(encoder.encode(req.getPassword())));
	}

	public User login(LoginRequest req) {
		Optional<User> optionalUser = userRepository.findByUserid(req.getUserid());

		// loginId와 일치하는 User가 없으면 null return
		if (optionalUser.isEmpty()) {
			return null;
		}

		User user = optionalUser.get();

		// 찾아온 User의 password와 입력된 password가 다르면 null return
		if (!user.getPassword().equals(req.getPassword())) {
			return null;
		}

		return user;
	}
	public void userDelete(String userId) {
		User user = userRepository.findByUserid(userId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
		userRepository.delete(user);
	}
	
	public User getLoginUserByLoginId(String userId) {
		if (userId == null)
			return null;

		Optional<User> optionalUser = userRepository.findByUserid(userId);
		if (optionalUser.isEmpty())
			return null;

		return optionalUser.get();
	}

	@Transactional
	public String userUpdate(UserUpdateDTO updateDTO) {
		User user = userRepository.findByUserid(updateDTO.getUserid())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
		updateDTO.setPassword(encoder.encode(updateDTO.getPassword()));
		user.update(updateDTO);
		return updateDTO.getUserid();
	}

	public String finalImage(String uniqueFileName) throws IOException {
		Path sourcePath = Paths.get(tempUploadDir, uniqueFileName); // 임시 폴더에서 파일 경로
		Path targetPath = Paths.get(finalUploadDir, uniqueFileName); // 최종 업로드 폴더로 파일 경로
		Path deletePath = Paths.get(tempUploadDir);
		Files.move(sourcePath, targetPath); // 파일 이동

		Files.walk(deletePath).filter(Files::isRegularFile).forEach(file -> {
			try {
				Files.delete(file);
			} catch (IOException e) {
				System.err.println("Failed to delete file: " + file);
			}
		});
		return "/uploads/final/" + uniqueFileName; // 이동된 파일의 경로 반환
	}

	public String deleteImge(String deleteImg) throws IOException {
		String basePath = "/uploads/final/";
		String relativePath = deleteImg.replaceFirst("^" + basePath, "");
		Path deleteImgPath = Paths.get(finalUploadDir, relativePath);
		if (Files.exists(deleteImgPath)) {
			Files.delete(deleteImgPath);
			return deleteImg;
		} else {
			System.out.println("File does not exist: " + deleteImg);
			return null;
		}

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
