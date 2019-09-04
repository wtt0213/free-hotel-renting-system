<template>
  <el-container style="width: 90%;margin-left: 5%">
    <el-header>
      <server-top-navigator></server-top-navigator>
    </el-header>
    <el-main>
      <div class="row" style="padding-top: 3%">
        <el-tabs :tab-position="tabPosition" style="height:auto;width:70%;margin-left:10%;">
          <el-tab-pane label="师傅信息">
            <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
              <el-form-item label="我的工号">
                <el-input disabled="disabled" v-model="formLabelAlign.name"></el-input>
              </el-form-item>
              <el-form-item label="密码">
                <el-input type="password" v-model="formLabelAlign.password"><el-button slot="append" icon="el-icon-edit" @click="changepwd"></el-button></el-input>
              </el-form-item>
              <el-form-item label="手机号">
                <el-input v-model="formLabelAlign.phone"><el-button slot="append" icon="el-icon-edit" @click="changephone"></el-button></el-input>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="我的工单">
            <el-table :data="this.workerOrder" border="true">
              <el-table-column property="workerId" label="工单编号"></el-table-column>
              <el-table-column property="tenant_name" label="用户姓名"></el-table-column>
              <el-table-column property="address" label="租户住址"></el-table-column>
              <el-table-column property="description" label="描述"></el-table-column>
              <el-table-column property="status" label="订单状态"></el-table-column>
              <el-table-column label="操作" width="120">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="primary"
                    @click="returnOrder(scope.$index)"
                  >回复订单
                  </el-button>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="120">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)"
                  >完成订单
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

          </el-tab-pane>


        </el-tabs>
      </div>
    </el-main>

    <div class="line"></div>
    <el-footer>
      <Footer></Footer>
      </el-footer>
  </el-container>
</template>

<script>
    import ServerTopNavigator from "./serverTopNavigator";
    import Footer from "../components/footer";

    export default {
        name: "serverBody",
        components: {ServerTopNavigator,Footer},
        data: function () {
            return {
                yes: false,
                yes2: true,
                myRouter: '/services',
                Number: '12345678',
                renters: [],
                orderData: [],
                ShortRentData: [],
                dialogTableVisible: false,
                LongRentData: [],
                houseData: [],
                formLabelAlign: {
                    name: 'www',
                    password: '',
                    phone: '',
                },
                workerOrder: [],
            }
        },
        beforeCreate() {
            this.$http({
                method: "get",
                url: "/api/get_all_work_orders_By_worker_Id",
                params: {
                    worker_id: sessionStorage.getItem('id')
                }
            }).then((res) => {
                this.workerOrder = res.data;
                for (let i = 0; i < res.data.length; i++) {
                    if (this.workerOrder[i]['is_complete'] === false)
                        this.workerOrder[i]['status'] = "未完成";
                    else
                        this.workerOrder[i]['status'] = "已完成";
                }
            })
        },
        mounted() {
            this.checkLogin(2);
            this.formLabelAlign.name = sessionStorage.getItem('workername');
            this.formLabelAlign.password = "*****";
            this.formLabelAlign.phone = sessionStorage.getItem('phone');
        },
        methods: {
            changephone(){
                this.$prompt('请输入您要设置的新密码', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                }).then(({value}) => {
                    this.$http({
                        method:"post",
                        url:"/api/worker/update/phone",
                        data:{
                            id:sessionStorage.getItem('id'),
                            phone:value
                        }
                    }).then((res)=>{
                        this.$notify({
                           title:"成功",
                            message:"您的电话已修改",
                            type:"success"
                        });
                    });
                });
            },
            changepwd(){
                this.$prompt('请输入您要设置的新密码', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                }).then(({value}) => {
                });
            },
            returnOrder(index) {
                if (this.workerOrder[index]['is_complete'] === false) {
                    this.$notify({
                        title: "错误",
                        message: "维修还未结束！",
                        type: "warning"
                    })
                } else {
                    this.$prompt('请输入回复订单的内容', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                    }).then(({value}) => {
                        this.$http({
                            method:"post",
                            url:"/api/review_workerOrder",
                            data:{
                                id:this.workerOrder[index]['id'],
                                mark:5,
                                review:value
                            }
                        }).then((res)=>{
                            this.$notify({
                                title:"成功",
                                message:"您已成功回复",
                                type:"success"
                            })
                        })
                    });
                }
            },
            open() {
                this.$alert('联系方式:' + this.Number);
            },
            openNew() {
                window.open('/rentDetail');
            },
            deleteRow(index, rows) {
                rows.splice(index, 1);
            },
            handleShortRentDelete(index, row) {
                this.tmpIndex = index;
                this.dialogAlert = true;
            },
            handleDelete(index, row) {
                this.$confirm('确认此工单已完成？')
                    .then(_ => {
                        this.$http({
                           method:"post",
                           url:"/api/complete_work_order",
                            data:{
                               id:this.workerOrder[index]['id'],
                            }
                        }).then((res)=>{
                            console.log(res.data);
                            this.$notify({
                                title:"完成",
                                message:"工单已完成",
                                type:"success"
                            });
                            window.location.reload();
                            this.workerOrder[index]['status'] = "已完成";
                        })
                    })
                    .catch(_ => {});
            }
        },
    }
