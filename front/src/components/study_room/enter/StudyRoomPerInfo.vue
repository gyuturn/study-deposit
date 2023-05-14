<template>
  <div>
    <v-app>
      <v-main>
        <v-container fluid>
          <StudyRoomLink />
          <v-row justify="center" class="mb-8">
            <v-col cols="12" sm="8" md="6">
              <v-card class="text-center bg">
                <v-title class="title">방 정보</v-title>
                <v-text class="text-center">
                  <div class="label text-center">방제목: {{ title }}</div>
                  <div class="label text-center">
                    스터디 기간: {{ startDate }} ~{{ endDate }}
                  </div>
                  <div class="label text-center">
                    출석 시간: {{ attendanceTime }}
                  </div>
                  <div class="label text-center">
                    인원: {{ currentOccupancy }}/{{ capacity }}
                  </div>
                  <div class="label text-center" style="color: blue">
                    보증금: {{ deposit }}원
                  </div>
                  <div class="label text-center" style="color: red">
                    퇴출조건: 5일이상
                  </div>
                </v-text>
              </v-card>
            </v-col>
          </v-row>
          <v-row justify="center" class="mb-8">
            <v-col cols="12" sm="8" md="6">
              <v-card class="text-center bg">
                <v-title class="title">보증금</v-title>
                <v-text class="text-center">
                  <div class="label text-center">방제목: {{ title }}</div>
                  <div class="label text-center">
                    현재 모인 보증금: {{ totalDeposit }}원
                  </div>
                  <div class="label text-center">
                    * 보증금은 추후 스터디를 완료하면 받을 수 있습니다.
                  </div>
                  <div class="label text-center">
                    * 만약 이탈시에 해당 보증금은 팀원+개발자/n 으로 분배됩니다.
                  </div>
                </v-text>
              </v-card>
            </v-col>
          </v-row>
          <Link />
        </v-container>
      </v-main>
    </v-app>
  </div>
</template>

<script>
import axios from "axios";
import StudyRoomLink from "./StudyRoomLink.vue";
import Link from "../../Link.vue";
export default {
  name: "StudyRoomPerInfo",
  components: {
    StudyRoomLink,
    Link,
  },
  data() {
    return {
      title: "",
      hashTags: [],
      startDate: "",
      endDate: "",
      attendanceTime: "",
      capacity: 0,
      currentOccupancy: 0,
      deposit: 0,
      totalDeposit: 0,
    };
  },
  mounted() {
    axios({
      method: "get", // [요청 타입]
      url: `${
        import.meta.env.VITE_API_URI
      }/studyroom/info/${this.getStudyRoomId()}`, // [요청 주소]
      headers: {
        "Content-Type": "application/json; charset=utf-8",
      },
      withCredentials: true,
    })
      .then((response) => {
        if (response.status === 200) {
          this.title = response.data.data.title;
          this.hashTags = response.data.data.hashTags;
          this.startDate = response.data.data.startDate;
          this.endDate = response.data.data.endDate;
          this.attendanceTime = response.data.data.attendanceTime;
          this.capacity = response.data.data.capacity;
          this.currentOccupancy = response.data.data.currentOccupancy;
          this.deposit = response.data.data.deposit;
          this.totalDeposit = this.deposit * this.currentOccupancy;
        } else {
          this.$router.push({ path: "/login/kakao" });
        }
      })
      .catch(function (error) {
        this.$router.push({ path: "/login/kakao" });
      });
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
