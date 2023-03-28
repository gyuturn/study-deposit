import { createWebHistory, createRouter } from "vue-router";
import KakaoLogin from "@/components/login/KakaoLogin.vue"
import LoginOrSignUpCheck from "@/components/login/LoginOrSignUpCheck.vue"
import EnterNickName from "@/components/login/EnterNickName.vue"
import SuccessChange from "@/components/login/SuccessChange.vue"

import MypageHome from "@/components/mypage/MypageHome.vue"
import KakaoPayment from "@/components/mypage/kakaopay/KakaoPayment.vue"

const router = createRouter({
    history : createWebHistory(),
    routes : [ // path별 component를 추가한다.
        { path : "/", name : "", component : "" },
        { path : "/login/kakao", name : "KakaoLogin", component : KakaoLogin },
        { path : "/login/check", name : "LoginOrSignUpCheck", component : LoginOrSignUpCheck },
        { path : "/login/enter/nickname", name : "EnterNickName", component : EnterNickName },

        { path : "/change/success", name : "SuccessChange", component : SuccessChange },

        { path : "/mypage/home", name : "MypageHome", component : MypageHome },

        { path : "/payment/kakaopay", name : "KakaoPayment", component : KakaoPayment },
    ]
});


export default router;