<template>
  <div>
          <!-- 상단 동그란 박스 -->
          <v-row justify="center" class="mb-4">
            <v-col cols="12" sm="8" md="6">
              <v-card class="text-center">
                <v-title>
                    스터디방 제목
                </v-title>
                <v-card-actions>
                   <!-- 출석하기 링크 -->
                  <v-btn  class="link" @click="goTo('attendance')">출석하기</v-btn>
                  <!-- 방정보 링크 -->
                  <v-btn  class="link" @click="goTo('room-info')">방정보</v-btn>
                  <!-- 채팅 링크 -->
                  <v-btn  class="link" @click="goTo('chat')">채팅</v-btn>
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
}
</script>
<style>
.link {
  background-color: #006FFD;
  color:white;
  border-radius: 30px;
}
</style>