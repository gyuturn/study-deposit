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
                  <div class="label text-center">
                    현재시간: {{ currentTime }}
                  </div>
                  <div class="label text-center">출석시간: 02:00 ~ 02:05</div>
                </v-text>
                <v-action>
                  <v-btn class="btn" @click="sendAttendance">출석하기</v-btn>
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
                  <div class="label text-center">
                    스터디 기간의 1/3 이상 결석 시 자동 퇴출
                  </div>
                  <div class="label text-center">현재방 퇴출 기간:5일</div>
                </v-text>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-main>
    </v-app>
    <v-dialog v-model="attendanceModal" max-width="500px">
      <v-card>
        <v-card-title>
          <span v-if="attendanceStatus === 201">정상 출석</span>
          <span v-else-if="attendanceStatus === 202">지각 출석</span>
          <span v-else-if="attendanceStatus === 204">출석시간 아님</span>
          <span v-else-if="attendanceStatus === 409">이미 출석 완료</span>
        </v-card-title>
        <v-card-text>
          <span v-if="attendanceStatus === 201"
            >출석이 정상적으로 처리되었습니다.</span
          >
          <span v-else-if="attendanceStatus === 202">지각 출석되었습니다.</span>
           <span v-else-if="attendanceStatus === 204">아직 출석시간이 아닙니다.</span>
          <span v-else-if="attendanceStatus === 409">이미 출석되었습니다.</span>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" text @click="attendanceModal = false"
            >확인</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import axios from "axios";
import StudyRoomLink from "./StudyRoomLink.vue";
import NowAttendance from "./NowAttendance.vue";
export default {
  name: "MainStudyRoom",
  components: {
    StudyRoomLink,
    NowAttendance,
  },
  data() {
    return {
      currentTime: "", // Variable to hold the current time
      attendanceModal: false,
      attendanceStatus: 0,
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
    sendAttendance() {
      axios({
        method: "post", // [요청 타입]
        url: `${import.meta.env.VITE_API_URI}/studyroom/attendance`, // [요청 주소]
        headers: {
          "Content-Type": "application/json; charset=utf-8",
        },
        withCredentials: true,
        data: JSON.stringify({
          studyRoomId: this.getStudyRoomId(),
          reqTime: this.getCurrentDateTime(),
        }), // [요청 데이터]
      })
        .then((response) => {
            console.log(response)
          //정상 출석
          if (response.status === 201) {
            this.attendanceStatus = 201;
          } else if (response.status === 202) {
            //지각 출석
            this.attendanceStatus = 202;
          }else if(response.status===204){
            this.attendanceStatus = 204;
          }
          this.attendanceModal = true;
        })
        .catch((error) => {
          // The request was made and the server responded with a status code
          if (error.response.status === 409) {
            // Already attended
            this.attendanceStatus = 409;
            this.attendanceModal = true;
          } else {
            this.$router.push({ path: "/error" });
          }
        });
    },
    getStudyRoomId() {
      const currentUri = this.$route.path;
      const parts = currentUri.split("/");
      const lastPart = parts[parts.length - 1];
      const studyRoomId = parseInt(lastPart);
      return studyRoomId;
    },
    getCurrentDateTime() {
      const now = new Date();
      const options = {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit",
        second: "2-digit",
        timeZone: "Asia/Seoul",
      };

      const year = now.getFullYear().toString().padStart(4, "0");
      const month = (now.getMonth() + 1).toString().padStart(2, "0");
      const day = now.getDate().toString().padStart(2, "0");
      const hour = now.getHours().toString().padStart(2, "0");
      const minute = now.getMinutes().toString().padStart(2, "0");
      const second = now.getSeconds().toString().padStart(2, "0");

      const currentDateTime = `${year}-${month}-${day}T${hour}:${minute}:${second}`;

      return currentDateTime;
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
  background-color: #f8f9fe;
}
</style>
