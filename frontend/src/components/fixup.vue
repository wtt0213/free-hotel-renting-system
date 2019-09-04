<template>
  <div id="fixup">
    <el-container style="width: 90%;margin-left: 5%">
      <el-header>
        <transition name="el-fade-in">
          <TopNavigator v-show="show"></TopNavigator>
        </transition>
      </el-header>
      <el-main>

      </el-main>
      <el-footer>
      <Footer></Footer>
    </el-footer>
    </el-container>
  </div>
</template>

<script>
    import TopNavigator from "./TopNavigator";
    import Footer from "./footer";
    export default {
        name: "fixup",
        components: {
            TopNavigator,
            Footer
        },
        data:function () {
            return{
                show:false,
            }
        },
        mounted() {
            this.show = true;
        }
    }
</script>

<style scoped>

</style>
