<script>
import router, {resetRouter} from "@/router";
import user from "@/views/User.vue";

export default {
  name: "Front",
  data() {
    return {
      user :localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  methods: {
    logout() {
      localStorage.removeItem("user")
      router.push("/login")
      resetRouter()
      this.$message.success("退出成功")
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    getUser(){
      this.user=localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
      console.log("user-----"+JSON.stringify(user))
    },
  }
}
</script>

<template>
  <div>
    <div style="display:flex;height: 60px;line-height: 60px;border-bottom: 1px solid #eee">

      <div style="width: 200px;text-align: center;"><img src="../../assets/logo.png"
                                                         style="width: 20px;height: 20px; position: relative;top: 5px;right: 5px"/>欢迎来到xx系统
      </div>
      <div style="flex: 1">
        <!--        <ul style="list-style: none;background-color: aquamarine;">-->
        <!--          <li class="item">-->
        <!--            <el-dropdown>-->
        <!--              <span class="el-dropdown-link">-->
        <!--                下拉菜单<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
        <!--              </span>-->
        <!--              <el-dropdown-menu slot="dropdown">-->
        <!--                <el-dropdown-item>黄金糕</el-dropdown-item>-->
        <!--                <el-dropdown-item>狮子头</el-dropdown-item>-->
        <!--                <el-dropdown-item>螺蛳粉</el-dropdown-item>-->
        <!--                <el-dropdown-item disabled>双皮奶</el-dropdown-item>-->
        <!--                <el-dropdown-item divided>蚵仔煎</el-dropdown-item>-->
        <!--              </el-dropdown-menu>-->
        <!--            </el-dropdown>-->
        <!--          </li>-->
        <!--          <li class="item"><a href="">菜单2</a></li>-->
        <!--          <li class="item"><a href="">菜单3</a></li>-->
        <!--          <li class="item"><a href="">菜单4</a></li>-->
        <!--        </ul>-->

        <el-menu :default-active="'1'" class="el-menu-demo" mode="horizontal" router>
          <el-menu-item index="/front/frontHome">首页</el-menu-item>
          <el-submenu index="2">
            <template slot="title">我的工作台</template>
            <el-menu-item index="2-1">选项1</el-menu-item>
            <el-menu-item index="2-2">选项2</el-menu-item>
            <el-menu-item index="2-3">选项3</el-menu-item>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项1</el-menu-item>
              <el-menu-item index="2-4-2">选项2</el-menu-item>
              <el-menu-item index="2-4-3">选项3</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-menu-item index="3" disabled>消息中心</el-menu-item>
          <el-menu-item index="4"><a href="https://www.ele.me" target="_blank"
                                     style="text-decoration: none">订单管理</a></el-menu-item>
        </el-menu>
      </div>
      <div style="width: 200px">
        <div v-if="!user.nickname" style="text-align: center">
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </div>
        <div v-else style="text-align: center">
          <el-dropdown style="width: auto;cursor: pointer">
            <div style="display: inline-block">
              <img :src="user.avatarUrl" alt=""
                   style="width: 30px;border-radius: 50%;position: relative;top:10px;right: 5px">
              <span>{{ user.nickname }}</span>
              <i class="el-icon-arrow-down" style="margin-left: 8px"></i>
            </div>
            <el-dropdown-menu slot="dropdown">

              <el-dropdown-item style="text-decoration: none">
                <router-link to="/front/frontUpdatePwd" style="text-decoration: none">修改密码</router-link>
              </el-dropdown-item>
              <el-dropdown-item style="text-decoration: none">
                <router-link to="/front/frontPerson" style="text-decoration: none">个人信息</router-link>
              </el-dropdown-item>
              <el-dropdown-item @click="">
                <span style="text-decoration: none" @click="logout">退出</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>

      </div>

    </div>
    <div style="width: 1200px;margin: 0 auto;">
      <router-view @refreshUser="getUser"/>
    </div>

  </div>

</template>

<style scoped>
.item {
  display: inline-block;
  width: 100px;
  color: red;
  text-align: center;
  cursor: pointer;
}

.item a {
  color: red;
  text-decoration: none;
}

.item:hover {
  background-color: #409EFF;
}
</style>