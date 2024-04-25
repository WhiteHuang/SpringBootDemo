<script>
import user from "@/views/User.vue";
import {setRouters} from "@/router";

export default {
  name: "Login",
  data() {
    return {
      user: {
        username:"monkey",
        password:"123456"
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, message: "长度不小于3个字符", trigger: 'blur'}
        ], password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 6, message: "长度不小于6个字符", trigger: 'blur'}
        ]
      }
    }
  },

  methods: {
    login() {
      this.$refs["userForm"].validate((valid) => {
            if(valid){
              console.log("login")
              this.request.post("/user/login", this.user).then(res => {
                console.log("数据")
                console.log(res)
                if (res.code === '200') {
                  console.log("成功")
                  localStorage.setItem("user",JSON.stringify(res.data))
                  setRouters()
                  if(res.data.role==='ROLE_STUDENT'){
                    this.$router.push("/front/frontHome")
                  }else{
                    this.$router.push("/")
                  }

                  this.$message.success("登录成功")
                } else {
                  console.log("失败")
                  this.$message.error(res.msg)
                }
              })
            }
          }
      );

    },

    register() {
this.$router.push("/register")
    }
  }
}
</script>

<template>
  <div class="wrapper">
    <div
        style="margin: 15% auto;background-color:#fff;width: 350px;height: 300px;padding: 20px;border-radius: 10px;box-shadow: gray">
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>登录</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"></el-input>
        </el-form-item>

        <el-form-item style="margin: 10px  0;text-align: center">
          <el-button type="primary" size="medium" autocomplete="off" style="width: 40%" @click="login">登录</el-button>
          <el-button type="warning" size="medium" autocomplete="off" style="width: 40%" @click="register">注册
          </el-button>
        </el-form-item>
      </el-form>
    </div>

  </div>
</template>

<style scoped>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom left, #42b983, #3f5efb);
  overflow: hidden;
}
</style>