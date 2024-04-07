package org.minnnisu.togetherdelivery.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NotValidRequestError(
            HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."
    ),
    DuplicatedUsernameError(
            HttpStatus.CONFLICT, "중복된 아이디입니다."
    ),
    DuplicatedNicknameError(
            HttpStatus.CONFLICT, "중복된 닉네임입니다"
    ),
    NotEqualPasswordAndPasswordCheck(
            HttpStatus.BAD_REQUEST, "패스워드와 패스워드 재입력이 일치하지 않습니다."
    ),
    UserNotFoundError(
            HttpStatus.NOT_FOUND, "유저 정보를 찾을 수 없습니다."
    ),
    QueryParamTypeMismatchError(
            HttpStatus.BAD_REQUEST, "해당 쿼리 파라미터의 타입이 올바르지 않습니다."
    ),
    MissingQueryParamError(
            HttpStatus.BAD_REQUEST, "해당 파라미터의 값이 존재하지 않습니다.."
    ),
    AccessDeniedError(
            HttpStatus.FORBIDDEN, "접근할 수 없는 권한을 가진 사용자입니다."
    ),
    InternalServerError(
            HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생하였습니다. 문제가 지속되면 관리자에게 문의하세요."
    ),
    NotValidAccessTokenError(
            HttpStatus.BAD_REQUEST, "유효하지 않은 AccessToken입니다."
    ),
    NotExpiredAccessTokenError(
            HttpStatus.BAD_REQUEST, "만료되지 않은 AccessToken입니다."
    ),
    ExpiredAccessTokenError(
            HttpStatus.BAD_REQUEST, "만료된 AccessToken입니다."
    ),
    NoSuchAccessTokenError(
            HttpStatus.BAD_REQUEST, "존재하지 않은 AccessToken입니다."
    ),
    NotValidRefreshTokenError(
            HttpStatus.BAD_REQUEST, "유효하지 않은 RefreshToken입니다."
    ),
    NotExpiredRefreshTokenError(
            HttpStatus.BAD_REQUEST, "만료되지 않은 RefreshToken입니다."
    ),
    ExpiredRefreshTokenError(
            HttpStatus.BAD_REQUEST, "만료된 RefreshToken입니다."
    ),
    NoSuchRefreshTokenError(
            HttpStatus.BAD_REQUEST, "존재하지 않은 RefreshToken입니다."
    ),

    // ----- Category ------
    NoSuchCategoryError(
            HttpStatus.BAD_REQUEST, "존재하지 않은 카테고리입니다."
    ),


    // ----- Post -----
    NoSuchPostError(
            HttpStatus.NOT_FOUND, "존재하지 않은 배달 게시물입니다."
    ),

    NoRequestBodyError(
            HttpStatus.BAD_REQUEST, "request body가 전달되지 않았습니다."
    ),
    IllegalArgumentError(
            HttpStatus.BAD_REQUEST, "잘못된 값이 전달되었습니다"
    );


    private final HttpStatus httpStatus;
    private final String message;

}