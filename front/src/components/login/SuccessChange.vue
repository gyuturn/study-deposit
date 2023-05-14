<template>
  <v-container fluid class="fill-height justify-center align-center">
    <v-card elevation="0">
      <v-card-title
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
        >변경완료 되었어요!!</v-card-title
      >
      <v-card-actions>
        <v-row class="align-items-center justify-center">
          <v-col cols="12" md="6" class="text-center flex-column main-btn">
            <v-btn @click="gohome">홈으로 가기</v-btn>
          </v-col>
        </v-row>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>

export default {
  name: "SuccessChange",
  methods: {
    gohome() {
      this.$router.push({ path: "/studyroom/list" });
    },
  },
};
</script>

<style>
</style>