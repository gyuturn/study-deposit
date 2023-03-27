<template>
  <v-container fluid class="fill-height justify-center align-center">
    <v-card elevation="0"  height="100%" width="100%">
      <v-card-title
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
        >나의 프로필</v-card-title
      >
      <v-card-text>
        <v-list align="center">
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>
                <img src="@/assets/person.png" />
              </v-list-item-title>
              <v-list-item-subtitle class="emphasized-text"
                >이름</v-list-item-subtitle
              >
              <v-list-item-subtitle class="emphasized-text"
                >포인트: 3000P</v-list-item-subtitle
              >
              <v-list-item-subtitle>kksy2423@naver.com</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <v-list>
          <v-list-item v-for="(item, index) in listItems" :key="index">
            <v-list-item-content>
              <div class="d-flex align-center justify-space-between">
                <v-list-item-title>
                  {{ item.title }}
                </v-list-item-title>
                <img src="@/assets/right_btn.png" />
              </div>
              <v-divider></v-divider>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "MypageHome",
  methods: {},
  data() {
    return {
      listItems: [
        { title: "회원정보 수정" },
        { title: "스터디방 관리" },
        { title: "포인트 충전하기" },
        { title: "포인트 환전하기" },
        { title: "로그아웃" },
      ],
    };
  },
};
</script>

<style>
.emphasized-text {
  font-weight: bold;
  color: black;
  margin: 10px;
}
</style>
