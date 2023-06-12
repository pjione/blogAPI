<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";

const posts = ref([]);
axios.get("/backend-api/posts").then((response)=>{
  response.data.data.forEach((r: any) => {
    posts.value.push(r);
  })
})
</script>

<template>
  <ul>
    <li v-for = "post in posts" :key="post.id" class="mt-5">
      <div>{{post.title}}</div>
      <div>{{post.content}}</div>
    </li>
  </ul>

</template>
