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
  /* 반응형 최대크기
  app { margin:0 auto; width:750px; min-height:100%; padding-top:71px; border-left:1px #D8D8D8 solid; border-right:1px #D8D8D8 solid; positon:relative; text-align:center; }

  /* 여백제거 + 드래그방지 */
  /* body{ padding:0; margin:0; -ms-user-select: none; -moz-user-select: none; -khtml-user-select: none; -webkit-user-select: none; user-select: none; } */

  /* 반응형 시작 */
  /* @media(max-width:750px){
    #app{ width:100%; border-left:0; border-right:0; }
  }   */
</style>
