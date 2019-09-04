<template>
  <div id="app" class="block">
    <el-container style="width: 90%;margin-left: 5%">
      <el-header>
        <transition name="el-fade-in">
          <TopNavigator v-show="show"></TopNavigator>
        </transition>
      </el-header>
      <el-main>
        <transition name="el-fade-in-linear">
          <div v-show="show">
            <carousel></carousel>
            <div class="gallery agileits w3layouts">
              <div class="container">

                <h2 style="text-align: center;color: black">优质房源，尽在掌握</h2>

                <div class="col-md-6 col-sm-6 gallery-grids agileits w3layouts gallery-grids1 wow slideInLeft">
                  <div class="gallery-grid-images agileits w3layouts">
                    <div class="col-md-4 col-sm-4 gallery-grid agileits w3layouts gallery-grid-1">
                      <img src="/static/images/project-1.jpg" alt="Agileits W3layouts">
                    </div>
                    <div class="col-md-4 col-sm-4 gallery-grid agileits w3layouts gallery-grid-2">
                      <img src="/static/images/project-3.jpg" alt="Agileits W3layouts">
                    </div>
                    <div class="col-md-4 col-sm-4 gallery-grid agileits w3layouts gallery-grid-3">
                      <img src="/static/images/project-5.jpg" alt="Agileits W3layouts">
                    </div>
                    <div class="col-md-4 col-sm-4 gallery-grid agileits w3layouts gallery-grid-4">
                      <img src="/static/images/project-6.jpg" alt="Agileits W3layouts">
                    </div>
                    <div class="col-md-4 col-sm-4 gallery-grid agileits w3layouts gallery-grid-5">
                      <img src="/static/images/project-7.jpg" alt="Agileits W3layouts">
                    </div>
                    <div class="col-md-4 col-sm-4 gallery-grid agileits w3layouts gallery-grid-6">
                      <img src="/static/images/project-8.jpg" alt="Agileits W3layouts">
                    </div>
                    <div class="clearfix"></div>
                  </div>
                  <div class="gallery-grid-info wow agileits w3layouts fadeInLeft">
                    <h3>快速</h3>
                    <h4>高效</h4>
                    <h5>可靠</h5>
                  </div>
                </div>

                <div class="col-md-6 col-sm-6 gallery-grids agileits w3layouts gallery-grids2 wow slideInRight">
                  <img src="/static/images/gallery.jpg" alt="Agileits W3layouts">
                </div>
                <div class="clearfix"></div>

              </div>
            </div>
            <h1 style="text-align: center;font-size: 40px">想要的屋子，一触即达</h1>
            <houseselector></houseselector>
          </div>
        </transition>
        <el-divider style="margin-top: 4%"></el-divider>
        <el-divider style="margin-top: 4%"></el-divider>
        <router-view></router-view>
      </el-main>
      <el-footer>
        <Footer></Footer>
      </el-footer>
    </el-container>
  </div>
</template>

<script>


    import TopNavigator from "./TopNavigator";
    import carousel from "./carousel";
    import houseselector from "./houseselector";
    import Footer from "./footer";

    export default {
        name: 'index',
        components: {
            TopNavigator,
            carousel,
            houseselector,
            Footer,
        },
        data: function () {
            return {
                show: false,
            }
        },
        mounted() {
            this.show = true;
        }
    }
</script>

<style>

</style>
