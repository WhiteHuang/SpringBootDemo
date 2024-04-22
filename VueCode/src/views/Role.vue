<script>
export default {
  name: "Role",
  data() {
    return {
      tableData: [],
      multipleSelection: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      description: "",
      dialogFormVisible: false,
      dialogFormMenuVisible: false,
      form: {},
      menuDdata: [],
      props: {label: 'name'},
      expends: [],
      checks: [],
      roleId: 0,
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      // fetch("http://localhost:9090/role/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username)
      //     .then(res=>res.json())
      //     .then(res=> {
      //           console.log(res)
      //           this.tableData=res.data
      //           this.total=res.total
      //         }
      //     )
      this.request.get("/role/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          description: this.description,
        }
      })
          .then(res => {
            if (res.code === '200') {
              this.tableData = res.data.records
              this.total = res.data.total
            }
          })


    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row) {
      this.dialogFormVisible = true
      this.form = JSON.parse(JSON.stringify(row))
    },
    selectMenu(row) {
      this.dialogFormMenuVisible = true;
      this.roleId = row.id
      console.log(row)
      this.request.get("/menu", {
        params: {}
      })
          .then(res => {
            if (res.code === '200') {
              this.menuDdata = res.data
              this.expends = res.data.map(v => v.id)
              this.dataSync()
            }
          })

      this.request.get("/role/roleMenu/" + this.roleId, {
        params: {}
      })
          .then(res => {
            if (res.code === '200') {
              this.checks = res.data
              this.dataSync()
            }
          })
    },
    dataSync() {
      //数据同步
      this.menuDdata.forEach(menu => {
        if (!menu.path) {
          this.checks=this.checks.filter(value => value !== menu.id)
        }
      })
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

    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    reset() {
      this.name = ""
      this.description = ""
      this.load()
    },
    save() {
      this.request.post("/role", this.form)
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
    saveRoleMenu() {
      this.request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys())
          .then(res => {
            if (res.code === '200') {
              this.$message.success("绑定成功！")
              this.dialogFormMenuVisible = false
              let curUserId = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).id : null
              if (this.roleId === curUserId) {
                this.$store.commit("logout")
              }
            } else {
              this.$message.error("绑定失败！")
            }
          })
    },

    del(id) {
      this.request.delete("/role/" + id).then(res => {
        if (res.code === '200') {
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
        this.request.post("/role/del/batch", ids).then(
            res => {
              if (res.code === '200') {
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

    handleCheckChange(data, checked, indeterminate) {
      // console.log(data, checked, indeterminate);
    },
  }
}
</script>

<template>
  <div>


    <div style="margin: 10px 0">
      <el-input style="width:200px" placeholder="请输入名称" suffix-icon="el-icon-search" class="ml-5"
                v-model="name"></el-input>
      <el-input style="width:200px" placeholder="请输入描述" suffix-icon="el-icon-message" class="ml-5"
                v-model="description"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button class="ml-5" type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i>
      </el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='好的'
          cancel-button-text='不用了'
          icon="el-icon-info"
          icon-color="red"
          @confirm="delBatch"
          title="确定批量删除吗？">
        <el-button class="ml-5" type="danger" slot="reference">批量删除 <i class="el-icon-delete"></i></el-button>
      </el-popconfirm>

    </div>

    <el-table :data="tableData" border stripe
              :header-cell-style="{background:'#eef1f6',color:'#606266'}"
              @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="80px"></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="keyName" label="唯一标识"></el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="info" @click="selectMenu(scope.row)">分配菜单 <i class="el-icon-menu"></i></el-button>
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              @confirm="del(scope.row.id)"
              title="确定删除吗？">
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

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="90px" size="medium">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识">
          <el-input v-model="form.keyName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="分配菜单" :visible.sync="dialogFormMenuVisible" width="30%">
      <el-tree
          :data="menuDdata"
          :props="props"
          show-checkbox
          node-key="id"
          ref="tree"
          :default-expand-all="true"
          :default-expand-keys="expends"
          :default-checked-keys="checks"
          @check-change="handleCheckChange"
      >
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span><i :class="data.icon"/> {{ data.name }}</span>
      </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormMenuVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
.headerBg {
  background-color: #222 !important;
}
</style>