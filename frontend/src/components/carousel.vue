<template>
  <el-carousel type="card" :interval="2000" indicator-position="none" id="el-carousel">
    <el-carousel-item v-for="item in pic" :key="item">
      <img :src="item.src">
    </el-carousel-item>
  </el-carousel>
</template>
<script>
  export default {
    data() {
      return {
        bannerHeight: 700,
        screenWidth: 1920,
        pic:[
          {
            src:require('../assets/1.jpeg')
          },
          {
            src:require('../assets/2.jpeg')
          },
          {
            src:require('../assets/3.jpeg')
          }
        ]
      };
      },
    mounted() {
      this.setSize1();
      const that = this;
      //监听浏览器窗口大小改变
      window.addEventListener('resize', function() {
        var width = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
        that.screenWidth = width;
        that.setSize();
        }, false);
      },
    methods: {
      setSize1: function() {
        var width = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
        this.screenWidth = width;
      this.bannerHeight = 700 / 1920 * this.screenWidth - 50;
      document.getElementById('el-carousel').style.height = this.bannerHeight + 'px';
      },
      setSize: function() {
        this.bannerHeight = 700 / 1920 * this.screenWidth - 50 ;
        document.getElementById('el-carousel').style.height = this.bannerHeight + 'px';
        },
    }
  }</script>
<style>
  .el-carousel__container {
    height: 100%;
  }
  img {
    display: inline-block;
    height: auto;
    max-width: 100%;
    margin-bottom: 0;
    margin-top: 8%;
  }
</style>
