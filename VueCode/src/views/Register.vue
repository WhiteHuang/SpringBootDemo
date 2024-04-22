<script>
import user from "@/views/User.vue";

export default {
  name: "Register",
  data() {

    var validatePass2 = (rule, value, callback) => {
      console.log("值" + value)
      if (value === '' || value===undefined) {
        console.log("请再次输入密码")
         callback(new Error('请再次输入密码'));
      } else if (value !== this.user.password) {
        console.log("两次输入密码不一致")
         callback(new Error('两次输入密码不一致!'));
      } else {
         callback();
      }
    };




    return {
      user: {},
      ruleForm: {

        // checkPas: '',

      },
      rules: {

        checkPas: [
          {validator: validatePass2, trigger: 'blur'}
        ]

        ,
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, message: "长度不小于3个字符", trigger: 'blur'}
        ], password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 6, message: "长度不小于6个字符", trigger: 'blur'}
        ], chepass: [
          { validator: validatePass2, trigger: 'blur'},
          {min: 6, message: "长度不小于6个字符", trigger: 'blur'}
        ]
      },

    }
  },

  methods: {
    login() {
      this.$refs["userForm"].validate((valid) => {
            if (valid) {
              this.request.post("/user/register", this.user).then(res => {
                console.log(res)
                if (res.code === '200') {
                  console.log("成功")
                  this.back()
                  this.$message.success("注册成功")
                } else {
                  console.log("失败")
                  this.$message.error(res.msg)
                }
              })
            }
          }
      );

    },

    back() {
      this.$router.push("/login")
    }
  }
}
</script>

<template>
  <div class="wrapper">
    <div
        style="margin: 15% auto;background-color:#fff;width: 350px;height: 400px;padding: 20px;border-radius: 10px;box-shadow: gray">
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>登录</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入用户名" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user"
                    v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock"
                    show-password
                    v-model="user.password"></el-input>
        </el-form-item>

        <el-form-item prop="chepass">
          <el-input placeholder="请再次输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock"
                    show-password
                    v-model="user.chepass"></el-input>
        </el-form-item>

        <el-form-item style="margin: 10px  0;text-align: center">
          <el-button type="primary" size="medium" autocomplete="off" style="width: 40%" @click="login">注册</el-button>
          <el-button type="warning" size="medium" autocomplete="off" style="width: 40%" @click="back">返回登录
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