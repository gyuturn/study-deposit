<template>
  <v-container fluid class="fill-height justify-center align-center">
    <v-card elevation="0" height="100%" width="100%">
      <v-card-title
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
        >나의 프로필</v-card-title
      >
      <v-card-text>
        <v-list align="center">
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>
                <img src="@/assets/person.png" />
              </v-list-item-title>
              <v-list-item-subtitle class="emphasized-text">{{
                nickname
              }}</v-list-item-subtitle>
              <v-list-item-subtitle class="emphasized-text"
                >포인트: {{ sumOfChargeAmount }}</v-list-item-subtitle
              >
              <v-list-item-subtitle>{{ email }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <v-list>
          <router-link
            v-for="(item, index) in listItems"
            :key="index"
            :to="item.to"
             style="color: black; text-decoration: none;"
          >
            <v-list-item>
              <v-list-item-content>
                <div
                  class="d-flex align-center justify-space-between"
                  style="width: 100%"
                >
                  <v-list-item-title>{{ item.title }}</v-list-item-title>
                  <img src="@/assets/right_btn.png" />
                </div>
                <v-divider></v-divider>
              </v-list-item-content>
            </v-list-item>
          </router-link>
        </v-list>
      </v-card-text>
    </v-card>
  </v-container>
</template>


<script>
import axios from "axios";
export default {
  name: "MypageHome",
  mounted() {
    // 백엔드 서버로 요청 보내기
    axios
      .get(`${import.meta.env.VITE_API_URI}/users`, {
        withCredentials: true, // Include cookies in the request
      })
      .then((response) => {
        console.log(response.status);

        if (response.status === 200) {
          this.nickname = response.data.data.nickName;
          this.sumOfChargeAmount = `${response.data.data.sumOfChargeAmount}원`;
          this.email = response.data.data.email;
        }
      })
      .catch((error) => {
        //  로그인 안되어 있음
        this.$router.push({ path: "/login/kakao" });
      });
  },
  methods: {},
  data() {
    return {
      listItems: [
      { title: "회원정보 수정", to: "/edit-profile" },
      { title: "스터디방 관리", to: "/study-room-management" },
      { title: "포인트 충전하기", to: "/payment/kakaopay" },
      { title: "포인트 환전하기", to: "/exchange-points" },
      { title: "로그아웃", to: "/logout" },
    ],
      nickname: "",
      sumOfChargeAmount: "",
      email: "",
    };
  },
};
</script>

<style>
.emphasized-text {
  font-weight: bold;
  color: black;
  margin: 10px;
}

</style>
