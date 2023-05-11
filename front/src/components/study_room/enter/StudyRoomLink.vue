<template>
  <div>
    <!-- 상단 동그란 박스 -->
    <v-row justify="center" class="mb-8">
      <v-col cols="12" sm="8" md="6">
        <v-card class="text-center bg">
          <v-title class="title"> 스터디방 제목 </v-title>
          <v-card-actions class="link-btn">
            <!-- 출석하기 링크 -->
            <v-btn class="link" @click="goTo('attendance')">출석하기</v-btn>
            <!-- 방정보 링크 -->
            <v-btn class="link" @click="goTo('info')">방정보</v-btn>
            <!-- 채팅 링크 -->
            <v-btn class="link" @click="goTo('chat')">채팅</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "StudyRoomLink",
  methods: {
    goTo(route) {
      const currentUri = this.$route.path;
      const parts = currentUri.split("/");
      const lastPart = parts[parts.length - 1];
      const number = parseInt(lastPart);
      // 각 링크 클릭 시 실행될 동작을 추가하세요.
      switch (route) {
        case "attendance":
          this.$router.push({ path: "/studyroom/attendance/" + number });
          break;
        case "room-info":
          this.$router.push({ path: "/studyroom/info/" + number });
          break;
        case "chat":
          this.$router.push({ path: "/studyroom/chat/" + +number });
          break;
        default:
          break;
      }
    },
  },
};
</script>
<style>
.link {
  background-color: #006ffd;
  color: white;
  border-radius: 30px;
  margin: 10px;
}
.link-btn {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>