<template>
  <v-container fluid class="fill-height justify-center align-center">
    <v-card elevation="0" width="100%">
      <v-card-title
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
      >
        결제가 완료 되었어요!
      </v-card-title>
      <v-card-text
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
      >
        결제된 금액: {{chargeAmount}}
      </v-card-text>
      <v-card-actions>
        <v-row class="align-items-center justify-center">
          <v-col cols="12" md="6" class="text-center flex-column main-btn">
            <v-btn @click="gohome">홈으로 가기</v-btn>
          </v-col>
        </v-row>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import axios from "axios";
export default {
  name: "PaymentResult",
  data() {
    return {
      chargeAmount: "",
      status: "",
    };
  },
  methods: {
    gohome() {
      this.$router.push({ path: "/mypage/home" });
    },
  },
  mounted() {
  setTimeout(() => {
    // 서버에서 결제 결과를 호출합니다.
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const imp_uid = urlParams.get('imp_uid');
    axios
      .get(`${import.meta.env.VITE_API_URI}/point/record/kakaopay/payment/result?imp_uid=${imp_uid}`, {
        withCredentials: true, // Include cookies in the request
      })
      .then((response) => {
        if (response.status === 200) {
            if(response.data.data.status=='cancelled'){
                this.$router.push({ path: "/error" });
            }else{
                this.chargeAmount=`${response.data.data.chargeAmount}원`;
            }
        } 
      })
      .catch((error) => {
        this.$router.push({ path: "/error" });
      });
  }, 1000);
},
};
</script>