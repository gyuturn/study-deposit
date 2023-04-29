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
              <input type="time" id="attendance-time" v-model="selectedTime"  @change='changeTimeFormat'/>
            </div>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column">
            <v-text-field type="date" label="스터디 종료 날짜" :min="minDate" v-model="endDate"></v-text-field>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column">
            <v-text-field v-model="personCapacity" label="스터디 인원" type="number" min="0" :rules="[positiveRule]"></v-text-field>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column">
            <v-text-field v-model="deposit" label="보증금" type="number" min="0" :rules="[positiveRule]" ></v-text-field>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column"> 
            <HashTag />
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column main-btn">
            <v-btn @click="updateNickname">변경</v-btn>
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
import HashTag from './HashTag.vue';

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
      positiveRule: val => val >= 0 || '숫자는 양수여야 합니다.',
      deposit: 0,
      attendanceType: { text: "시간 내 출석체크 ", value: "AttendanceCheck" },
      attendanceTypes: [{ text: "시간 내 출석체크", value: "AttendanceCheck" }],
      title: "",
      selectedTime: '',
      attendanceTime: {
        hour: "",
        minute: "",
        second: "",
      },
    };
  },
  methods: {
    changeTimeFormat() {
      const timeObj = new Date(`2000-01-01T${this.selectedTime}:00`);
      this.attendanceTime.hour = timeObj.getHours();
      this.attendanceTime.minute = timeObj.getMinutes();
      this.attendanceTime.second = timeObj.getSeconds();
      
      console.log(this.attendanceTime)
    }
  },

};
</script>

<style>
</style>
