<script setup lang = "ts">
import axios from "axios";

import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true
  }
})

const post = ref({
  id: 0,
  title: "",
  content: ""
})
axios.get('/backend-api/posts/' + props.postId).then((response) => {
  post.value = response.data
})

const edit = () =>{
  axios.patch('/backend-api/posts/' + props.postId, post.value).then((response) => {
    router.replace({name: "read", params: {postId: props.postId}})
})
}

</script>

<template>
  <div>
    <el-input type="text" v-model="post.title" placeholder="제목을 입력해주세요."/>
  </div>
  <div class="mt-2">
    <el-input type="textarea" v-model="post.content" rows="15"></el-input>
  </div>
  <div class="mt-2">
    <el-button type="warning" @click="edit()">수정완료</el-button>
  </div>

</template>

<style>
</style>