<template>
  <!-- Footer -->
  <div class="footer agileits w3layouts" id="footer">
    <div class="container" id="cont">
      <div class="col-md-6 col-sm-6 agileits w3layouts footer-grids newsletter wow fadeInUp">
        <h4>Stay Connected</h4>
        <p>Subscribe to our newsletter and get informed of our newest projects.</p>
        <form action="#" method="post">
          <input type="text" placeholder="Email" name="Email" required="">
        </form>
      </div>
      <div class="clearfix"></div>
    </div>
    <div class="clearfix"></div>
    <div style="height: 50px;margin-top: 5px"></div>
  </div>

  <!-- //Footer -->
</template>

<script>
  export default {
    name: "app-footer",
    data(){
      return{
        copyright:"Copyright 2019 Hotel Demo"
      }
    }
  }

</script>

<style scoped>
  #footer{
    height: 260px;
  }
  p{
    text-align: center;
  }
  .padding{
    padding-top: 2%;
  }
</style>
