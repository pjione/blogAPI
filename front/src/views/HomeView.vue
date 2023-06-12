<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const posts = ref([]);
axios.get("/backend-api/posts").then((response)=>{
  response.data.data.forEach((r: any) => {
    posts.value.push(r);
  })
})

const moveToRead = () => {
  router.push({name: "read"})
}
</script>

<template>
  <ul>
    <li v-for = "post in posts" :key="post.id" class="mt-5">
      <div><router-link :to="{name : 'read', params: {postId: post.id}}">{{post.title}}</router-link></div>
      <div>{{post.content}}</div>
    </li>
  </ul>


</template>
