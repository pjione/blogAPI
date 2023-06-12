<script setup lang = "ts">
import axios from "axios";

import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter()

const title = ref("")
const content = ref("")

const write = function(){
  axios.post("/backend-api/posts",{
    title: title.value,
    content: content.value
  })
  .then(() => {
    router.replace({name: "home"})
  })
}
</script>

<template>
  <div>
    <el-input type="text" v-model="title" placeholder="제목을 입력해주세요."/>
  </div>
  <div class="mt-2">
    <el-input type="textarea" v-model="content" rows="15"></el-input>
  </div>
  <div class="mt-2">
    <el-button type="primary" @click="write()">글 등록</el-button>
  </div>

</template>

<style>
</style>