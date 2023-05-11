import { createWebHistory, createRouter } from "vue-router";
import KakaoLogin from "@/components/login/KakaoLogin.vue"
import LoginOrSignUpCheck from "@/components/login/LoginOrSignUpCheck.vue"
import EnterNickName from "@/components/login/EnterNickName.vue"
import SuccessChange from "@/components/login/SuccessChange.vue"

import MypageHome from "@/components/mypage/MypageHome.vue"
import KakaoPayment from "@/components/mypage/payment/kakaopay/KakaoPayment.vue"
import PaymentResult from "@/components/mypage/payment/PaymentResult.vue"

import PostStudyRoom from "@/components/study_room/PostStudyRoom.vue"
import MainStudyList from "@/components/study_room/MainStudyList.vue"

import MainStudyRoom from "@/components/study_room/enter/MainStudyRoom.vue"
const router = createRouter({
    history: createWebHistory(),
    routes: [ // path별 component를 추가한다.
        { path: "/", name: "", component: "" },
        { path: "/login/kakao", name: "KakaoLogin", component: KakaoLogin },
        { path: "/login/check", name: "LoginOrSignUpCheck", component: LoginOrSignUpCheck },
        { path: "/login/enter/nickname", name: "EnterNickName", component: EnterNickName },

        { path: "/change/success", name: "SuccessChange", component: SuccessChange },

        { path: "/mypage/home", name: "MypageHome", component: MypageHome },

        { path: "/payment/kakaopay", name: "KakaoPayment", component: KakaoPayment },
        { path: "/payment/result", name: "PaymentResult", component: PaymentResult },

        { path: "/studyroom/post", name: "PostStudyRoom", component: PostStudyRoom },
        { path: "/studyroom/list", name: "MainStudyList", component: MainStudyList },
        {
            path: "/studyroom/attendance/:id",
            name: "MainStudyRoom",
            component: MainStudyRoom,
        },
    ]
});


export default router;