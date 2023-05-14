<template>
  <v-container fluid class="fill-height justify-center align-center">
    <v-card elevation="0">
      <v-card-title
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
        >나의 스터디방</v-card-title
      >
      <v-card-text
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
      >
        <div v-for="room in studyRooms" :key="room">
          <StudyRoomInfo :studyRoom="room" @click="enterRoom(room.id)" />
        </div>
      </v-card-text>
    </v-card>
    <Link />
  </v-container>
</template>

<script>
import axios from "axios";
import StudyRoomInfo from "../study_room/StudyRoomInfo.vue";
import Link from "../Link.vue";
export default {
  name: "MyStudyRooms",
  components: {
    StudyRoomInfo,
    Link,
  },
  data() {
    return {
      studyRooms: [], // Initialize an empty array to store study room data
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
        } else {
          this.$router.push({ path: "/error" });
        }
      })
      .catch(function (error) {
        this.$router.push({ path: "/error" });
      });
  },
  methods: {
    enterRoom(studyRoomId) {
      this.$router.push({
                path: "/studyroom/attendance/" + studyRoomId,
              });
    },
  },
};
</script>

<style>
.v-card__title {
  margin-top: 0;
}
</style>
