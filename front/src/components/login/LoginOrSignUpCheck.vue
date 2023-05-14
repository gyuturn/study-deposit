<template></template>

<script>
import axios from "axios";

export default {
  name: "LoginOrSignUpCheck",
  methods: {},
  mounted() {
    // 백엔드 서버로 요청 보내기
    axios
      .get(`${import.meta.env.VITE_API_URI}/users/nickname/check`, {
        withCredentials: true, // Include cookies in the request
      })
      .then((response) => {
        if (response.status === 200) {
          // 이미 변경한 이력이 있는경우 홈으로
          this.$router.push({ path: "/mypage/home" });
        } else if (response.status === 202) {
          //  닉네임 변경 요청 요망
          this.$router.push({ path: "/login/enter/nickname" });
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
