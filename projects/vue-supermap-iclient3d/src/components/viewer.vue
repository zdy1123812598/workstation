<template>
  <div id="cesiumContainer"></div>
</template>

<script>

export default {
  name:'viewer',
  mounted(){
    this.initMap()
  },
  methods:{
    initMap(){
      const Cesium = window.Cesium
      let viewer = new Cesium.Viewer('cesiumContainer',{
        shadows:true
      })
      console.log(viewer);
      let url = 'http://www.supermapol.com/realspace/services/3D-CBDCache20200416/rest/realspace'
      let promise = viewer.scene.open(url)
      Cesium.when(promise,layers =>{
        console.log(layers);
        layers.forEach(item => {
          item.selectEnable = false
          item.shadowType = 2
        });
      })
      this.$store.state.viewer = viewer
    },
  }
}
</script>

<style scoped>

</style>