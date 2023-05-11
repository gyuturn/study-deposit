<template>
  <div>
    <!-- 상단 동그란 박스 -->
    <v-row justify="center" class="mb-8">
      <v-col cols="12" sm="8" md="6">
        <v-card class="text-center bg">
          <v-title class="title" md="6">출석현황</v-title>

          <div style="display: flex; align-items: center; margin-top: 10px;">
            <div style="margin-right: 20px;">
              <img src="@/assets/attendance_check.png" class="attendance_img"/>
            </div>
            <div style="flex: 1; text-align: left;">
              <div class="label" >사용자 이름</div>
              <div class="label">결석일수: x일</div>
            </div>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "NowAttendance",
  methods: {},
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

.label {
  text-align: left;
}

.attendance_img {
    margin:20px;
}
</style>
