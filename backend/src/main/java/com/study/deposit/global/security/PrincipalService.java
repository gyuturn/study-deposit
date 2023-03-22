//package com.study.deposit.global.auth;
//
//
//import com.study.deposit.domain.user.dao.UserDao;
//import com.study.deposit.domain.user.domain.Users;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//// 시큐리티에서 설정에서 LoginProcessingUrl("/login");
//// "/login" 요청이 오면 자동으로 UserDetailsService 타입으로 loC 되어있는  loadUserByUsername 함수가 실행된다.!
//// Authentication 객체로 만들어준다
//@Service
//@RequiredArgsConstructor
//public class PrincipalService implements UserDetailsService {
//
//    private final UserDao userDao;
//
//    //시큐리티 session => Authentication => UserDetails
//    // 여기서 리턴 된 값이 Authentication 안에 들어간다.(리턴될때 들어간다.)
//    // 그리고 시큐리티 session 안에 Authentication 이 들어간다.
//    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        Optional<Users> findUser = userDao.findByEmail(username);
//        if(findUser.isPresent()) {
//            return new PrincipalDetails(findUser.get());
//        }
//
//        return null;
//    }
//}