<template>
  <div>
    <!-- 상단 동그란 박스 -->
    <v-row justify="center" class="mb-8">
      <v-col cols="12" sm="8" md="6">
        <v-card class="text-center bg">
          <v-title class="title" md="6">출석현황</v-title>

          <div
            v-for="info in attendanceInfo"
            :key="info"
            style="display: flex; align-items: center; margin-top: 10px"
          >
            <div v-if="info.todaysAttendance === true" style="margin-right: 20px">
              <img src="@/assets/attendance_check.png" class="attendance_img" />
            </div>
            <div  v-else-if="info.todaysAttendance === false" style="margin-right: 20px">
              <img src="@/assets/not_attendance_check.png" class="attendance_img" />
            </div>
            <div style="flex: 1; text-align: left">
              <div class="label">{{ info.usersNickName }}</div>
              <div class="label" style="color: red">
                결석일수: {{info.absenceDay}}일
              </div>
              <div class="label">
                입장일로부터 필요 출석수: {{info.totalAttendanceDay}}일
              </div>
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
  data() {
    return {
      attendanceInfo: [],
    };
  },
  methods: {
    getStudyRoomId() {
      const currentUri = this.$route.path;
      const parts = currentUri.split("/");
      const lastPart = parts[parts.length - 1];
      const studyRoomId = parseInt(lastPart);
      return studyRoomId;
    },
  },
  mounted() {
    // Make the API request
    axios({
      method: "get", // [요청 타입]
      url: `${
        import.meta.env.VITE_API_URI
      }/studyroom/attendance/list/${this.getStudyRoomId()}`, // [요청 주소]
      headers: {
        "Content-Type": "application/json; charset=utf-8",
      },
      withCredentials: true,
    })
      .then((response) => {
        if (response.status === 200) {
          this.attendanceInfo = response.data.data.attendanceInfo;
        } else {
          this.$router.push({ path: "/login/kakao" });
        }
      })
      .catch(function (error) {
        this.$router.push({ path: "/login/kakao" });
      });
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

.label {
  text-align: left;
}

.attendance_img {
  margin: 20px;
}
</style>
