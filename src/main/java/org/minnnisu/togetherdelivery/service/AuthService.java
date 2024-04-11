package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.constant.TokenType;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.auth.*;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.provider.JwtTokenProvider;
import org.minnnisu.togetherdelivery.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final RedisTemplate<String, String> redisTemplate;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupDto signup(
            SignupRequestDto signupRequestDto
    ) {
        if (userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()) {
            throw new CustomErrorException(ErrorCode.DuplicatedUsernameError);
        }

        if (userRepository.findByNickname(signupRequestDto.getNickname()).isPresent()) {
            throw new CustomErrorException(ErrorCode.DuplicatedNicknameError);
        }

        checkPasswordAndPasswordCheckEqual(signupRequestDto.getPassword(), signupRequestDto.getPasswordCheck());

        User user = userRepository.save(User.fromDto(signupRequestDto, passwordEncoder));
        return SignupDto.fromEntity(user);
    }

    private void checkPasswordAndPasswordCheckEqual(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new CustomErrorException(ErrorCode.NotEqualPasswordAndPasswordCheck);
        }
    }

    public void checkUsernameDuplication(UsernameDuplicationCheckDto usernameDuplicationCheckDto) {
        if (userRepository.findByUsername(usernameDuplicationCheckDto.getUsername()).isPresent()) {
            throw new CustomErrorException(ErrorCode.DuplicatedUsernameError);
        }
    }


    public void checkNicknameDuplication(NicknameDuplicationCheckDto nicknameDuplicationCheckDto) {
        if (userRepository.findByNickname(nicknameDuplicationCheckDto.getNickname()).isPresent()) {
            throw new CustomErrorException(ErrorCode.DuplicatedNicknameError);
        }
    }

    public void logout(String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String resolvedRefreshToken = jwtTokenProvider.resolveToken(refreshToken);

        if (resolvedRefreshToken == null) {
            throw new CustomErrorException(ErrorCode.NotValidRequestError);
        }

        String savedAccessToken = valueOperations.get(resolvedRefreshToken);
        if (savedAccessToken == null) {
            throw new CustomErrorException(ErrorCode.NoSuchRefreshTokenError);
        }

        valueOperations.getAndDelete(resolvedRefreshToken);
    }

    public ReIssueTokenDto reIssueToken(String accessToken, String refreshToken) {
        log.info("POST /auth/refreshToken");
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String resolvedAccessToken = jwtTokenProvider.resolveToken(accessToken);
        String resolvedRefreshToken = jwtTokenProvider.resolveToken(refreshToken);

        log.info("accessToken: " + resolvedAccessToken);
        log.info("refreshToken: " + resolvedRefreshToken);

        if (resolvedAccessToken == null || resolvedRefreshToken == null) {
            throw new CustomErrorException(ErrorCode.NotValidRequestError);
        }

        String savedAccessToken = valueOperations.get(resolvedRefreshToken);
        if (savedAccessToken == null) {
            throw new CustomErrorException(ErrorCode.NoSuchRefreshTokenError);
        }

        // RefreshToken 유효성 및 만료여부 확인
        boolean isExpiredRefreshToken = jwtTokenProvider.isExpiredToken(TokenType.REFRESH_TOKEN, resolvedRefreshToken);
        if (isExpiredRefreshToken) {
            valueOperations.getAndDelete(resolvedRefreshToken);
            throw new CustomErrorException(ErrorCode.ExpiredRefreshTokenError);
        }

        if (!resolvedAccessToken.equals(savedAccessToken)) {
            // RefreshToken이 탈취 당한 것으로 판단
            valueOperations.getAndDelete(resolvedRefreshToken);
            throw new CustomErrorException(ErrorCode.NoSuchAccessTokenError);
        }

        // AccessToken 유효성 및 만료여부 확인
        boolean isExpiredAccessToken = jwtTokenProvider.isExpiredToken(TokenType.ACCESS_TOKEN, resolvedAccessToken);
        if (!isExpiredAccessToken) {
            // RefreshToken이 탈취 당한 것으로 판단
            valueOperations.getAndDelete(resolvedRefreshToken);
            throw new CustomErrorException(ErrorCode.NotExpiredAccessTokenError);
        }

        String reIssuedAccessToken = jwtTokenProvider.reIssueAccessToken(resolvedAccessToken);
        valueOperations.getAndDelete(resolvedRefreshToken);
        valueOperations.set(resolvedRefreshToken, reIssuedAccessToken);
        return ReIssueTokenDto.of(reIssuedAccessToken);
    }
}