</script>

<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }


  body {
    font-family: 'Open Sans', sans-serif;
    line-height: 28px;

  }

  .content-wrapper {
    margin-top: 40px;
    min-height: 800px;
    padding-bottom: 90px;
  }

  .header-line {
    font-weight: 900;
    padding-bottom: 25px;
    border-bottom: 1px solid #eeeeee;
    text-transform: uppercase;
  }

  .pad-botm {
    padding-bottom: 30px;
  }


  #menu-top a {
    color: #000;
    text-decoration: none;
    font-weight: 500;
    padding: 25px 15px 25px 15px;
    text-transform: uppercase;

  }


  .navbar-inverse .navbar-collapse, .navbar-inverse .navbar-form {
    border-color: transparent;
  }


  .img-comments {
    border: 3px double #e1e1e1;
    height: 60px;

  }

  .chat-widget-main {
    max-height: 330px;
    overflow: auto;
  }

  .slide-bdr {
    border: 5px solid #9170E4
  }

  .chat-widget-left:after {
    top: 100%;
    left: 10%;
    border: solid transparent;
    content: " ";
    position: absolute;
    border-top-color: #F70E62;
    border-width: 15px;
    margin-left: -15px;
  }

  .chat-widget-left {
    width: 100%;
    height: auto;
    padding: 15px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    position: relative;
    border: 1px solid #F70E62;
    font-size: 12px;
  }

  .chat-widget-right:after {
    top: 100%;
    right: 10%;
    border: solid transparent;
    content: " ";
    position: absolute;
    border-top-color: #5AA8CC;
    border-width: 15px;
    margin-left: -15px;
  }

  .chat-widget-right {
    width: 100%;
    height: auto;
    padding: 15px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    position: relative;
    border: 1px solid #5AA8CC;
    font-size: 12px;
  }

  .chat-widget-name-left {
    color: #808080;
    margin-top: 30px;
    margin-left: 60px;
    text-align: left;
  }

  .img-left-chat {
    border: 3px double #e1e1e1;
    float: left;
    margin-right: 30px;
    width: 20%;
    height: 20%
  }

  .img-right-chat {
    border: 3px double #e1e1e1;
    float: right;
    margin-left: 30px;
    width: 20%;
    height: 20%
  }

  .chat-widget-main img-right {
    border: 3px double #e1e1e1;
    float: left;
  }

  .chat-widget-name-left h4 {
    font-weight: normal;
    font-size: 11px;

  }

  .chat-widget-name-left h5 {
    font-weight: normal;
    font-size: 10px;
  }

  .chat-widget-name-right h4 {
    font-weight: normal;
    font-size: 11px;
  }

  .chat-widget-name-right h5 {
    font-weight: normal;
    font-size: 10px;
  }

  .chat-widget-name-right {
    color: #808080;
    margin-top: 40px;
    margin-right: 60px;
    text-align: right;
  }

  .recent-users-sec img {
    max-height: 80px;
    margin: 15px;
  }

  .back-widget-set {
    background-color: transparent;
  }


  .footer-section a, .footer-section a:hover {
    color: #000;
  }


</style>
