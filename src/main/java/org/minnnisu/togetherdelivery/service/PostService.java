package org.minnnisu.togetherdelivery.service;

import lombok.RequiredArgsConstructor;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.Category;
import org.minnnisu.togetherdelivery.domain.Post;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.post.PostSaveResponseDto;
import org.minnnisu.togetherdelivery.dto.post.PostSaveRequestDto;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.minnnisu.togetherdelivery.repository.CategoryRepository;
import org.minnnisu.togetherdelivery.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService{
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

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
