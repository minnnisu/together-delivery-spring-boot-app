package org.minnnisu.togetherdelivery.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReportedPost {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Post post;

    @Lob
    private String comment;

    @CreatedDate
    private LocalDateTime createdAt;
}
