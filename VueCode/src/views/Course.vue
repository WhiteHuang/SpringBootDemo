<script>
import {serverIp} from "../../public/config";

export default {
  name: "Course"
  , data() {
    return {
      tableData: [],
      multipleSelection: [],
      name: "",
      total: 0,
      pageNum: 1,
      pageSize: 5,
      dialogFormVisible: false,
      form: {},
      options:[],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/course/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      })
          .then(res => {
            if (res.code === '200') {
              console.log(res)
              this.tableData = res.data.records
              this.total = res.data.total
            }

          })
    },

    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
      this.loadTeachers()
    },
    handleEdit(row) {
      this.dialogFormVisible = true
      this.form = JSON.parse(JSON.stringify(row))
      this.loadTeachers()
    },
    selectCourse(row) {
      this.request.post('/course/studentCourse/'+row.id+'/'+this.user.id).then(res=>{
        if (res.code === '200') {
          this.$message.success("选课成功！")
          this.load()
        } else {
          this.$message.error("选课失败！"+res.msg)
        }
      })
    },

    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    reset() {
      this.name = ""
      this.load()
    },

    del(id) {
      this.request.delete("/course/" + id).then(res => {
        if (res.code === '200' && res.data) {
          this.$message.success("删除成功！")
          this.load()
        } else {
          this.$message.error("删除失败！")
        }
      })
    },

    delBatch() {
      if (this.multipleSelection.length > 0) {
        let ids = this.multipleSelection.map(v => v.id)
        this.request.post("/course/del/batch", ids).then(
            res => {
              if (res.code === '200' && res.data) {
                this.$message.success("批量删除成功！")
                this.load()
                this.multipleSelection = []
              } else {
                this.$message.error("批量删除失败！")
              }
            }
        )
      }

    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.load()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.pageNum = val
      this.load()
    },
    changeState(row) {
      this.request.post("/course", row).then(res => {
        if (res.code === '200' && res.data) {
          this.$message.success("更新成功！")
        } else {
          this.$message.error("更新失败！")
        }
      })
    },
    loadTeachers(){
      this.request.get("/user/role/"+"ROLE_TEACHER").then(res=>{
        if(res.code==='200'){
          console.log(JSON.stringify(res.data))
          this.options=res.data
        }
      })
    },
    save() {
      this.request.post("/course", this.form)
          .then(res => {
            if (res.code === '200') {
              this.$message.success("保存成功！")
              this.dialogFormVisible = false
              this.load()
            } else {
              this.$message.error("保存失败！")
            }
          })
    },
  }
}
</script>

<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width:200px" placeholder="请输入课程名" suffix-icon="el-icon-search" class="ml-5"
                v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button class="ml-5" type="primary" @click="handleAdd" v-if="user.role==='ROLE_ADMIN'">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='好的'
          cancel-button-text='不用了'
          icon="el-icon-info"
          icon-color="red"
          @confirm="delBatch"
          title="确定批量删除吗？"
          v-if="user.role==='ROLE_ADMIN'">
        <el-button class="ml-5" type="danger" slot="reference">批量删除 <i class="el-icon-delete"></i></el-button>
      </el-popconfirm>

    </div>

    <el-table :data="tableData" border stripe :header-cell-style="{background:'#eef1f6',color:'#606266'}"
              @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <!--    <el-table-column prop="name" label="文件" width="140" align="center">-->
      <!--      <template slot-scope="scope">-->
      <!--        <img :src="scope.row.url" alt="" style="width: 50px;border-radius: 50%;position: relative;top:10px;right: 5px">-->
      <!--      </template>-->
      <!--    </el-table-column>-->
      <el-table-column prop="name" label="课程名称"></el-table-column>
      <el-table-column prop="score" label="课程学分"></el-table-column>
      <el-table-column prop="times" label="课时"></el-table-column>
      <el-table-column prop="teacher" label="授课老师"></el-table-column>
      <el-table-column prop="state" label="是否开课"  v-if="this.user.role!=='ROLE_STUDENT'">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.state" active-color="#13ce66" inactive-color="#ccc"
                     @change="changeState(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" @click="selectCourse(scope.row)" v-if="user.role==='ROLE_STUDENT'">选课 <i class="el-icon-s-check"></i></el-button>
          <el-button type="success" @click="handleEdit(scope.row)" v-if="user.role==='ROLE_ADMIN'">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              @confirm="del(scope.row.id)"
              title="确定删除吗？" v-if="user.role==='ROLE_ADMIN'">
            <el-button type="danger" slot="reference">删除 <i class="el-icon-delete"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>


    <el-dialog title="课程信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="90px" size="medium">
        <el-form-item label="课程名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="课程学分">
          <el-input v-model="form.score" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="课时">
          <el-input v-model="form.times" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="授课老师">
          <template slot-scope="scope">
            <el-select clearable v-model="form.teacherId" placeholder="请选择" style="width: 100%">
              <el-option v-for="item in options" :key="item.id" :label="item.nickname" :value="item.id">
                {{item.nickname}}
              </el-option>
            </el-select>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<style scoped>

</style>