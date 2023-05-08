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
          <StudyRoomInfo :studyRoom="room" />
        </div>
      </v-card-text>

    </v-card>
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
      studyRooms: [] // Initialize an empty array to store study room data
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
            this.studyRooms=response.data.data;
            console.log(this.studyRooms);
          } else {
            this.$router.push({ path: "/error" });
          }
        })
        .catch(function (error) {
          this.$router.push({ path: "/error" });
        });
  }
};
</script>

<style>
.v-card__title {
  margin-top: 0;
}
</style>
