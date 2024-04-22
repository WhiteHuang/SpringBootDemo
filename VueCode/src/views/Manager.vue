<template>
  <el-container style="min-height: 100vh ">
    <el-aside :width="sidewidth +'px'"
              style="background-color: rgb(238, 241, 246);box-shadow: 2px 0 6px rgb(0,21,45,35%)">
      <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" :menus="user.menus"></Aside>
    </el-aside>

    <el-container>
      <el-header style=" border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user"></Header>

      </el-header>

      <el-main>
<!--        当前页面子路由展示位置 router-view-->
        <router-view @refreshUser="getUser"/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>


// import request from "@/utils/request";
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";
import user from "@/views/User.vue";
export default {
  name: 'HomeView',
  data() {
    return {
      collapseBtnClass: "el-icon-s-fold",
      isCollapse: false,
      sidewidth: 200,
      logoTextShow: true,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },

  created() {
  },

  components: {
    Aside,
    Header
  },
  methods: {
    getUser(){
      this.user=JSON.parse(localStorage.getItem("user"))
      console.log("user-----"+JSON.stringify(user))
      // this.request.get("/user/username/" + this.user.username).then(
      //     res => {
      //       if (res.code === '200') {
      //
      //       }
      //     }
      // )
    },
    collapse() {
      this.isCollapse = !this.isCollapse;
      this.logoTextShow = !this.isCollapse
      if (this.isCollapse) {
        this.sidewidth = 64
        this.collapseBtnClass = "el-icon-s-unfold"
      } else {
        this.sidewidth = 200
        this.collapseBtnClass = "el-icon-s-fold"
      }
    },

  }
}
</script>
<style>

</style>