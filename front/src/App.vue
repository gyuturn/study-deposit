<template>
  <v-app>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>

export default {
  name: 'App',

  components: {
  },

  data: () => ({
    //
  }),
}
</script>


<style>
.main-btn{
  background-color: #006FFD;
  border-radius: 12px;
  color: #FFFFFF;
  width: 327px;
  height: 48px;
  margin: 10px;
}
.center-content {
  margin: auto;
}
@media (max-width: 600px) {
  .center-content {
    width: 100%;
    height: 100%;
    margin: auto;
  }
}
</style>
