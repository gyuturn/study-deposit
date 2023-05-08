<template>
  <v-container fluid>
    <v-card elevation="0" class="study-room-card">
      <v-card-title class="text-center font-weight-medium text--primary">
        {{ studyRoom.title }}
      </v-card-title>
      <v-card-text class="text-center  font-weight-regular text--primary">
        <div class="hash-tag-list">
          <div v-for="hashTag in studyRoom.hashTags" :key="hashTag" class="hash-tag-item">
            #{{ hashTag }}
          </div>
        </div>
        <div>기간: {{ studyRoom.startDate }} ~ {{ studyRoom.endDate }}</div>
        <div>출석시간: {{ studyRoom.attendanceTime }}</div>
        <div class="occupancy">
          <div class="occupancy_img">
            <img src="@/assets/person_removebg.png" />
            {{ studyRoom.currentOccupancy }}/{{ studyRoom.capacity }}
          </div>
          <div>보증금: {{ studyRoom.deposit }}원</div>
        </div>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "StudyRoomInfo",
  data() {
    return {};
  },
  props: {
    studyRoom: {
      type: Object,
    },
  },
};
</script>

<style scoped>
.study-room-card {
  background-color: #eaf2ff;
  border-radius: 10px;
  width: 300px;
  height: 165px;
}

.hash-tag-list {
  display: flex;
  justify-content: center;
  margin-bottom: 5px;
}

.hash-tag-item {
  border-radius: 5px;
  margin-bottom: 8px;
  margin-right: 8px;
  color:gray
}

.occupancy {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-top: 8px;
}

.occupancy_img {
  display: flex;
  align-items: center;
}

.occupancy img {
  width: 16px;
  height: 16px;
  margin-right: 8px;
}

.text--primary {
  font-size: 14px;
  /* Adjust the font size to your desired value */
}

.font-weight-medium {
  font-weight: normal;
}

.font-weight-regular {
  font-weight: normal;
}
</style>
