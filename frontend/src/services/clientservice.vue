<template>
  <div class="hello">
    <el-container style="width: 90%;margin-left: 5%">
      <el-header>
        <server-top-navigator></server-top-navigator>
      </el-header>
      <el-main>
        <Body></Body>
        <el-divider style="margin-top: 4%"></el-divider>
      </el-main>
      <el-footer>
        <Footers></Footers>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
    import Footers from "../components/footer";
    import serverTopNavigator from "./serverTopNavigator";
    import Body from './serverBody'

    export default {
        name: 'clientservice',
        components: {
            Footers,
            Body,
            serverTopNavigator
        },
        mounted() {
            this.checkLogin(2);
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
