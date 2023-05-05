<template>
  <v-container>
    <v-text-field
      v-model="inputText"
      label="해시태그를 입력하세요"
      @input="fetchTagsDelayed"
    />
    <v-chip v-for="(tag, index) in tags" :key="index" @click="selectTag(tag)">
      {{ tag.tagName }}
    </v-chip>
    <v-btn v-if="!tags.length" color="primary" @click="createTag">
      새로 만들기
    </v-btn>
    <br />
    <br />
    <v-chip v-for="(tag, index) in myTags" :key="index" outlined color="red">
      {{ tag.tagName }}
    </v-chip>
  </v-container>
</template>

<script>
import axios from "axios";
export default {
  name: "HashTag",
  data() {
    return {
      inputText: "",
      tags: [],
      delayTimer: null,
      myTags: [],
    };
  },

  methods: {
    fetchTagsDelayed() {
      if (this.inputText && this.inputText.length >= 2) {
        clearTimeout(this.delayTimer);
        this.delayTimer = setTimeout(() => {
          axios
            .get(
              `${import.meta.env.VITE_API_URI}/hashtag?input=${this.inputText}`,
              {
                withCredentials: true, // Include cookies in the request
              }
            )
            .then((response) => {
              this.tags = response.data.data;
            })
            .catch((error) => {
              console.error(error);
            });
        }, 1000);
      } else {
        this.tags = [];
      }
    },
    createTag() {
      // 새로운 태그를 만드는 로직을 구현합니다.
      axios
        .post(
          `${import.meta.env.VITE_API_URI}/hashtag`,
          {
            tagName: this.inputText,
          },
          {
            withCredentials: true, // Include cookies in the request
          }
        )
        .then((response) => {
          this.myTags.push(response.data.data);
          this.inputText = "";
        })
        .catch((error) => {
          console.error(error);
        });
    },
    selectTag(tag) {
      console.log(tag);
      // 중복 검사
      if (!this.myTags.some((t) => t.id === tag.id)) {
        this.myTags.push(tag);
        this.$emit('updateHashTags', this.myTags);
      }
    },
  },
};
</script>
