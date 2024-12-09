package org.minnnisu.togetherdelivery.domain;

import jakarta.persistence.*;
import lombok.*;
import org.minnnisu.togetherdelivery.dto.auth.SignupRequestDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
    @GeneratedValue
    @Id
    private Long id;

    private String username;

    private String password;

    private String name;

    private String nickname;

    private String email;

    private boolean isCollegeAuth;

    private String college;

    private String authority;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public User(
            String username,
            String password,
            String authority
    ) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static User fromDto(
            SignupRequestDto signupRequestDto,
            PasswordEncoder passwordEncoder
    ) {
        return User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .name(signupRequestDto.getName())
                .nickname(signupRequestDto.getNickname())
                .email(signupRequestDto.getEmail())
                .college(signupRequestDto.getCollege())
                .isCollegeAuth(false)
                .authority("ROLE_USER")
                .build();
    }

    public static User of(
            String username,
            String password,
            String name,
            String nickname,
            String email,
            boolean isCollegeAuth,
            String college,
            PasswordEncoder passwordEncoder) {

        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .email(email)
                .college(college)
                .isCollegeAuth(isCollegeAuth)
                .authority("ROLE_USER")
                .build();
    }
}
