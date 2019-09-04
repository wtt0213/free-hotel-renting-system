<template>
  <el-container style="width: 90%;margin-left: 5%;">
    <el-header>
      <TopNavigator></TopNavigator>

    </el-header>
    <el-main>
      <Body></Body>
      <el-divider style="margin-top: 4%"></el-divider>
    </el-main>
    <el-footer>
      <Footer></Footer>
    </el-footer>
  </el-container>
</template>

<script>
  import TopNavigator from "../components/TopNavigator";
  import Footer from "../components/footer";
  import Body from './rentBody'
    export default {
        name: "details",
      components:{
          TopNavigator,
          Footer,
          Body,
      },
        mounted() {
            this.checkLogin(1);
        }
    }
</script>

<style scoped>

</style>
