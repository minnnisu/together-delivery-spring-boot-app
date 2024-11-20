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

    public static Location of(
            Long id,
            String address,
            String shortAddress,
            double latitude,
            double longitude,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt){
        return Location.builder()
                .id(id)
                .address(address)
                .shortAddress(shortAddress)
                .latitude(latitude)
                .longitude(longitude)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }

    public static Location of(
            String address,
            String shortAddress,
            double latitude,
            double longitude){
        return Location.builder()
                .address(address)
                .shortAddress(shortAddress)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
