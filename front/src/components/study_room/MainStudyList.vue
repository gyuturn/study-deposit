<template>
  <v-container fluid class="fill-height justify-center align-center">
    <v-card elevation="0">
      <v-card-title
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
        >현재 스터디방</v-card-title
      >
      <v-card-text
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
      >
        <div v-for="room in studyRooms" :key="room">
          <StudyRoomInfo :studyRoom="room" @click="openModal(room)" />
        </div>
      </v-card-text>
    </v-card>
    <v-dialog v-model="isModalOpen" max-width="500px">
      <v-card>
        <v-card-title>{{ selectedStudyRoom.title }} </v-card-title>
        <v-card-text>
          <div>
            기간: {{ selectedStudyRoom.startDate }} ~{{
              selectedStudyRoom.endDate
            }}
          </div>
          <div>필요 보증금: {{ selectedStudyRoom.deposit }}원</div>
          <div>현재 가지고 있는 보증금: {{ usersPoint }}원</div>
          <div v-if="insufficientPoints" style="color: red;">포인트가 부족합니다!</div>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" text @click="enterRoom">입장하기</v-btn>
          <v-btn color="primary" text @click="closeModal">돌아가기</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import axios from "axios";
import StudyRoomInfo from "./StudyRoomInfo.vue";

export default {
  name: "MainStudyList",
  components: {
    StudyRoomInfo,
  },
  data() {
    return {
      studyRooms: [], // Initialize an empty array to store study room data
      selectedStudyRoom: null, // Track the selected study room
      isModalOpen: false, // Track whether the modal is open or not
      usersPoint: 0,
      insufficientPoints: false,
    };
  },
  mounted() {
    // Fetch study room data from the backend API
    // You can use axios or any other HTTP library for this
    // Here's an example using axios
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
    getUserPoint() {
      axios
        .get(`${import.meta.env.VITE_API_URI}/users`, {
          withCredentials: true, // Include cookies in the request
        })
        .then((response) => {
          if (response.status === 200) {
            this.usersPoint = response.data.data.sumOfChargeAmount;
          }
        })
        .catch((error) => {
          //  로그인 안되어 있음
          this.$router.push({ path: "/login/kakao" });
        });
    },
    openModal(studyRoom) {
      this.selectedStudyRoom = studyRoom;
      this.getUserPoint();
      this.isModalOpen = true;
    },
    closeModal() {
      this.isModalOpen = false;
    },
    enterRoom() {
      if (this.usersPoint >= this.selectedStudyRoom.deposit) {
        axios({
        method: "post", // [요청 타입]
        url: `${import.meta.env.VITE_API_URI}/studyroom/enter`, // [요청 주소]
        data: JSON.stringify({
          id:this.selectedStudyRoom.id
        }), // [요청 데이터]

        headers: {
          "Content-Type": "application/json; charset=utf-8",
        },
        withCredentials: true,
      })
        .then((response) => {
          if (response.status === 200) {
            this.$router.push({ path: "/studyroom/attendance/" + this.selectedStudyRoom.id });
          } else {
            this.$router.push({ path: "/error" });
          }
        })
        .catch(function (error) {
          this.$router.push({ path: "/error" });
        });
      }else{
        this.insufficientPoints=true;
      }
    },
  },
};
</script>

<style>
.v-card__title {
  margin-top: 0;
}
</style>
