<template>
  <div class="bottom-navigation">
    <div class="nav-item" @click="navigateTo('/studyroom/list')">
      <img src="@/assets/Search.png" class="nav-icon" />
      <div class="nav-text">스터디방 검색</div>
    </div>
    <div class="nav-item" @click="navigateTo('/mypage/home')">
      <img src="@/assets/Profile.png" class="nav-icon" />
      <div class="nav-text">마이페이지</div>
    </div>
    <div class="nav-item" @click="navigateTo('/studyroom/post')">
      <img src="@/assets/Edit.png" class="nav-icon" @click="navigateTo('/studyroom/list')" />
      <div class="nav-text">스터디방 생성</div>
    </div>
  </div>
</template>


<script>
export default {
  name: "Link",
  data() {
    return {};
  },

  methods: {
    navigateTo(link) {
      // Perform the navigation to the specified link
      // You can use Vue Router or any other navigation mechanism here
      // For example, using Vue Router:
      this.$router.push(link);
    },
  },
};
</script>

<style scoped>
.bottom-navigation {
  position: fixed;
  bottom: 0px;
  left: 0;
  right: 0;
  height: 77px; /* Adjust the height as needed */
  background-color: #fff; /* Set the desired background color */
  display: flex;
  justify-content: space-around;
  align-items: center;
  box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.1); /* Add a box shadow if desired */
}

.nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  cursor: pointer;
}

.nav-icon {
  height: 20px; /* Adjust the height as needed */
  width: auto; /* Allow the width to adjust proportionally */
  max-width: 100%; /* Ensure the image does not exceed the container's width */
  margin-bottom: 4px; /* Adjust the margin as needed */
}

.nav-text {
  font-size: 12px; /* Adjust the font size as needed */
}
</style>
