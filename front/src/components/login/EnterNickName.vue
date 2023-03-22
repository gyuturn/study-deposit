<template>
  <v-container fluid class="fill-height justify-center align-center">
    <v-card elevation="0">
      <v-card-title
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
        >회원가입을 환영해요!</v-card-title
      >
      <v-card-text
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
      >
        현재 닉네임은 <span style="color: #006ffd">{{ nickname }}</span> 입니다.
        <br /><br />닉네임을 변경해보시는 건 어떠세요?
      </v-card-text>
      <v-card-actions>
        <v-row class="align-items-center justify-center">
          <v-col cols="12" md="6" class="text-center flex-column">
            <v-text-field
              v-model="newNickname"
              label="새 닉네임"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column main-btn">
            <v-btn @click="updateNickname">변경</v-btn>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column main-btn">
            <v-btn @click="goHome">다음에 하기</v-btn>
          </v-col>
        </v-row>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "EnterNickName",
  data() {
    return {
      nickname: "",
      newNickname: "",
    };
  },
  methods: {
    updateNickname() {
      // 서버에 새 닉네임을 보내고, 200 OK를 받으면 리다이렉트합니다.
      axios({
        method: "patch", // [요청 타입]
        url: `${import.meta.env.VITE_API_URI}/users/nickname`, // [요청 주소]
        data: JSON.stringify({ newNickName: this.newNickname }), // [요청 데이터]
        headers: {
          "Content-Type": "application/json; charset=utf-8",
        },
        withCredentials: true,
      })
        .then((response) => {
          if (response.status === 200) {
            this.$router.push({ path: "/change/success" });
          } else {
            this.$router.push({ path: "/error" });
          }
        })
        .catch(function (error) {
          this.$router.push({ path: "/error" });
        });
    },
    goHome(){
      this.$router.push({ path: "/" });
    }
  },
  mounted() {
    // 서버에서 현재 닉네임을 가져옵니다.
    axios
      .get(`${import.meta.env.VITE_API_URI}/auth`, {
        withCredentials: true, // Include cookies in the request
      })
      .then((response) => {
        if (response.status === 200) {
          this.nickname = response.data.data.nickName;
        } else {
          this.$router.push({ path: "/error" });
        }
      })
      .catch((error) => {
        this.$router.push({ path: "/error" });
      });
  },
};
</script>

<style>
</style>
