package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.ReIssueTokenDto;
import org.minnnisu.togetherdelivery.dto.SignupDto;
import org.minnnisu.togetherdelivery.dto.SignupRequestDto;
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
            throw new CustomErrorException(ErrorCode.DuplicatedUserNameError);
        }

        checkPasswordAndPasswordCheckEqual(signupRequestDto.getPassword(), signupRequestDto.getPasswordCheck());

        User user =  userRepository.save(User.fromDto(signupRequestDto, passwordEncoder));
        return SignupDto.fromEntity(user);
    }

    private void checkPasswordAndPasswordCheckEqual(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)){
            throw new CustomErrorException(ErrorCode.NotEqualPasswordAndPasswordCheck);
        }
    }

    public void logout(String accessToken, String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String resolvedAccessToken = jwtTokenProvider.resolveToken(accessToken);
        String resolvedRefreshToken = jwtTokenProvider.resolveToken(refreshToken);

        if (resolvedAccessToken == null || resolvedRefreshToken == null) {
            throw new CustomErrorException(ErrorCode.NotValidRequestError);
        }

        String savedAccessToken = valueOperations.get(resolvedRefreshToken);
        if (savedAccessToken == null) {
            throw new CustomErrorException(ErrorCode.NoSuchRefreshTokenError);
        }

        if (!resolvedAccessToken.equals(savedAccessToken)) {
            // RefreshToken이 탈취 당한 것으로 판단
            valueOperations.getAndDelete(resolvedRefreshToken);
            throw new CustomErrorException(ErrorCode.NoSuchAccessTokenError);
        }

        valueOperations.getAndDelete(resolvedRefreshToken);
    }

    public ReIssueTokenDto reIssueToken(String accessToken, String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String resolvedAccessToken = jwtTokenProvider.resolveToken(accessToken);
        String resolvedRefreshToken = jwtTokenProvider.resolveToken(refreshToken);

        if (resolvedAccessToken == null || resolvedRefreshToken == null) {
            throw new CustomErrorException(ErrorCode.NotValidRequestError);
        }

        String savedAccessToken = valueOperations.get(resolvedRefreshToken);
        if (savedAccessToken == null) {
            throw new CustomErrorException(ErrorCode.NoSuchRefreshTokenError);
        }

        if (!resolvedAccessToken.equals(savedAccessToken)) {
            // RefreshToken이 탈취 당한 것으로 판단
            valueOperations.getAndDelete(resolvedRefreshToken);
            throw new CustomErrorException(ErrorCode.NoSuchAccessTokenError);
        }

        // AccessToken 유효성 및 만료여부 확인
        boolean isExpiredAccessToken = jwtTokenProvider.isExpiredAccessToken(resolvedAccessToken);
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
