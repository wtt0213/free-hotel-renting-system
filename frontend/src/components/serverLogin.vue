<template>
  <div class="login-container">
    <div>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
               class="demo-ruleForm login-page">
        <h3 class="title" style="margin-bottom:10%">服务系统登录</h3>
        <el-form-item label="用户名" prop="Usernames">
          <el-input v-model="ruleForm.Usernames" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off" placeholder="输入密码"></el-input>
        </el-form-item>
        <el-form-item label="身份选择" prop="Id">
          <el-select v-model="ruleForm.Id" placeholder="身份选择">
            <el-option label="管理员" value="server" @click.native="is_Worker=false"></el-option>
            <el-option label="维修师" value="worker" @click.native="is_Worker=true"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="滑动验证">
          <Slider @verify="Verify"></Slider>
        </el-form-item>
        <el-form-item style="width:50%;">
          <el-button type="primary" style="width:40%;" @click="handleSubmit" :loading="logining">登录</el-button>
          <el-button type="primary" style="width:40%;" @click="handleSubmit" :loading="logining">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
    import Slider from './Slider'

    export default {
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                }
            };
            return {
                is_verify: false,
                is_Worker: false,
                logining: false,
                is_Id: false,
                is_Name: false,
                is_Pass: false,
                ruleForm: {
                    pass: '',
                    email: '',
                    Usernames: '',
                    Id: ''
                },
                rules: {
                    pass: [
                        {required: true, validator: validatePass, trigger: 'blur'}
                    ],
                    email: [
                        {required: true, message: '请输入邮箱地址', trigger: 'blur'},
                        {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
                    ],
                    Usernames: [
                        {required: true, message: '请输入用户名', trigger: 'blur'}
                    ],
                    Id: [
                        {required: true, message: '请选择身份', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            handleSubmit() {
                if (this.ruleForm.name !== '')
                    this.is_Name = true;
                if (this.ruleForm.Id !== '')
                    this.is_Id = true;
                if (this.ruleForm.pass !== '')
                    this.is_Pass = true;
                if (this.is_verify && this.is_Name && this.is_Pass && this.is_Id) {
                    this.ServerLogin();
                } else {
                    alert("请先填写必要信息")
                }
            },
            Verify(data) {
                if (data === "1") {
                    this.is_verify = true;
                }
            },
            ServerLogin() {
                let type = '';
                if (!this.is_Worker) {
                    type = 'administrator';
                }
                this.$http({
                    method: 'get',
                    url: '/api/service_login',
                    params: {
                        password: this.$md5(this.ruleForm.pass),
                        name: this.ruleForm.Usernames,
                        type: type
                    }
                }).then((res) => {
                    if (res.data["ok"] === true) {
                        console.log(res.data);
                        sessionStorage.clear();
                        sessionStorage.setItem('id', res.data.id);
                        sessionStorage.setItem('workername', this.ruleForm.Usernames);
                        sessionStorage.setItem('phone', res.data.phone);
                        this.$notify({
                            title: '成功',
                            message: '您已成功登录',
                            type: 'success'
                        });
                        if (!this.is_Worker)
                            window.location.href = '/services/clientService';
                        else
                            window.location.href = '/services/fixerService';
                    } else {
                        alert('用户密码不正确，请重新填写');
                    }
                }).catch((error) => {
                    console.warn(error)
                });
            }
        },
        components: {
            Slider
        }
    };
</script>

<style scoped>
  .login-container {
    width: 100%;
    height: 100%;
  }

  #background {
    position: absolute;
    left: 0px;
    top: 0px;
    z-index: -1;
    width: 100%;
    height: 100%;
    display: block;
  }

  .login-page {
    -webkit-border-radius: 5px;
    border-radius: 5px;
    top: 33%;
    left: 30%;
    margin-top: -230px;
    width: 650px;
    padding: 35px 35px 15px;
    background: rgba(253, 253, 253, 0.5);
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    position: absolute;
  }
</style>
