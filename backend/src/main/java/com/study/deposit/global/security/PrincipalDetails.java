package com.study.deposit.global.security;

import com.study.deposit.domain.user.domain.Users;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class PrincipalDetails implements UserDetails,OAuth2User {

    private Users user;
    private Map<String, Object> attributes;

    // 일반 로그인
    public PrincipalDetails(Users user){
        this.user = user;
    }
    // OAuth 로그인
    public PrincipalDetails(Users user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    //
    // UserDetails //
    @Override // 해당 User의 권한을 리턴하는곳.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().name();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getNickName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


    // OAuth2User //
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }


    @Override
    public String getName() {
        return user.getNickName();
    }
}