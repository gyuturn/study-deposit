<template>
  <div class="position-relative">
    <div class="position-absolute top-50 start-50 translate-middle">
      <div>카카오 계정으로 '\n'간편하게 회원가입 할 수 있어요!</div>
      <button @click="loginWithGoogle" class="btn btn-primary">
        <i class="fab fa-google"></i> Login with Google
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "KakaoLogin",
};
</script>

<style>
</style>