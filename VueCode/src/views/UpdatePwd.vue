<script>
export default {
  name: "UpdatePwd",
  data() {
    var validatePass2 = (rule, value, callback) => {
      console.log("值" + value)
      if (value === '' || value===undefined) {
        console.log("请再次输入密码")
        callback(new Error('请再次输入密码'));
      } else if (value !== this.user.newpassword) {
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
        oldpassword: [
          {required: true, message: '请输入旧密码', trigger: 'blur'},
          {min: 3, message: "长度不小于3个字符", trigger: 'blur'}
        ], newpassword: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
          {min: 6, message: "长度不小于6个字符", trigger: 'blur'}
        ], chepass: [
          {validator: validatePass2, trigger: 'blur'},
          {min: 6, message: "长度不小于6个字符", trigger: 'blur'}
        ]
      },
    }
  },
  methods:{
    save() {

    }
  }
}
</script>

<template>
  <el-card style="width: 500px;padding: 20px;border: 1px ; ">
    <el-form :model="user" :rules="rules" ref="userForm">
      <el-form-item prop="oldpassword">
        <el-input placeholder="请输入旧密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock"
                  v-model="user.oldpassword"></el-input>
      </el-form-item>
      <el-form-item prop="newpassword">
        <el-input placeholder="请输入新密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock"
                  show-password
                  v-model="user.newpassword"></el-input>
      </el-form-item>

      <el-form-item prop="chepass">
        <el-input placeholder="请再次输入新密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock"
                  show-password
                  v-model="user.chepass"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<style scoped>

</style>