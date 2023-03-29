<template>
  <v-container fluid class="fill-height justify-center align-center">
    <v-card elevation="0" width="100%">
      <v-card-title
        class="text-center mb-3 font-weight-bold text--primary text--body-2"
      >
        얼마를 결제하시겠어요?
      </v-card-title>
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
              :error-messages="chargeAmountErrorMessage"
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
import axios from "axios";
export default {
  name: "KakaoPayment",
  data() {
    return {
      chargeAmount: "",
    };
  },
  computed: {
    chargeAmountErrorMessage() {
      if (this.chargeAmount === "") {
        return "충전금액을 입력해주세요";
      }
      if (!/^[1-9][0-9]*$/.test(this.chargeAmount)) {
        return "충전금액은 양의 정수만 입력 가능합니다";
      }
      return "";
    },
  },
  methods: {
    //사전 결제 백엔드에 요청
    preparePay: function (data) {
      axios({
        method: "post", // [요청 타입]
        url: `${
          import.meta.env.VITE_API_URI
        }/point/record/kakaopay/payment/prepare`, // [요청 주소]
        data: data, // [요청 데이터]
        headers: {
          "Content-Type": "application/json; charset=utf-8",
        },
        withCredentials: true,
      })
        .then((response) => {})
        .catch(function (error) {
          this.$router.push({ path: "/error" });
        });
    },
     //주문번호 만들기
    createOrderNum: function () {
      const date = new Date();
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");

      let orderNum = year + month + day;
      for (let i = 0; i < 10; i++) {
        orderNum += Math.floor(Math.random() * 8);
      }
      return orderNum;
    },
    requestPay: function () {
      const data = {
        pg: "kakaopay",
        pay_method: "card",
        merchant_uid: this.createOrderNum(),
        name: "스터디보증 포인트 구매",
        amount: this.chargeAmount,
        buyer_email: this.email,
        buyer_name: this.nickname,
        m_redirect_url: "localhost:5173",
      };

      this.preparePay(data);

      var IMP = window.IMP; // 생략 가능
      IMP.init("imp47202403"); // 예: imp00000000
      // IMP.request_pay(param, callback) 결제창 호출
      IMP.request_pay(
        {
          // param
          pg: data.pg,
          pay_method: data.pay_method,
          merchant_uid: data.merchant_uid,
          name: data.name,
          amount: data.amount,
          buyer_email: data.buyer_email,
          buyer_name: data.buyer_name,
          m_redirect_url:data.m_redirect_url,
        },
        (rsp) => {
          // callback ,callback은 pc인경우 m_redirect_url은 모바일인경우
          if (rsp.success) {
            data.impUid = res.imp_uid;
            data.merchant_uid = rsp.merchant_uid;
            this.paymentComplete(data);
            // 결제 성공 시 로직,
          } else {
            console.log(rep);
            // 결제 실패 시 로직,
            this.$router.push({ path: "/error" });
          }
        }
      );
    },

   

    // 계산 완료
    paymentComplete: function (data) {
      axios({
        method: "post", // [요청 타입]
        url: `${import.meta.env.VITE_API_URI}/point/record`, // [요청 주소]
        data: JSON.stringify(data), // [요청 데이터]
        headers: {
          "Content-Type": "application/json; charset=utf-8",
        },
        withCredentials: true,
      })
        .then((response) => {
          if (response.status === 200) {
            this.$router.push({ path: "/mypage/home" });
          }
        })
        .catch(function (error) {
          this.$router.push({ path: "/error" });
        });
    },
  },
};
</script>

<style></style>
