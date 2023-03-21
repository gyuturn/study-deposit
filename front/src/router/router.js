import { createWebHistory, createRouter } from "vue-router";
import KakaoLogin from "@/components/login/KakaoLogin.vue"

const router = createRouter({
    history : createWebHistory(),
    routes : [ // path별 component를 추가한다.
        { path : "/", name : "", component : "" },
        { path : "/login/kakao", name : "kakak-login", component : KakaoLogin },
    ]
});

export default router;