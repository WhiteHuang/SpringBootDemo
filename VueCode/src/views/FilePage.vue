<script>
import {serverIp} from "../../public/config";

export default {
  name: "FilePage"
  , data() {
    return {
      tableData: [],
      multipleSelection: [],
      name: "",
      total: 0,
      pageNum: 1,
      pageSize: 5,
      uploadPath:"http://"+serverIp+":9090/file/upload"
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/file/page", {
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

    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    reset() {
      this.name = ""
      this.load()
    },

    del(id) {
      this.request.delete("/file/" + id).then(res => {
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
        this.request.post("/file/del/batch", ids).then(
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
    addSuccess() {
      this.$message.success("导入成功！")
      this.load()
    },
    addFail() {
      this.$message.error("导入失败！")
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
    download(val) {
      window.open(val)
    },
    changeState(row) {
      this.request.post("/file/update", row).then(res => {
        if (res.code === '200' && res.data) {
          this.$message.success("更新成功！")
        } else {
          this.$message.error("更新失败！")
        }
      })
    }
  }
}
</script>

<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width:200px" placeholder="请输入文件名" suffix-icon="el-icon-search" class="ml-5"
                v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
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
      <el-upload
          :action="uploadPath" style="display: inline-block" class="ml-5"
          :show-file-list="false"
          :on-success="addSuccess"
          :on-error="addFail">
        <el-button class="ml-5" type="primary">上传文件 <i class="el-icon-bottom"></i></el-button>
      </el-upload>
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
      <el-table-column prop="name" label="文件名称"></el-table-column>
      <el-table-column prop="type" label="文件类型"></el-table-column>
      <el-table-column prop="size" label="文件大小(kb)"></el-table-column>
      <el-table-column prop="url" label="下载">
        <template slot-scope="scope">
          <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="enable" label="启用">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc"
                     @change="changeState(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
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
  </div>
</template>

<style scoped>

</style>