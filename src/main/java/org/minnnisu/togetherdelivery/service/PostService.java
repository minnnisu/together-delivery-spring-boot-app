package org.minnnisu.togetherdelivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.constant.UploadPathType;
import org.minnnisu.togetherdelivery.domain.*;
import org.minnnisu.togetherdelivery.dto.post.postDetailResponseDto.PostDetailResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostListResponseDto;
import org.minnnisu.togetherdelivery.dto.post.postSaveResponseDto.PostSaveResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostSaveRequestDto;
import org.minnnisu.togetherdelivery.dto.post.postStatusToggle.PostStatusToggleDto;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService{
    private final FileService fileService;

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final PostImageRepository postImageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;

    private final int PAGE_SIZE = 10;

    public PostListResponseDto getPost(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> post = postRepository.findAll(pageable);
        return PostListResponseDto.fromPage(post);
    }

    public PostDetailResponseDto getPostDetail(Long id, User user) {
        boolean isPostCreator = false;
        boolean isChatRoomMember = false;

        Post post = postRepository.findById(id).orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchPostError));
        ChatRoom chatRoom = chatRoomRepository.findByPost(post).orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchChatRoomError));

        if (user != null && post.getUser().getUsername().equals(user.getUsername())) {
            isPostCreator = true;
            isChatRoomMember = true;
        }

        if(!isPostCreator) {
            Optional<ChatRoomMember> chatRoomMember = chatRoomMemberRepository.findByChatRoomAndUser(chatRoom, user);
            if (chatRoomMember.isPresent()) {
                isChatRoomMember = true;
            }
        }

        List<PostImage> postImages = postImageRepository.findPostImageByPost(post);

        return PostDetailResponseDto.of(post, postImages, isPostCreator, isChatRoomMember, chatRoom);
    }

    public PostSaveResponseDto savePost(User user, PostSaveRequestDto postSaveRequestDto, List<MultipartFile> files) {
        List<String> addedImages = new ArrayList<>();
        List<PostImage> postImages = new ArrayList<>();

        if (user == null) {
            throw new CustomErrorException(ErrorCode.UserNotFoundError);
        }

        Category category = categoryRepository.findByCategoryCode(postSaveRequestDto.getCategoryCode())
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchCategoryError));

        Location location = locationRepository.save(Location.fromDto(postSaveRequestDto));

        Post post = postRepository.save(Post.of(postSaveRequestDto, user, category, location));

        if (files != null) {
            for (MultipartFile file : files) {
                if (!fileService.checkImageFile(file)) {
                    fileService.deleteFiles(UploadPathType.POST_IMAGE, addedImages);
                    throw new CustomErrorException(ErrorCode.NoImageFileError);
                }

                try {
                    String uniqueFileName = fileService.saveFiles(UploadPathType.POST_IMAGE, file);
                    PostImage postImage = postImageRepository.save(PostImage.of(post, uniqueFileName));
                    addedImages.add(postImage.getImageName());
                    postImages.add(postImage);
                } catch (NullPointerException e) {
                    fileService.deleteFiles(UploadPathType.POST_IMAGE, addedImages);
                    throw new CustomErrorException(ErrorCode.NoImageNameError);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    fileService.deleteFiles(UploadPathType.POST_IMAGE, addedImages);
                    throw new CustomErrorException(ErrorCode.InternalServerError);
                }
            }

        }

        ChatRoom newChatRoom = chatRoomRepository.save(ChatRoom.of(post));

        ChatRoomMember chatRoomCreator = chatRoomMemberRepository.save(ChatRoomMember.createChatRoomCreator(newChatRoom, user));

        chatMessageRepository.save(
                ChatMessage.of(
                        chatRoomCreator,
                        "채팅방이 생성되었습니다.",
                        ChatMessageType.OPEN));


        return PostSaveResponseDto.of(post, postImages, chatRoomCreator);
    }


    public PostStatusToggleDto togglePost(User user, Long id) {
        Post foundPost = postRepository.findById(id).orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchPostError));
        if(!foundPost.getUser().getUsername().equals(user.getUsername())) {
            throw new CustomErrorException(ErrorCode.AccessDeniedError);
        }

        foundPost.toggleStatus();
        return PostStatusToggleDto.fromEntity(foundPost);
    }
}
