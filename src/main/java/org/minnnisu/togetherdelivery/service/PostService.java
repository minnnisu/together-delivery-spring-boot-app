package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.Category;
import org.minnnisu.togetherdelivery.domain.Post;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.post.PostDetailResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostListResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostSaveResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostSaveRequestDto;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.CategoryRepository;
import org.minnnisu.togetherdelivery.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class PostService{
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    private final int PAGE_SIZE = 10;

    public PostListResponseDto getPost(int pageNo) {
        Page<Post> post = postRepository.findAll(PageRequest.of(pageNo, PAGE_SIZE));
        return PostListResponseDto.fromPage(post);
    }

    public PostDetailResponseDto getPostDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchPostError));
        return PostDetailResponseDto.fromEntity(post);

    }

    public PostSaveResponseDto savePost(User user, PostSaveRequestDto postSaveRequestDto) {
        if (user == null) {
            throw new CustomErrorException(ErrorCode.AccessDeniedError);
        }

        Category category = categoryRepository.findByCategoryCode(postSaveRequestDto.getCategoryCode())
                .orElseThrow(() -> new CustomErrorException(ErrorCode.NoSuchCategoryError));

        Post post = postRepository.save(Post.of(postSaveRequestDto, user, category));
        return PostSaveResponseDto.fromEntity(post);
    }



}
