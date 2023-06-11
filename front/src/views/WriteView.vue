<script setup lang = "ts">


import {ref} from "vue";

const title = ref("")
const content = ref("")


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