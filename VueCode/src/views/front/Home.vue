<script>
export default {
  name: "FrontHome",
  data() {
    return {
      imgs: [
        'https://m.360buyimg.com/babel/jfs/t1/199597/16/40389/109145/662217fbF585641c1/e93d3236967f6d55.png',
        'https://m.360buyimg.com/babel/jfs/t20270422/239538/20/5534/48919/66261ee3F64c60213/ace6b21de3d195d6.jpg',
        'https://m.360buyimg.com/babel/jfs/t1/228625/35/12130/97158/66276912F36e259de/34ca8bc281ee8f2b.png',
        'https://m.360buyimg.com/babel/jfs/t1/135498/4/42634/94871/66285fe6Fc98a1291/cec437fa6a819719.png'
      ],
      flies:[],
    }
  },
  created() {
    this.request.get("/file/all").then(res=>{
      if(res.code==='200'){
        this.flies=res.data.filter(item=>item.type==='png'||item.type==='jpg')
      }
    })
  },
  methods: {}
}
</script>

<template>
  <div>
    <div style="margin: 10px 0;">
      <el-carousel indicator-position="outside" height="500px" :interval="5000">
        <el-carousel-item v-for="item in imgs" :key="item">
          <img :src="item" alt="图片" style="width: 100%;height: 100%;"/>
        </el-carousel-item>
      </el-carousel>
    </div>
    <div style="margin: 10px 0">
      <el-row :gutter="5" >

        <el-col :span="6" v-for="flie in this.flies" :key="flie.id" style="margin-bottom: 10px;">
          <div style="border: 1px solid #ccc">
            <img :src="flie.url" alt="" style="width: 100%">
            <div style="padding: 10px 0;">
              <span class="spanStyle" style="color: gray" :title="flie.name">{{flie.name}}}</span>
            </div>
          </div>

        </el-col>

      </el-row>
    </div>
  </div>
</template>

<style scoped>
.spanStyle {
  display: inline-block;
  white-space: normal;
  width: 100%;
  overflow: hidden; /*超出的文本隐藏*/
  text-overflow: ellipsis; /* 溢出用省略号*/

}
</style>