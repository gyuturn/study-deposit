package com.study.deposit.global.auth;

import com.study.deposit.domain.user.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserDao userDao;

    @ResponseBody
    @GetMapping("/user")
    public String test(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails = " + principalDetails);
        return "index.html";
    }
}
