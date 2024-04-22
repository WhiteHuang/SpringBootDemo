<script>
import router, {resetRouter} from "@/router";

export default {
  name: "Header",
  props:{//声明父级传递的属性
    collapseBtnClass:String,
    pathName:String,
    user:Object,
    collapse:function () {
    }
  },
  data(){
    return {
      paths:[],
      // user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
    }
  },

  methods:{
    logout(){
      localStorage.removeItem("user")
      router.push("/login")
      resetRouter()
      this.$message.success("退出成功")
    },

    toPerson(){
      this.$router.push("/person")
    }
  }

}
</script>

<template>
  <div style=" font-size: 12px;line-height: 60px;display: flex">
    <div style="flex: 1;font-size: 18px">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
      <div class="ml-5" style="display: inline-block ;margin-left: 10px">
        <el-breadcrumb separator="/">
<!--          <el-breadcrumb-item :to="'/'" v-for="item in paths">{{item.name}}}</el-breadcrumb-item>-->
           <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="'/'">{{this.$route.name}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
    </div>
    <el-dropdown style="width: auto;cursor: pointer">
      <div style="display: inline-block" >
        <img :src="user.avatarUrl" alt="" style="width: 30px;border-radius: 50%;position: relative;top:10px;right: 5px">
        <span>{{ user.nickname }}</span>
        <i class="el-icon-arrow-down" style="margin-left: 8px"></i>
      </div>
      <el-dropdown-menu slot="dropdown">

        <el-dropdown-item @click=""><router-link to="/updatepwd" style="text-decoration: none">修改密码</router-link></el-dropdown-item>
        <el-dropdown-item @click=""><router-link to="/person" style="text-decoration: none">个人信息</router-link></el-dropdown-item>
        <el-dropdown-item @click="">
        <span  style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>


  </div>
</template>

<style scoped>

</style>