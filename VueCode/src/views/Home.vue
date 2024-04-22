<script>
import * as echart from 'echarts'

export default {
  name: "Home",
  data() {
    return {}
  },
  mounted() { //页面元素渲染后再触发

    var option;
    option = {
      title: {
        text: '各季度会员数量统计',
        subtext: '趋势图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      xAxis: {type: 'category', data: []},
      yAxis: {type: 'value'},
      series: [
        {
          name:'趋势',
          data: [],
          type: "line"
        },
        {
          name:'数量',
          data: [],
          type: "bar"
        }, {
          name:'数量1',
          data: [],
          type: "bar"
        }, {
          name:'趋势1',
          data: [],
          type: "line"
        }
      ]
    };

    var pieoption = {
      title: {
        text: '各季度会员数量统计',
        subtext: '比例图',
        left: 'center'

      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name:"锡巴克",
          type: 'pie',
          center:['25%','50%'],
          radius: '50%',
          label: {
            normal: {
              show: true,
              position: 'inner',
              textStyle: {
                fontWeight: 300,
                fontSize: 13
              },
              formatter: '{d}%'
            }
          },
          data: [
            {value: 1048, name: 'Search Engine'},
            {value: 735, name: 'Direct'},
            {value: 580, name: 'Email'},
            {value: 484, name: 'Union Ads'},
            {value: 300, name: 'Video Ads'}
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
        {
          name:"星瑞",
          type: 'pie',
          center:['75%','50%'],
          radius: ['20%','50%'],
          label: {
            normal: {
              show: true,
              position: 'inner',
              textStyle: {
                fontWeight: 300,
                fontSize: 13
              },
              formatter: '{d}%'
            }
          },
          data: [
            {value: 1048, name: 'Search Engine'},
            {value: 735, name: 'Direct'},
            {value: 580, name: 'Email'},
            {value: 484, name: 'Union Ads'},
            {value: 300, name: 'Video Ads'}
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
      ]
    };

    var chartDom = document.getElementById('main');
    var myChart = echart.init(chartDom);

    var pieDom = document.getElementById('pie');
    var pieChart = echart.init(pieDom);

    this.request.get("/echarts/member").then(res => {
      if (res.code === '200') {
        option.xAxis.data = res.data.x;
        option.series[0].data = res.data.y;
        option.series[1].data = res.data.y;
        option.series[2].data = [4,3,7,1];
        option.series[3].data = [4,3,7,1];
        myChart.setOption(option);


        pieoption.series[0].data = [
          {name: res.data.x[0], value: res.data.y[0]},
          {name: res.data.x[1], value: res.data.y[1]},
          {name: res.data.x[2], value: res.data.y[2]},
          {name: res.data.x[3], value: res.data.y[3]},
        ]
        pieoption.series[1].data = [
          {name: res.data.x[0], value: 4},
          {name: res.data.x[1], value: 3},
          {name: res.data.x[2], value: 7},
          {name: res.data.x[3], value: 1},
        ]


        pieChart.setOption(pieoption)
      }
    });

    //饼图


    // pieChart.setOption(pieoption);
  }
}

</script>


<template>
  <div>

    <el-row style="margin-bottom: 20px" :gutter="10">
      <el-col :span="6">
        <el-card style="color: #409eff">
          <div >
            用户总数
          </div>
          <div style="padding: 10px;text-align: center;font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #f56c6c">
            销售总量
          </div>
          <div style="padding: 10px;text-align: center;font-weight: bold">
            ￥1000000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #67c23a">
            <i class="el-icon-bank-card"/>
            收益总额
          </div>
          <div style="padding: 10px;text-align: center;font-weight: bold">
            ￥500000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #e6a23c">
            门店总数
          </div>
          <div style="padding: 10px;text-align: center;font-weight: bold">
            1000
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>

      <el-col :span="12">
        <div id="main" style="width: 500px;height: 400px"></div>
      </el-col>
      <el-col :span="12">
        <div id="pie" style="width: 500px;height: 400px"></div>
      </el-col>
    </el-row>


  </div>
</template>

<style scoped>

</style>