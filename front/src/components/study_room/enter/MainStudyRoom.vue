<template>
  <div>
    <v-app>
      <v-main>
        <v-container fluid>
          <StudyRoomLink />
          <v-row justify="center" class="mb-8">
            <v-col cols="12" sm="8" md="6">
              <v-card class="text-center bg">
                <v-title class="title">출석하기</v-title>
                <v-text class="text-center">
                  <div class="label text-center" >현재시간: {{ currentTime }}</div>
                  <div class="label text-center">출석시간: 02:00 ~ 02:05</div>
                </v-text>
                <v-action>
                  <v-btn class="btn" @click="goTo('chat')">출석하기</v-btn>
                </v-action>
              </v-card>
            </v-col>
          </v-row>
          <NowAttendance />
          <v-row justify="center" class="mb-8">
            <v-col cols="12" sm="8" md="6">
              <v-card class="text-center bg">
                <v-title class="title">출석제한</v-title>
                <v-text class="text-center">
                  <div class="label text-center" >스터디 기간의 1/3 이상 결석 시 자동 퇴출</div>
                  <div class="label text-center">현재방 퇴출 기간:5일</div>
                </v-text>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-main>
    </v-app>
  </div>
</template>

<script>
import axios from "axios";
import StudyRoomLink from "./StudyRoomLink.vue";
import NowAttendance from "./NowAttendance.vue"

export default {
  name: "MainStudyRoom",
  components: {
    StudyRoomLink,
    NowAttendance
  },
  data() {
    return {
      currentTime: "", // Variable to hold the current time
    };
  },
  mounted() {
    // Fetch the current time
    this.getCurrentTime();

    // Update the current time every second
    setInterval(this.getCurrentTime, 1000);

    // Make the API request
    axios({
      method: "get", // [요청 타입]
      url: `${import.meta.env.VITE_API_URI}/studyroom`, // [요청 주소]
      headers: {
        "Content-Type": "application/json; charset=utf-8",
      },
      withCredentials: true,
    })
      .then((response) => {
        if (response.status === 200) {
          this.studyRooms = response.data.data;
          console.log(this.studyRooms);
        } else {
          this.$router.push({ path: "/error" });
        }
      })
      .catch(function (error) {
        this.$router.push({ path: "/error" });
      });
  },
  methods: {
    getCurrentTime() {
      const now = new Date();
      const options = {
        timeZone: "Asia/Seoul", // Set the time zone to Seoul
        hour12: false, // Use 24-hour format
      };
      this.currentTime = now.toLocaleTimeString("en-US", options);
    },
  },
};
</script>

<style>
.link {
  background-color: #006ffd;
  color: white;
  border-radius: 30px;
}
.btn {
  background-color: #006ffd;
  color: white;
  border-radius: 30px;
  margin: 10px;
  width: 80%;
}
.title {
  font-weight: bold;
  font-size: 18px;
}

.label {
  font-weight: bold;
  font-size: 16px;
  margin: 4px;
}

.bg {
    background-color: #F8F9FE;
}


</style>
