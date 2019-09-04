<template>
  <div id="TopNavigator">
    <nav class="navbar navbar-inverse">
      <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                  data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/">青年租房</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div style="margin-left: 20%" class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <form class="navbar-form navbar-left">
            <div class="form-group">
              <input type="text" class="form-control" placeholder="Search">
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
          </form>
          <ul class="nav navbar-nav navbar-right">
            <li style="padding-top: 5%">
              <el-dropdown>
                <el-badge v-bind:value=is_new style="margin-right: 40px"><i class="el-icon-bell"></i></el-badge>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item v-on:click.native="confirmNotice(index)" v-for="(item,index) in notice.slice(0,5)" :key="index" divided>{{item['content']}}
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </li>
            <li><a v-on:click="loginAction">{{logininf}}</a></li>
            <li><a v-on:click="toInfoCenter">个人中心</a></li>
            <li><a v-on:click="exit"><i class="el-icon-delete"></i></a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>

    <el-dialog title="联系我们" :visible.sync="dialogTableVisible">
      <contactUs></contactUs>
    </el-dialog>
    <el-dialog title="注册/登录" :visible.sync="dialogFormVisible">
      <el-tabs v-model="activeName" type="card">

        <el-tab-pane label="注册" name="first">

          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                   class="demo-ruleForm">
            <el-form-item label="邮箱" prop="email">
              <el-input type="email" v-model="ruleForm.email" placeholder="请填写正确格式的邮箱"></el-input>
            </el-form-item>
            <el-form-item label="用户名" prop="Usernames">
              <el-input v-model="ruleForm.Usernames" placeholder="请输入3-10长度字符"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="pass">
              <el-input type="password" v-model="ruleForm.pass" autocomplete="off" placeholder="输入密码"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPass">
              <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off" placeholder="确认密码"></el-input>
            </el-form-item>
            <el-form-item label="验证" prop="Verify">
              <Slider @verify="Verify1"></Slider>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitFormReg('ruleForm')">提交</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>

        </el-tab-pane>

        <el-tab-pane label="登录" name="second">
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                   class="demo-ruleForm">
            <el-form-item label="邮箱" prop="email">
              <el-input type="email" v-model="ruleForm.email"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="pass">
              <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="验证" prop="Verify">
              <Slider @verify="Verify2"></Slider>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitFormLogin('ruleForm')">登录</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script>
    import contactUs from "./contactUs";
    import Slider from "./Slider"

    export default {
        name: "TopNavigator",
        components: {
            contactUs,
            Slider
        },
        data() {
            let validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            let validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                logininf: "",
                ruleForm: {
                    pass: '',
                    checkPass: '',
                    email: '',
                    Usernames: '',
                    Id: ''
                },
                input4: '',
                activeName: 'first',
                rules: {
                    pass: [
                        {required: true, validator: validatePass, trigger: 'blur'}
                    ],
                    checkPass: [
                        {required: true, validator: validatePass2, trigger: 'change'}
                    ],
                    email: [
                        {required: true, message: '请输入邮箱地址', trigger: 'change'},
                        {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
                    ],
                    Usernames: [
                        {required: true, message: '请输入用户名', trigger: 'change'},
                        {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: ['blur', 'change']}
                    ],

                },

                dialogTableVisible: false,
                dialogFormVisible: false,
                activeIndex: '1',
                formLabelWidth: '100px',
                imageUrl: '',
                notice: [],
                is_new: "",
                isVerify1: '',
                isVerify2: ''
            };
        },
        beforeCreate() {
            this.$http({
                method: "get",
                url: "/api/notice/get_by_tenant_id",
                params: {
                    tenant_id: sessionStorage.getItem('id')
                }
            }).then((res) => {
                for(let i = 0; i < res.data.length; i++){
                    if(res.data[i]['read'] === false)
                        this.notice.push(res.data[i]);
                }
                console.log(this.notice);
                try {
                    for (let i = 0; i < this.notice.length; i++) {
                        if (this.notice[i]['read'] === false) {
                            this.is_new = "new";
                            break;
                        }
                    }
                } catch (e) {
                    console.log(e);
                }
            }).catch((error) => {
                console.log(error);
            })
        },
        mounted() {

            let email = sessionStorage.getItem("email");
            console.log(email);
            if (email === null)
                this.logininf = "注册/登录";
            else
                this.logininf = "欢迎" + sessionStorage.getItem('name') + "！";


        },
        methods: {
            confirmNotice(index){
                this.$http({
                    method:"post",
                    url:"/api/notice/set_read",
                    data:{
                        notice_id:this.notice[index]['id']
                    }
                }).then((res)=>{
                    window.location.href = "/usercenter"
                })
            },
            loginAction(){
                if(!sessionStorage.getItem('email'))
                    this.dialogFormVisible = true;
                else
                    this.notify({
                        title:"失败",
                        message:"您已经登陆,请注销后操作",
                        type:"warning"
                    })
            },
            Verify1(data) {
                if (data == "1") {
                    this.isVerify1 = true;
                }
            },
            Verify2(data) {
                if (data == "1") {
                    this.isVerify2 = true;
                }
            },
            toInfoCenter: function () {
                let is_login = sessionStorage.getItem('id');
                if (is_login)
                    window.location.href = "/usercenter"
                else
                    this.$notify({
                        title: '失败',
                        message: '请登录',
                        type: 'warning'
                    });
            },
            exit: function () {
                sessionStorage.clear();
                this.logininf = "登录/注册";
                this.$notify({
                    title: '成功',
                    message: '您已成功退出',
                    type: 'success'
                });
                setTimeout(function () {  //使用  setTimeout（）方法设定定时2000毫秒
                    window.location.reload();//页面刷新
                }, 500);
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
                this.Usernames = '';
                this.checkPass = '';
            },
            submitFormReg(formName) {
                if (!this.isVerify1) {
                    this.$notify({
                        title: '错误',
                        message: "请验证",
                        type: "warning"
                    })
                } else {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            const url = '/api/register';
                            this.$http({
                                method: 'post',
                                url: url,
                                data: {
                                    name: this.ruleForm.Usernames,
                                    password: this.$md5(this.ruleForm.pass),
                                    email: this.ruleForm.email
                                }
                            }).then((res) => {
                                if (res.data["ok"] === true) {
                                    this.dialogFormVisible = false;
                                    this.$notify({
                                        title: '成功',
                                        message: '您已成功注册,请前往邮箱验证身份信息',
                                        type: 'success'
                                    });
                                } else if (res.data["name"] === false) {
                                    this.$notify({
                                        title: '失败',
                                        message: '此用户名已被注册',
                                        type: 'warning'
                                    });
                                } else if (res.data["email"] === false) {
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
                }

            },
            submitFormLogin(formName) {
                if (!this.isVerify2) {
                    this.$notify({
                        title: '错误',
                        message: "请验证",
                        type: "warning"
                    })
                } else {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            const url = '/api/login';
                            this.$http({
                                method: 'get',
                                url: url,
                                params: {
                                    password: this.$md5(this.ruleForm.pass),
                                    email: this.ruleForm.email
                                }
                            }).then((res) => {
                                if (res.data["ok"] === true && res.data["is_active"] === true) {
                                    this.dialogFormVisible = false;
                                    this.$notify({
                                        title: '成功',
                                        message: '您已成功登录',
                                        type: 'success'
                                    });
                                    sessionStorage.setItem("email", this.ruleForm.email);
                                    sessionStorage.setItem("id", res.data['tenant_id']);
                                    sessionStorage.setItem("name", res.data['name']);
                                    this.logininf = "欢迎" + sessionStorage.getItem('name') + "！";
                                    setTimeout(function () {  //使用  setTimeout（）方法设定定时2000毫秒
                                        window.location.reload();//页面刷新
                                    }, 500);
                                } else if (res.data["ok"] === true && res.data["is_active"] === false) {
                                    this.$notify({
                                        title: '失败',
                                        message: '请前往邮箱确认您的身份信息',
                                        type: 'warning'
                                    });
                                } else {
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
                }


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
    margin-left: 5%;
  }

  .input {
    margin-left: 10%;
    width: 25%;
  }

  .avatar {
    margin-left: 5%;
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

  #TopNavigator {
    padding-top: 2%;
  }

</style>
