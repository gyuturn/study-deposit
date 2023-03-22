<template>
    <v-container fluid class="fill-height">
      <v-row class="align-items-center justify-center">
        <v-col cols="12" md="6" class="text-center flex-column">
          <div class="text-center mb-3 font-weight-bold text--primary text--body-2">
            카카오 계정으로 <br> 간편하게 회원가입 할 수 있어요!
          </div>
        </v-col>
        <v-col cols="12" md="6" class="text-center flex-column">
          <img src="@/assets/kakao_login.png" alt="kakaoLogin" @click="enterKaKaoLogin" class ="mx-auto"/>
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
export default {
  name: "KakaoLogin",
  methods: {
    enterKaKaoLogin() {
      //springsecurity oauth2 clinet redirect uri
      window.location.href = `${import.meta.env.VITE_KAKAO_URI}`
    },
  },
};
</script>

<style>
 

</style>
