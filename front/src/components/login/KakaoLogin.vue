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
<<<<<<< Updated upstream
    handleImageClick() {
      // Handle the image click event here
      console.log("Image clicked");
      console.log("Image clicked");
=======
    enterKaKaoLogin() {
      //springsecurity oauth2 clinet redirect uri
      window.location.href = "https://accounts.kakao.com/login/?continue=https%3A%2F%2Fkauth.kakao.com%2Foauth%2Fauthorize%3Fscope%3Dprofile_nickname%2520account_email%26response_type%3Dcode%26state%3DcpAoKmo-EtN9zcfPFG3qOf7aD8WYecXywyHJ3zuilrw%253D%26redirect_uri%3Dhttp%253A%252F%252Flocalhost%253A8080%252Flogin%252Foauth2%252Fcode%252Fkakao%26through_account%3Dtrue%26client_id%3D7ee2b750676219c7f2c7d4f1b574bf4d#login";
>>>>>>> Stashed changes
    },
  },
};
</script>

<style>
.center-content {
  margin: auto ;
}
@media (max-width: 600px) {
  .center-content {
    width: 100% ;
    height: 100% ;
    margin: auto 
  }
}
 

</style>
