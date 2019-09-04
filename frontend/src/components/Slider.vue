<template>
  <div>
    <slide-verify :l="42"
                  :r="10"
                  :w="310"
                  :h="155"
                  @success="onSuccess"
                  @fail="onFail"
                  @refresh="onRefresh"
                  :slider-text="text"
    ></slide-verify>
    <p>{{msg}}</p>
  </div>

</template>

<script>
    export default {
        name: "Slider",
        data(){
          return{
            msg: '',
            text: '请向右滑',
            is_verify:false
          }
        },
        methods:{
          onSuccess(){
            this.msg = '验证成功';
            this.$emit("verify","1");
          },
          onFail(){
            this.msg = '验证失败';
            this.$emit("verify","2");
          },
          onRefresh(){
            this.msg = ''
          }
        }
    }
</script>

<style scoped>

</style>
