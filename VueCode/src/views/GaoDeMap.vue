<script>
export default {
  name: "GaoDeMap",
  mounted() {
    var map = new AMap.Map("container", {
      zoom: 13, //地图级别
      center: [116.397428, 39.90923], //地图中心点
      resizeEnable: true
    });

    const marker = new AMap.Marker({
      position: new AMap.LngLat(116.39, 39.9), //经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
      title: "北京",
    });
    var clickHandler = function (e) {
      console.log('位置[' + e.lnglat.getLng() + ',' + e.lnglat.getLat() + ']')
      //打开信息窗体
      infoWindow.open(map, [e.lnglat.getLng(), e.lnglat.getLat()]); //map 为当前地图的实例，map.getCenter() 用于获取地图中心点坐标。
    }
    map.on('click', clickHandler)
//将创建的点标记添加到已有的地图实例：
    map.add(marker);


    var content = [
      "<div><b>高德软件有限公司</b>",
      "电话 : 010-84107000   邮编 : 100102",
      "地址 : 北京市望京阜通东大街方恒国际中心A座16层</div>",
    ];

    //创建 infoWindow 实例
    var infoWindow = new AMap.InfoWindow({
      content: content.join("<br>"), //传入字符串拼接的 DOM 元素
      anchor: "top-left",
    });


    var path = [];
    path.push([116.303843, 39.983412]);
    path.push([116.321354, 39.896436]);
    path.push([116.407012, 39.992093]);



    AMap.plugin("AMap.DragRoute", function () {
      //path 是驾车导航的起、途径和终点，最多支持16个途经点
      var path = [];
      path.push([116.303843, 39.983412]);
      path.push([116.321354, 39.896436]);
      path.push([116.407012, 39.992093]);
      var route = new AMap.DragRoute(map, path, 0);
      //查询导航路径并开启拖拽导航
      route.search();
    });
  },
}
</script>

<template>
  <div id="container">
  </div>
</template>

<style scoped>
#container {
  width: 100%;
  height: 100vh;
}
</style>