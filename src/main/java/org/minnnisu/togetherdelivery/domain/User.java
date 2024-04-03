package org.minnnisu.togetherdelivery.domain;

import jakarta.persistence.*;
import lombok.*;
import org.minnnisu.togetherdelivery.dto.auth.SignupRequestDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "Users")
public class User implements UserDetails {
    @GeneratedValue
    @Id
    private Long id;
    private String username;
    private String password;
    private String name;
    private String nickname;
    private String email;
    private boolean isTelephoneAuth;
    private String telephone;
    private boolean isCollegeAuth;
    private String college;
    private String authority;


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

    public static User fromDto(
            SignupRequestDto signupRequestDto,
            PasswordEncoder passwordEncoder
    ){
        return User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .name(signupRequestDto.getName())
                .nickname(signupRequestDto.getNickname())
                .email(signupRequestDto.getEmail())
                .telephone(signupRequestDto.getTelephone())
                .isTelephoneAuth(false)
                .college(signupRequestDto.getCollege())
                .isCollegeAuth(false)
                .authority("ROLE_USER")
                .build();
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
}
