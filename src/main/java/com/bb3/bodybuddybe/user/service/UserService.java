package com.bb3.bodybuddybe.user.service;

import com.bb3.bodybuddybe.user.dto.*;
import com.bb3.bodybuddybe.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
    @Transactional
    void signup(SignupRequestDto requestDto);

    @Transactional
    void socialSignup(SocialSignupRequestDto requestDto, User user);

    UserResponseDto getUser(User user);

    void reissueToken(ReissueRequestDto requestDto, HttpServletResponse response);

    void logout(LogoutRequestDto requestDto, HttpServletRequest request);

    @Transactional
    void deleteUser(UserDeleteRequestDto requestDto, User user);

    @Transactional
    void changePassword(PasswordChangeRequestDto requestDto, User user);

    @Transactional
    void uploadProfileImage(MultipartFile file, User user);

    @Transactional(readOnly = true)
    String getProfileImage(User user);

    @Transactional
    void deleteProfileImage(User user);

    @Transactional
    void createProfile(ProfileRequestDto requestDto, User user);

    @Transactional
    void updateProfile(ProfileRequestDto requestDto, User user);

    @Transactional(readOnly = true)
    ProfileResponseDto getProfile(Long userId);
}
