<template>
    <v-container fluid class="fill-height">
      <v-row class="align-items-center justify-center">
        <v-col cols="12" md="6" class="text-center flex-column">
          <div class="text-center mb-3 font-weight-bold text--primary text--body-2">
            카카오 계정으로 <br> 간편하게 회원가입 할 수 있어요!
          </div>
        </v-col>
        <v-col cols="12" md="6" class="text-center flex-column">
          <img src="@/assets/kakao_login.png" alt="kakaoLogin" @click="handleImageClick" class ="mx-auto"/>
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
export default {
  name: "KakaoLogin",
  methods: {
    handleImageClick() {
      // Handle the image click event here
      console.log("Image clicked");
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
