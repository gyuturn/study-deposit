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
        현재 닉네임은 {{ nickname }} 입니다. <br /><br />닉네임을 변경해보시는
        건 어떠세요?
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
            <v-btn  @click="updateNickname">변경</v-btn>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column main-btn">
            <v-btn  @click="updateNickname">다음에 하기</v-btn>
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
      fetch("https://backend-server.com/nickname", {
        method: "PUT",
        body: JSON.stringify({ nickname: this.newNickname }),
        headers: {
          "Content-Type": "application/json",
        },
      }).then((response) => {
        if (response.ok) {
          this.$router.push("/home");
        } else {
          console.error("Failed to update nickname.");
        }
      });
    },
  },
  mounted() {
    // 서버에서 현재 닉네임을 가져옵니다.
    this.nickname = "John Doe";
  },
};
</script>

<style>


</style>
