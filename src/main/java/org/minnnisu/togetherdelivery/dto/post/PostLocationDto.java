package org.minnnisu.togetherdelivery.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Post;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostLocationDto {
    @NotBlank
    private String address;

    @NotBlank
    private String shortAddress;

    @NotBlank
    private double latitude;

    @NotBlank
    private double longitude;

    public static PostLocationDto fromEntity(Post post){
        return PostLocationDto.builder()
                .address(post.getMeetLocation().getAddress())
                .shortAddress(post.getMeetLocation().getShortAddress())
                .latitude(post.getMeetLocation().getLatitude())
                .longitude(post.getMeetLocation().getLongitude())
                .build();
    }
}
