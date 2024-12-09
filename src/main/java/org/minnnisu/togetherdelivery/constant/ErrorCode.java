package org.minnnisu.togetherdelivery.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // ----- Common ------
    NotValidRequestError(
            HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."
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

    // ----- User ------
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
    UserPermissionDeniedError(
            HttpStatus.FORBIDDEN, "권한이 없는 유저입니다."
    ),

    // ----- Token ------
    NotValidAccessTokenError(
            HttpStatus.UNAUTHORIZED, "유효하지 않은 AccessToken입니다."
    ),
    NotExpiredAccessTokenError(
            HttpStatus.UNAUTHORIZED, "만료되지 않은 AccessToken입니다."
    ),
    ExpiredAccessTokenError(
            HttpStatus.UNAUTHORIZED, "만료된 AccessToken입니다."
    ),
    NoSuchAccessTokenError(
            HttpStatus.UNAUTHORIZED, "존재하지 않은 AccessToken입니다."
    ),
    NotValidRefreshTokenError(
            HttpStatus.UNAUTHORIZED, "유효하지 않은 RefreshToken입니다."
    ),
    NotExpiredRefreshTokenError(
            HttpStatus.UNAUTHORIZED, "만료되지 않은 RefreshToken입니다."
    ),
    ExpiredRefreshTokenError(
            HttpStatus.UNAUTHORIZED, "만료된 RefreshToken입니다."
    ),
    NoSuchRefreshTokenError(
            HttpStatus.UNAUTHORIZED, "존재하지 않은 RefreshToken입니다."
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


    // ---- Image ----
    NoImageNameError(
            HttpStatus.BAD_REQUEST, "이미지 이름을 찾을 수 없습니다."
    ),
    NoImageFileError(
            HttpStatus.BAD_REQUEST, "이미지 파일만 업로드할 수 있습니다."
    ),
    SizeLimitExceededError(
            HttpStatus.BAD_REQUEST, "업로드 가능한 파일 크기보다 큽니다."
    ),

    // ---- Comment ----
    NoSuchCommentError(
            HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."
    ),
    NotTheAuthorOfTheComment(
            HttpStatus.UNAUTHORIZED, "댓글의 작성자가 아닙니다."
    ),
    DeletedCommentError(
            HttpStatus.NOT_FOUND, "삭제된 댓글입니다"
    ),

    // ---- Reply ----
    NoSuchReplyError(
            HttpStatus.NOT_FOUND, "존재하지 않는 답글입니다."
    ),
    NotTheAuthorOfTheReply(
            HttpStatus.UNAUTHORIZED, "답글의 작성자가 아닙니다."
    ),
    DeletedReplyError(
            HttpStatus.NOT_FOUND, "삭제된 답글입니다"
    ),

    // ---- ChatRoom ----
    AlreadyExistChatRoomError(
            HttpStatus.BAD_REQUEST, "이미 존재하는 채팅방입니다."
    ),
    NoSuchChatRoomError(
            HttpStatus.NOT_FOUND, "존재하지 않은 채팅방입니다"
    ),
    AlreadyExistChatRoomMemberError(
            HttpStatus.BAD_REQUEST, "이미 존재하는 채팅방 인원 입니다."
    ),
    NoSuchMemberInChatRoomError(
            HttpStatus.NOT_FOUND, "채팅방에 존재하지 않는 유저입니다."
    ),
    UnsupportedMessageTypeError(
            HttpStatus.BAD_REQUEST, "지원되지 않은 메시지 타입입니다."
    ),
    NoSuchChatMessageError(
            HttpStatus.NOT_FOUND, "존재하지 않은 채팅 메시지입니다"
    ),
    NotIncludeChatRoomCreatorInfoError(
            HttpStatus.BAD_REQUEST, "채팅방을 생성한 유저의 정보가 없습니다."
    ),
    ChatInvitePermissionDeniedError(
            HttpStatus.FORBIDDEN, "채팅방 초대 권한이 없는 멤버입니다."
    ),

    // ---- ChatMessage ----
    NotTheSenderOfChatMessage(
            HttpStatus.UNAUTHORIZED, "채팅 메시지의 작성자가 아닙니다."
    ),
    ClosedPostError(
            HttpStatus.FORBIDDEN, "비활성화된 게시물입니다."
    );

    private final HttpStatus httpStatus;
    private final String message;

}