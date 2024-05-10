package org.minnnisu.togetherdelivery.domain;

import jakarta.persistence.*;
import lombok.*;
import org.minnnisu.togetherdelivery.dto.post.PostSaveRequestDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Location {
    @Id
    @GeneratedValue
    private Long id;

    private String address;

    private String shortAddress;

    private double latitude;

    private double longitude;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public static Location fromDto(PostSaveRequestDto postSaveRequestDto){
        return Location.builder()
                .address(postSaveRequestDto.getMeetLocation().getAddress())
                .shortAddress(postSaveRequestDto.getMeetLocation().getShortAddress())
                .latitude(postSaveRequestDto.getMeetLocation().getLatitude())
                .longitude(postSaveRequestDto.getMeetLocation().getLongitude())
                .build();
    }
}
