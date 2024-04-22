<script>
export default {
  name: "MenuManager",
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
      form: {},
      options:[]
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      // fetch("http://localhost:9090/menu/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username)
      //     .then(res=>res.json())
      //     .then(res=> {
      //           console.log(res)
      //           this.tableData=res.data
      //           this.total=res.total
      //         }
      //     )
      this.request.get("/menu", {
        params: {
          // pageNum: this.pageNum,
          // pageSize: this.pageSize,
          // name: this.name,
          // description: this.description,
        }
      })
          .then(res => {
            if (res.code === '200') {
              this.tableData = res.data
              console.log(res.data)
              // this.total = res.data.total
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
      this.loadIcons()
    },
    addChilren(row) {
      this.dialogFormVisible = true
      this.form = {}
      this.form.pid = row.id;
      this.loadIcons()
    },

    loadIcons(){
      this.request.get("/menu/icons").then(res=>{
        if(res.code==='200'){
          this.options=res.data
        }
      })
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
      console.log(this.form)
      this.request.post("/menu", this.form)
          .then(res => {
            if (res.code === '200') {
              this.$message.success("分配成功！")
              this.dialogFormVisible = false
              this.load()
            } else {
              this.$message.error("分配失败！")
            }
          })
    },

    del(id) {
      this.request.delete("/menu/" + id).then(res => {
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
        this.request.post("/menu/del/batch", ids).then(
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
              @selection-change="handleSelectionChange" row-key="id" default-expand-all
    >
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="80px"></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="path" label="路径"></el-table-column>
      <el-table-column prop="component" label="页面组件"></el-table-column>
      <el-table-column prop="icon" label="图标" align="center">
        <template slot-scope="scope">
          <i :class="scope.row.icon" style="font-size: 25px"/>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">

          <el-button type="primary" @click="addChilren(scope.row)" v-if="scope.row.path===''">新增子菜单 <i
              class="el-icon-plus"></i></el-button>
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


    <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="90px" size="medium">
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="页面组件">
          <el-input v-model="form.component" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <template slot-scope="scope">
            <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%">
              <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
                <i :class="item.value"/> {{item.name}}
              </el-option>
            </el-select>
          </template>
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
  </div>
</template>

<style scoped>
.headerBg {
  background-color: #222 !important;
}
</style>