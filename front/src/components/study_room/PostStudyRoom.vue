<template>
  <v-container fluid class="fill-height justify-center align-center">
    <v-card elevation="0">
      <v-card-title
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
        >스터디방 생성</v-card-title
      >
      <v-card-text
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
      >
      </v-card-text>
      <v-card-actions>
        <v-row class="align-items-center justify-center">
          <v-col cols="12" md="6" class="text-center flex-column">
            <v-text-field
              v-model="title"
              label="스터디방 제목을 입력해주세요"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column">
            <v-select
              v-model="attendanceType.text"
              :items="attendanceTypes"
              item-text="text"
              label="출석체크 방법 선택"
            ></v-select>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column">
            <div>
              <label for="attendance-time">출석 시간:</label>
              <input
                type="time"
                id="attendance-time"
                v-model="selectedTime"
                @change="changeTimeFormat"
              />
            </div>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column">
            <v-text-field
              type="date"
              label="스터디 종료 날짜"
              :min="minDate"
              v-model="endDate"
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column">
            <v-text-field
              v-model="personCapacity"
              label="스터디 인원"
              type="number"
              min="0"
              :rules="[positiveRule]"
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column">
            <v-text-field
              v-model="deposit"
              label="보증금"
              type="number"
              min="0"
              :rules="[positiveRule]"
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column">
            <HashTag :myTags="hashTags" @updateHashTags="updateHashTags" />
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column main-btn">
            <v-btn @click="post">등록하기</v-btn>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column main-btn">
            <v-btn @click="goHome">다음에 하기</v-btn>
          </v-col>
        </v-row>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import axios from "axios";
import HashTag from "./HashTag.vue";

export default {
  name: "PostStudyRoom",
  components: {
    HashTag,
  },
  data() {
    return {
      minDate: new Date().toISOString().substr(0, 10),
      endDate: new Date().toISOString().substr(0, 10),
      personCapacity: 0,
      positiveRule: (val) => val >= 0 || "숫자는 양수여야 합니다.",
      deposit: 0,
      attendanceType: { text: "시간 내 출석체크 ", value: "AttendanceCheck" },
      attendanceTypes: [{ text: "시간 내 출석체크", value: "AttendanceCheck" }],
      title: "",
      selectedTime: "",
      attendanceTime: "",
      hashTags: [],
    };
  },
  methods: {
    changeTimeFormat() {
      const timeObj = new Date(`2000-01-01T${this.selectedTime}:00`);
      const hours = String(timeObj.getHours()).padStart(2, "0");
      const minutes = String(timeObj.getMinutes()).padStart(2, "0");
      const seconds = String(timeObj.getSeconds()).padStart(2, "0");
      this.attendanceTime = `${hours}:${minutes}:${seconds}`;
    },
    updateHashTags(data) {
      this.hashTags = data;
    },
    goHome() {
      this.$router.push({ path: "/studyroom" });
    },
    post() {
      // 서버에 스터디방 데이터를 보내고, 201  받으면 리다이렉트합니다.
      axios({
        method: "post", // [요청 타입]
        url: `${import.meta.env.VITE_API_URI}/studyroom`, // [요청 주소]
        data: JSON.stringify({
          title: this.title,
          attendanceType: "AttendanceCheck",
          attendanceTime: this.attendanceTime,
          endDate: this.endDate,
          personCapacity: this.personCapacity,
          deposit: this.deposit,
          hashTags: this.hashTags
        }), // [요청 데이터]

        headers: {
          "Content-Type": "application/json; charset=utf-8",
        },
        withCredentials: true,
      })
        .then((response) => {
          if (response.status === 201) {
            this.$router.push({ path: "/studyroom/list" });
          } else {
            this.$router.push({ path: "/error" });
          }
        })
        .catch(function (error) {
          this.$router.push({ path: "/error" });
        });
    },
  },
};
</script>

<style>
</style>
