<template>
  <div id="TopNavigator">
    <nav class="navbar navbar-inverse">
      <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand">青年租房</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div style="margin-left: 20%" class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav navbar-right">
            <li><a v-on:click="exit"><i class="el-icon-delete"></i></a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>
  </div>
</template>

<script>
  import contactUs from "../components/contactUs";
  export default {
    name: "serverTopNavigator",
    components:{
      contactUs
    },
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        logininf:"",
        ruleForm: {
          pass: '',
          checkPass: '',
          email: '',
          Usernames: '',
          Id: ''
        },
        input4:'',
        activeName: 'first',
        rules: {
          pass: [
            { required: true,validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { required: true,validator: validatePass2, trigger: 'change' }
          ],
          email:[
            { required: true, message: '请输入邮箱地址', trigger: 'change'},
            { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
          ],
          Usernames: [
            { required: true, message: '请输入用户名', trigger: 'change' },
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: ['blur', 'change'] }
          ],

        },
        tableData: [{
          id: '20190102',
          type: '短租',
          address: '上海市普陀区金沙江路 1518 弄',
          status: 'success'
        }, {
          id: '20190203',
          type: '长租',
          address: '上海市普陀区金沙江路 1518 弄',
          status: 'fail'
        }, {
          id: '20190218',
          type: '短租',
          address: '上海市普陀区金沙江路 1518 弄',
          status: 'waiting'
        }, {
          id: '20190228',
          type: '长租',
          address: '上海市普陀区金沙江路 1518 弄',
          status: 'waiting'
        }],
        dialogTableVisible: false,
        dialogFormVisible: false,
        dialogTableVisible2:false,
        activeIndex: '1',
        formLabelWidth: '100px',
        imageUrl:'',
      };
    },
    updated(){
      var email = sessionStorage.getItem("email");
      console.log(email);
      if(email === null)
        this.logininf = "注册/登录";
      else
        this.logininf =  "欢迎"  + "！";
    },
    mounted(){
      var email = sessionStorage.getItem("email");
      console.log(email);
      if(email === null)
        this.logininf = "注册/登录";
      else
        this.logininf =  "欢迎"  + "！";
    },
    methods:{
      exit:function(){
        sessionStorage.clear();
        this.$notify({
          title: '成功',
          message: '您已成功退出',
          type: 'success'
        });
        setTimeout(function () {
            window.location.href = "/services";
        },400)
      },
      deleteRow(index, rows) {
        rows.splice(index, 1);
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.Usernames='';
        this.checkPass='';
      },
      submitFormReg(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const url ='/api/register';
            this.$http({
              method: 'post',
              url:url,
              data:{
                name:this.ruleForm.Usernames,
                password:this.ruleForm.pass,
                email:this.ruleForm.email
              }
            }).then((res)=>{
              if(res.data["ok"] === true){
                this.dialogFormVisible = false;
                this.$notify({
                  title: '成功',
                  message: '您已成功注册,请前往邮箱验证身份信息',
                  type: 'success'
                });
              }
              else if(res.data["name"] === false){
                this.$notify({
                  title: '失败',
                  message: '此用户名已被注册',
                  type: 'warning'
                });
              }
              else if(res.data["email"] === false){
                this.$notify({
                  title: '失败',
                  message: '此邮箱已被注册',
                  type: 'warning'
                });
              }
            }).catch((error) => {
              console.warn(error)
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      submitFormLogin(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const url ='/api/login';
            this.$http({
              method: 'get',
              url:url,
              params:{
                password:this.ruleForm.pass,
                email:this.ruleForm.email
              }
            }).then((res)=>{
              if(res.data["ok"] === true && res.data["is_active"] === true){
                this.dialogFormVisible = false;
                this.$notify({
                  title: '成功',
                  message: '您已成功登录',
                  type: 'success'
                });
                sessionStorage.setItem("email", this.ruleForm.email);
              }
              else if(res.data["ok"] === true && res.data["is_active"] === false){
                this.$notify({
                  title: '失败',
                  message: '请前往邮箱确认您的身份信息',
                  type: 'warning'
                });
              }
              else{
                this.$notify({
                  title: '失败',
                  message: '错误的身份信息',
                  type: 'warning'
                });
              }
            }).catch((error) => {
              console.warn(error)
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });

      },
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      }
    },

  }
</script>
<style scoped>
  .aboutus {
    margin-left: 18%;
  }

  .login {
    margin-left: 2%;
  }

  .input {
    margin-left: 20%;
    width: 25%;
  }

  .avatar {
    margin-left: 10%;

  }

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
  #TopNavigator{
    padding-top: 2%;
  }

</style>



