<template>
  <div id="housemanage">
    <div>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="房屋区域">
          <el-select v-model="form.cityy" placeholder="请选择房屋区域">
            <el-option label="海淀区" value="海淀区"></el-option>
            <el-option label="昌平区" value="昌平区"></el-option>
            <el-option label="房山区" value="房山区"></el-option>
            <el-option label="朝阳区" value="朝阳区"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="房间类型">
          <el-select v-model="form.peoplenum" placeholder="请选择是几人间">
            <el-option label="一人间" value=1></el-option>
            <el-option label="两人间" value=2></el-option>
            <el-option label="四人间" value=4></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="具体地址">
          <el-input v-model="form.addrDetail"></el-input>
        </el-form-item>
        <el-form-item label="长租租金">
          <el-input v-model="form.longRentPrice"></el-input>
        </el-form-item>
        <el-form-item label="短期租金">
          <el-input v-model="form.shortRentPrice"></el-input>
        </el-form-item>
        <el-form-item label="详图">
          <Plupload @fun="getmsg"></Plupload>
        </el-form-item>
        <el-form-item label="宽图">
          <Plupload1 @fun="getmsg"></Plupload1>
        </el-form-item>
        <el-form-item label="小图">
          <Plupload2 @fun="getmsg"></Plupload2>
        </el-form-item>
        <el-form-item label="VR预览图">
          <Plupload3 @fun="getmsg"></Plupload3>
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="form.description" :rows="6"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">立即创建</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
    </div>
  </div>
</template>

<script>
    import serverTopNavigator from "./serverTopNavigator";
    import Footer from "../components/footer";
    import Plupload from "../components/Plupload";
    import Plupload1 from "../components/Plupload1";
    import Plupload2 from "../components/Plupload2";
    import Plupload3 from "../components/Plupload3";

    export default {
        name: "housemanage",
        components: {
            serverTopNavigator,
            Footer,
            Plupload,
            Plupload1,
            Plupload2,
            Plupload3
        },
        data() {
            return {
                pic1: false,
                pic2: false,
                pic3: false,
                uploadForm: new FormData(),
                form: {
                    cityy: '',
                    addrDetail: '',
                    longRentPrice: '',
                    shortRentPrice: '',
                    description: '',
                    peoplenum: ''
                },
                url1: [],
                url2: [],
                url3: [],
                url4: [],

            }
        },
        methods: {
            getmsg(url, type) {
                switch (type) {
                    case 1:
                        this.url1.push('https://lzmmmmm.oss-cn-beijing.aliyuncs.com/'+url);
                        break;
                    case 2:
                        this.url2.push('https://lzmmmmm.oss-cn-beijing.aliyuncs.com/'+url);
                        break;
                    case 3:
                        this.url3.push('https://lzmmmmm.oss-cn-beijing.aliyuncs.com/'+url);
                        break;
                    case 4:
                        this.url4.push('https://lzmmmmm.oss-cn-beijing.aliyuncs.com/'+url)

                }
            },
            onSubmit() {
                const url = '/api/add_room';
                this.$http({
                    method: 'post',
                    url: url,
                    data: {
                        city: this.form.cityy,
                        shortPrice: this.form.shortRentPrice,
                        longPrice: this.form.longRentPrice,
                        type: this.form.peoplenum,
                        hdpiImages: this.url1,
                        mdpiImages: this.url2,
                        ldpiImages: this.url3,
                        vrImages: this.url4,
                        description: this.description,
                        location: this.form.addrDetail,
                    }
                }).then((res) => {
                    this.$notify({
                        title: "成功",
                        message: "您已成功上传房间信息",
                        type: "success"
                    });
                    this.$emit("funn");
                    setTimeout(function () {
                        window.location.reload();
                    },500)
                }).catch((error) => {
                    console.warn(error)
                });
            },
            clear() {
                document.getElementById('ossfile').innerHTML = "";
                this.pic1 = false;
            },


        }

    }
</script>

<style scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
