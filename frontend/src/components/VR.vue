<template>
  <div id="main-screen">
    <a-scene>
      <a-assets>
         <img id="floor" v-bind:src="message[0]">
      <img id="arrow" src="/static/pic/arrow.png">
      <img id="ru" v-bind:src="message[1]">
    </a-assets>
    <a-sky id="asky-back" src="#ru"></a-sky>
    <a-sky id="asky-front" src="#floor"></a-sky>

    <a-curvedimage id="arrow1" src="#arrow" theta-length="15" height=".36" rotation="0 180 0" scale="0.5 0.5 0.5">
      <a-animation attribute="position"  to=".2 0 0" repeat="indefinite" dur="500" direction="alternate"></a-animation>
    </a-curvedimage>
    <!-- Camera with gaze cursor -->
    <a-entity>
      <a-entity camera look-controls wasd-controls>
        <!-- Cursor should only interact with .box class entities -->
        <a-entity cursor id="cursor"
                  geometry="primitive: ring; radiusOuter: 0.015;
                              radiusInner: 0.01; segmentsTheta: 32"
                  material="color: white; shader: flat"
                  position="0 0 -0.75">
        </a-entity>
      </a-entity>
    </a-entity>
  </a-scene>
  </div>
</template>

<script>
    export default {
        props: {
            message:{
                type:Array,
            }
        },
        name: "VR",
        mounted() {
          console.log('dfsdfdfsfdds');
        },
        data(){
            return{
                path:"/static/pic/vr1.jpeg"
            }

        }

    }
</script>

<style scoped>

</style>
