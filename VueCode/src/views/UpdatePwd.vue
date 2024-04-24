<script>
export default {
  name: "UpdatePwd",
  data() {
    var validatePass2 = (rule, value, callback) => {
      console.log("值" + value)
      if (value === '' || value===undefined) {
        console.log("请再次输入密码")
        callback(new Error('请再次输入密码'));
      } else if (value !== this.user.newPwd) {
        console.log("两次输入密码不一致")
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      user: {},
      rules: {

        checkPas: [
          {validator: validatePass2, trigger: 'blur'}
        ]

        ,
        oldPwd: [
          {required: true, message: '请输入旧密码', trigger: 'blur'},
          {min: 3, message: "长度不小于3个字符", trigger: 'blur'}
        ], newPwd: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
          {min: 6, message: "长度不小于6个字符", trigger: 'blur'}
        ], chePass: [
          {validator: validatePass2, trigger: 'blur'},
          {min: 6, message: "长度不小于6个字符", trigger: 'blur'}
        ]
      },
    }
  },
  methods:{
    updatePwd() {
      this.$refs["pwdForm"].validate((valid) => {
        console.log("pwdForm "+ JSON.stringify(this.user))
        if(valid){
          this.request.post("/user/updatePwd",this.user).then(res=>{
            if(res.code==='200'){
              this.$store.commit("logout")
              this.$message.success("密码修改成功")
            }else{
              this.$message.success("密码修改失败："+res.message)
            }
          })
        }

      })

    }
  }
}
</script>

<template>
  <el-card style="width: 500px;padding: 20px;border: 1px ; ">
    <el-form :model="user" :rules="rules" ref="pwdForm">
      <el-form-item prop="oldPwd">
        <el-input placeholder="请输入旧密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock"
                  show-password
                  v-model="user.oldPwd"></el-input>
      </el-form-item>
      <el-form-item prop="newPwd">
        <el-input placeholder="请输入新密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock"
                  show-password
                  v-model="user.newPwd"></el-input>
      </el-form-item>

      <el-form-item prop="chePass">
        <el-input placeholder="请再次输入新密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock"
                  show-password
                  v-model="user.chePass"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="updatePwd">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<style scoped>

</style>