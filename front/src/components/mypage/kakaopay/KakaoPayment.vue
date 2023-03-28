<template>
  <v-container fluid class="fill-height justify-center align-center">
    <v-card elevation="0" width="100%">
      <v-card-title
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
        >얼마를 결제하시겠어요?</v-card-title
      >
      <v-card-text
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
      >
        1P = 1원(ex.1000P=1000원)
      </v-card-text>
      <v-card-actions>
        <v-row class="align-items-center justify-center">
          <v-col cols="12" md="6" class="text-center flex-column">
            <v-text-field
              v-model="chargeAmount"
              label="충전금액"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="6" class="text-center flex-column main-btn">
            <v-btn @click="requestPay">결제 요청하기</v-btn>
          </v-col>
        </v-row>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "KakaoPayment",
  data() {
    return {
      chargeAmount: "",
    };
  },
  methods: {
    requestPay: function () {
      if (
        !(/^\d+$/.test(this.chargeAmount) && parseInt(this.chargeAmount) > 0)
      ) {
        alert("충전금액을 양의 정수로 입력해주세요.");
        return;
      }

      var IMP = window.IMP; // 생략 가능
      IMP.init("imp47202403"); // 예: imp00000000
      // IMP.request_pay(param, callback) 결제창 호출
      IMP.request_pay(
        {
          // param
          pg: "kakaopay",
          pay_method: "card",
          merchant_uid: "merchant_" + new Date().getTime(),
          name: "스터디보증 포인트 구매",
          amount: this.chargeAmount,
          buyer_email: this.email,
          buyer_name: this.nickname,
          m_redirect_url: "localhost:5173",
        },
        (rsp) => {
          // callback ,callback은 pc인경우 m_redirect_url은 모바일인경우
          if (rsp.success) {
            console.log(rsp.success);
            console.log(rsp);
            // 결제 성공 시 로직,
          } else {
            console.log(rep);
            // 결제 실패 시 로직,
            console.log("실패");
          }
        }
      );
    },
  },
};
</script>

<style>
</style>