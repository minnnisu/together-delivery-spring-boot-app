package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.constant.UploadPathType;
import org.minnnisu.togetherdelivery.domain.*;
import org.minnnisu.togetherdelivery.dto.post.PostDetailResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostListResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostSaveResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostSaveRequestDto;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.CategoryRepository;
import org.minnnisu.togetherdelivery.repository.LocationRepository;
import org.minnnisu.togetherdelivery.repository.PostImageRepository;
import org.minnnisu.togetherdelivery.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService{
    private final FileService fileService;

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final PostImageRepository postImageRepository;

    private final int PAGE_SIZE = 10;

    public PostListResponseDto getPost(int pageNo) {
        Page<Post> post = postRepository.findAll(PageRequest.of(pageNo - 1, PAGE_SIZE));
        return PostListResponseDto.fromPage(post);
    }

    public PostDetailResponseDto getPostDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchPostError));
        List<PostImage> postImages = postImageRepository.findPostImageByPost(post);

        return PostDetailResponseDto.fromEntity(post, postImages);
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

        return PostSaveResponseDto.fromEntity(post, postImages);
    }



}
