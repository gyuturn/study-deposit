<template>
  <div class="not-ready-page">
    <h1>죄송합니다</h1>
    <h3>아직 준비중인 서비스 입니다.</h3>
    <v-icon class="icon" size="128">mdi-emoticon-sad</v-icon>
    <v-btn class="main-btn" @click="goToHome">홈으로 가기</v-btn>
  </div>
</template>

<script>
export default {
  name: "NotReady",
  methods: {
    goToHome() {
      this.$router.push("/studyroom/list");
    },
  },
};
</script>

<style scoped>
.not-ready-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

.icon {
  margin-bottom: 24px;
}
</style>
