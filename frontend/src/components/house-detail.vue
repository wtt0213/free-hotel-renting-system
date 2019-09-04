<template>
  <div class="hello">
    <div class="header agileits w3layouts" id="home">
    <el-container style="width: 95%;margin-left: 5%">
      <el-header>
        <TopNavigator></TopNavigator>
      </el-header>
      <el-main>
        <div class="header agileits w3layouts"style="padding-top: 2%">
          <div class="banner agileits w3layouts">
            <img v-bind:src="root+houseInfo.hdpiImages[0]" alt="Agileits W3layouts">
            <h1 class="wow agileits w3layouts fadeInDown">房屋概况</h1>
          </div>
        </div>
        <div class="rooms agileits w3layouts">
          <div class="container">

            <h2 style="color: black;text-align: center">美图展示</h2>
            <div class="rooms-grids agileits w3layouts">
              <div class="rooms-grid agileits w3layouts rooms-grid-1 wow fadeInRight">
                <img v-bind:src="root+houseInfo.mdpiImages[0]" alt="Agileits W3layouts">
              </div>
              <div class="rooms-grid rooms-grid-2 agileits w3layouts wow fadeInLeft">
                <img v-bind:src="root+houseInfo.mdpiImages[1]" alt="Agileits W3layouts"></div>
              <div class="rooms-grid rooms-grid-3 wow fadeInRight agileits w3layouts">
                <img v-bind:src="root+houseInfo.mdpiImages[2]" alt="Agileits W3layouts">
              </div>
              <div class="rooms-grid rooms-grid-4 wow agileits w3layouts fadeInLeft">
                <img v-bind:src="root+houseInfo.mdpiImages[3]" alt="Agileits W3layouts">
              </div>
            </div>

          </div>
        </div>
        <div class="portfolio agileits w3layouts">

          <div class="ga-top agileits w3layouts">

            <div class="col-md-3 col-sm-3 agileits w3layouts portfolio-top">

              <div class="portfolio-item agileits w3layouts wow zoomIn">
                <a class="example-image-link agileits w3layouts" href="/static/images/project-1.jpg"
                   data-lightbox="example-set" data-title="">
                  <div class="grid agileits w3layouts">
                    <figure class="effect-apollo agileits w3layouts">
                      <img v-bind:src="root+houseInfo.ldpiImages[0]" alt="Agileits W3layouts">
                      <figcaption></figcaption>
                    </figure>
                  </div>
                </a>
              </div>

              <div class="portfolio-item wow agileits w3layouts zoomIn">
                <a class="example-image-link agileits w3layouts" href="/static/images/project-2.jpg"
                   data-lightbox="example-set" data-title="">
                  <div class="grid agileits w3layouts">
                    <figure class="effect-apollo agileits w3layouts">
                      <img v-bind:src="root+houseInfo.ldpiImages[1]" alt="Agileits W3layouts">
                      <figcaption></figcaption>
                    </figure>
                  </div>
                </a>
              </div>
              <div class="clearfix"></div>

            </div>

            <div class="col-md-3 col-sm-3 agileits w3layouts portfolio-top">

              <div class="portfolio-item wow agileits w3layouts zoomIn">
                <a class="example-image-link agileits w3layouts" href="/static/images/project-3.jpg"
                   data-lightbox="example-set" data-title="">
                  <div class="grid agileits w3layouts">
                    <figure class="effect-apollo agileits w3layouts">
                      <img v-bind:src="root+houseInfo.ldpiImages[2]" alt="Agileits W3layouts">
                      <figcaption></figcaption>
                    </figure>
                  </div>
                </a>
              </div>

              <div class="portfolio-item agileits w3layouts wow zoomIn">
                <a class="example-image-link agileits w3layouts" href="/static/images/project-4.jpg"
                   data-lightbox="example-set" data-title="">
                  <div class="grid agileits w3layouts">
                    <figure class="effect-apollo agileits w3layouts">
                      <img v-bind:src="root+houseInfo.ldpiImages[3]" alt="Agileits W3layouts">
                      <figcaption></figcaption>
                    </figure>
                  </div>
                </a>
              </div>

              <div class="portfolio-item wow agileits w3layouts zoomIn">
                <a class="example-image-link agileits w3layouts" href="/static/images/project-5.jpg"
                   data-lightbox="example-set" data-title="">
                  <div class="grid agileits w3layouts">
                    <figure class="effect-apollo agileits w3layouts">
                      <img v-bind:src="root+houseInfo.ldpiImages[4]" alt="Agileits W3layouts">
                      <figcaption></figcaption>
                    </figure>
                  </div>
                </a>
              </div>
              <div class="clearfix"></div>

            </div>

            <div class="col-md-3 col-sm-3 agileits w3layouts portfolio-top">

              <div class="portfolio-item agileits w3layouts wow zoomIn">
                <a class="example-image-link agileits w3layouts" href="/static/images/project-6.jpg"
                   data-lightbox="example-set" data-title="">
                  <div class="grid agileits w3layouts">
                    <figure class="effect-apollo agileits w3layouts">
                      <img v-bind:src="root+houseInfo.ldpiImages[5]" alt="Agileits W3layouts">
                      <figcaption></figcaption>
                    </figure>
                  </div>
                </a>
              </div>

              <div class="portfolio-item agileits w3layouts wow zoomIn">
                <a class="example-image-link agileits w3layouts" href="/static/images/project-7.jpg"
                   data-lightbox="example-set" data-title="">
                  <div class="grid agileits w3layouts">
                    <figure class="effect-apollo agileits w3layouts">
                      <img v-bind:src="root+houseInfo.ldpiImages[6]" alt="Agileits W3layouts">
                      <figcaption></figcaption>
                    </figure>
                  </div>
                </a>
              </div>
              <div class="clearfix"></div>

            </div>

            <div class="col-md-3 col-sm-3 agileits w3layouts portfolio-top">

              <div class="portfolio-item wow agileits w3layouts zoomIn">
                <a class="example-image-link agileits w3layouts" href="/static/images/project-8.jpg"
                   data-lightbox="example-set" data-title="">
                  <div class="grid agileits w3layouts">
                    <figure class="effect-apollo agileits w3layouts">
                      <img v-bind:src="root+houseInfo.ldpiImages[7]" alt="Agileits W3layouts">
                      <figcaption></figcaption>
                    </figure>
                  </div>
                </a>
              </div>

              <div class="portfolio-item wow agileits w3layouts zoomIn">
                <a class="example-image-link agileits w3layouts" href="/static/images/project-9.jpg"
                   data-lightbox="example-set" data-title="">
                  <div class="grid agileits w3layouts">
                    <figure class="effect-apollo agileits w3layouts">
                      <img v-bind:src="root+houseInfo.ldpiImages[8]" alt="Agileits W3layouts">
                      <figcaption></figcaption>
                    </figure>
                  </div>
                </a>
              </div>

              <div class="portfolio-item wow agileits w3layouts zoomIn">
                <a class="example-image-link agileits w3layouts" href="/static/images/project-10.jpg"
                   data-lightbox="example-set" data-title="">
                  <div class="grid agileits w3layouts">
                    <figure class="effect-apollo agileits w3layouts">
                      <img v-bind:src="root+houseInfo.ldpiImages[9]" alt="Agileits W3layouts">
                      <figcaption></figcaption>
                    </figure>
                  </div>
                </a>
              </div>
              <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
          </div>
        </div>
        <h1>房屋信息</h1>
        <div class="cuisines agileits w3layouts" style="margin-top: 1%;padding: 3%">
          <el-tooltip style="float: left" class="item" effect="dark" content="点击进行VR预览" placement="right-start">
            <div class="cuisines-grids agileits w3layouts cuisines-grids-2 wow slideInRight" style="width: 40%">
              <img @click="vrGallery=true" src="/static/images/cuisines.jpg" alt="Agileits W3layouts" height="360px">
              <div style="margin-top: 10%;padding-left: 21%">
                <el-button type="success" v-on:click="shortRentP">
                  我要短租
                </el-button>
                <el-button type="warning" v-on:click="longRentP" style="margin-left: 10%">
                  我要长租
                </el-button></div>
            </div>

          </el-tooltip>
          <div style="float: left; margin-left: 8%;">
            <div class="panel panel-default" style="width: 600px">
              <div class="panel-heading">基本信息</div>
              <table class="table table-striped">
                <tr>
                  <td style="width: 20%"><b>区位</b></td>
                  <td>{{houseInfo['city']}}</td>
                </tr>
                <tr>
                  <td style="width: 20%"><b>详细位置</b></td>
                  <td>{{houseInfo['location']}}</td>
                </tr>
                <tr>
                  <td style="width: 20%"><b>短租价格(/天)</b></td>
                  <td>{{houseInfo
                    ['shortPrice']}}
                  </td>
                </tr>
                <tr>
                  <td style="width: 20%"><b>长租价格(/月)</b></td>
                  <td>{{houseInfo['longPrice']}}</td>
                </tr>
                <tr>
                  <td style="width: 20%"><b>评分</b></td>
                  <td>
                    <el-rate
                      v-model="value"
                      disabled
                      text-color="#ff9900"
                      score-template="{value}">
                    </el-rate>
                  </td>
                </tr>
                <tr>
                  <td style="width: 20%"><b>是否可租</b></td>
                  <td>{{IsAvailable}}</td>
                </tr>
                <tr>
                  <td style="width: 20%"><b>几人间</b></td>
                  <td>{{houseInfo['type']}}</td>
                </tr>
                <tr>
                  <td style="width: 20%"><b>描述</b></td>
                  <td>{{houseInfo['description']}}</td>
                </tr>
              </table>
            </div>
          </div>

          <div class="clearfix"></div>

        </div>
        <div style="padding-left: 40%">
          <el-dialog title="短租申请" :visible.sync="shortRentPop" :center="true">
            <p style="padding-top: 3%; margin-bottom: 3%"><span style="font-size:larger;"><b>租客姓名:</b><el-input
              style="margin-left:3%;max-width: 50%" v-model="gridData.name" :disabled="true"></el-input></span></p>
            <p style="padding-top: 3%; margin-bottom: 3%"><span style="font-size: larger;"><b>下单时间:</b><el-input
              style="margin-left:3%;max-width: 50%" v-model="gridData.date" :disabled="true"></el-input></span></p>
            <p style="padding-top: 3%; margin-bottom: 3%"><span style="font-size: larger;"><b>租房地址:</b><el-input
              style="margin-left:3%;max-width: 50%" v-model="gridData.address" :disabled="true"></el-input></span></p>
            <p style="padding-top: 3%; margin-bottom: 3%"><span style="font-size: larger;"><b>选择时间:</b><el-date-picker
              v-model="time1"
              style="margin-left:3%;max-width: 50%"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期" format="yyyy-MM-dd" :picker-options="pickerOptions">
            </el-date-picker></span></p>
            <p style="padding-top: 3%; margin-bottom: 3%"><span style="font-size:larger;"><b>申请描述:</b><el-input
              style="margin-left:3%;max-width: 50%;" v-model="gridData.description" type="textarea"
              :autosize="{ minRows: 2, maxRows: 9}"></el-input></span></p>
            <p style="padding-top: 3%; margin-bottom: 3%"><span style="font-size: larger"><b>总共金额:</b></span><span
              style="color: red;font-size: x-large;margin-left:3%">￥{{money1}}</span></p>

            <div slot="footer" class="dialog-footer">
              <el-button @click="shortRentPop = false">取 消</el-button>
              <el-button type="primary" @click="shortRenter">确 定</el-button>
            </div>
          </el-dialog>


          <el-dialog title="长租申请" :visible.sync="longRentPop" :center="true">
            <p style="padding-top: 3%; margin-bottom: 3%;margin-left:10%"><span style="font-size: larger;"><b>租客姓名:</b><el-input
              style="margin-left:3%;max-width: 50%" v-model="gridData.name"></el-input></span></p>
            <p style="padding-top: 3%; margin-bottom: 3%;margin-left:10%"><span style="font-size: larger;"><b>下单时间:</b><el-input
              style="margin-left:3%;max-width: 50%" v-model="gridData.date"></el-input></span></p>
            <p style="padding-top: 3%; margin-bottom: 3%;margin-left:10%"><span style="font-size: larger;"><b>租房地址:</b><el-input
              style="margin-left:3%;max-width: 50%" v-model="gridData.address"></el-input></span></p>
            <p style="padding-top: 3%; margin-bottom: 3%;margin-left:10%"><span style="font-size: larger;"><b>选择时间:</b><el-date-picker
              v-model="time2"
              style="margin-left:3%;max-width: 50%"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期" format="yyyy-MM-dd" :picker-options="pickerOptions">
            </el-date-picker></span></p>
            <p style="padding-top: 3%;;margin-left:10%"><span style="font-size: larger"><b>总共金额:</b></span><span
              style="color: red;font-size: x-large;margin-left:3%">￥{{money2}}</span></p>
            <div slot="footer" class="dialog-footer">
              <el-button type="primary" @click="longRenter">确 定</el-button>
              <el-button @click="longRentPop = false">取 消</el-button>
            </div>
          </el-dialog>
          <el-dialog title="VR预览" :visible.sync="vrGallery" fullscreen @close="refresh">
            <VR v-bind:message="vr_pic"></VR>
          </el-dialog>

        </div>
      </el-main>

      <el-footer>
        <Footer></Footer>
      </el-footer>

    </el-container>
    </div>


  </div>
</template>

<script>
    import TopNavigator from "./TopNavigator";
    import Footer from "./footer";
    import VR from "./VR";

    export default {
        name: 'house-detail',
        components: {
            TopNavigator,
            Footer,
            VR
        },
        data: function () {
            return {
                vr_pic:[],
                vrGallery:false,
                root: 'https://lzmmmmm.oss-cn-beijing.aliyuncs.com/',
                time1: '',
                time2: '',
                value: 0,
                index: 0,
                input: '',
                shortRentPop: false,
                longRentPop: false,
                gridData: {
                    date: '',
                    name: 'ddddd',
                    address: '',
                    description: ''
                },
                num: 1,
                value1: '1998-07-11',
                value2: '',
                date: ['2017-7-26', '2017-12-18'],
                radio: '1',
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() < Date.now();
                    }
                },
                pickerOptions1: {
                    disabledDate(time) {
                        return (time.getTime() < Date.now());
                    }
                },
                houseInfo: {}
            }
        },

        beforeCreate() {
            console.log(this.$route.query.index);
            this.index = this.$route.query.index;
            const url = '/api/get_room_by_id';
            this.$http({
                method: 'get',
                url: url,
                params: {
                    id: this.index,
                }
            }).then((res) => {
                //alert('success');
                console.log(res.data);
                this.houseInfo = res.data;
                console.log(this.houseInfo);
                this.gridData.date = this.getNowFormatDate();
                this.gridData.address = this.houseInfo['location'];
                this.value = this.houseInfo['mark'];
                this.gridData.name = sessionStorage.getItem('name');
                this.value = this.houseInfo['mark'];
                this.vr_pic = this.houseInfo['vrImages'];
            });
        },
        computed: {
            IsAvailable: function () {
                if (this.houseInfo['available'] === true)
                    return '可租';
                else
                    return '不可租';
            },
            money2: function () {
                let days = this.caculateTime(this.time2).days;
                if (isNaN(days))
                    days = 0;
                let per = 0;
                per = this.houseInfo['longPrice'] / 30;
                return Math.floor(days * per);
            },
            money1: function () {
                let days = this.caculateTime(this.time1).days;
                if (isNaN(days))
                    days = 0;
                let per = 0;
                per = this.houseInfo['shortPrice'];
                return Math.floor(days * per);
            }
        },
        methods: {
            refresh:function(){
                window.history.back();
                window.location.reload();
            },
            caculateTime(Time) {
                let Timer = Time;
                Timer = Timer.toString().split(' ');
                let transer = {
                    'Jan': '01',
                    'Feb': '02',
                    'Mar': '03',
                    'Apr': '04',
                    'May': '05',
                    'Jun': '06',
                    'Jul': '07',
                    'Aug': '08',
                    'Sep': '09',
                    'Oct': '10',
                    'Nov': '11',
                    "Dec": '12'
                };
                let startTime = Timer[3] + '-' + transer[Timer[1]] + '-' + Timer[2];
                let endTime = Timer[9] + '-' + transer[Timer[7]] + '-' + Timer[8];
                let days = this.getDays(startTime, endTime);
                return {
                    days: days,
                    startTime: startTime,
                    endTime: endTime
                };
            },
            Rentp: function (val) {
                let is_Login = sessionStorage.getItem('id');
                let is_available = this.houseInfo['available'];
                if (is_Login && is_available) {
                    if (val === 1)
                        this.shortRentPop = true;
                    else
                        this.longRentPop = true;
                    this.gridData.address = this.$route.query.loc;
                    this.gridData.date = this.getNowFormatDate();
                } else if (!is_Login)
                    this.$notify({
                        title: '失败',
                        message: '注册后才能进行租房活动哟',
                        type: 'warning'
                    });
                else if (!is_available) {
                    this.$notify({
                        title: '失败',
                        message: '此房间暂时无法租赁',
                        type: 'warning'
                    });
                }
            },
            shortRentP: function () {
                this.Rentp(1);
            },
            longRentP: function () {
                this.Rentp(2);
            },
            shortRenter() {
                if (this.time1 == null)
                    alert('请输入时间');
                let Time = this.caculateTime(this.time1);
                let startTime = Time.startTime;
                let endTime = Time.endTime;
                let tenant_id = sessionStorage.getItem('id');
                let houseId = this.$route.query.index;
                let content = this.gridData.description;
                //console.log("tenant_id" + tenant_id + " start_time" + startTime + " end_Time" + endTime + "roomId" + houseId);
                let rentID = '';
                this.$http({
                    method: 'post',
                    url: '/api/short_rent/save',
                    data: {
                        tenant_id: tenant_id,
                        start_time: startTime,
                        end_time: endTime,
                        room_id: houseId
                    }
                }).then((res) => {
                    //console.log(res.data);
                    rentID = res.data['id'];
                    this.$notify({
                        title:'成功',
                        message:"成功提交短租申请，请等待管理员审核",
                        type:"success"
                    });
                    this.shortRentPop = false;
                }).catch((error) => {
                    console.warn(error)
                });
            },
            longRenter() {
                let Time = this.caculateTime(this.time2);
                let startTime = Time.startTime;
                let endTime = Time.endTime;
                let tenant_id = sessionStorage.getItem('id');
                let houseId = this.$route.query.index;
                if (this.getDays(startTime, endTime) < 60) {
                    this.$notify({
                       title:"失败",
                       message:"长租至少要60天",
                       type:"warning"
                    });
                } else {
                    this.$http({
                        method: 'post',
                        url: '/api/long_rent/save',
                        data: {
                            tenant_id: tenant_id,
                            start_time: startTime,
                            end_time: endTime,
                            room_id: houseId
                        }
                    }).then((res) => {
                        this.$notify({
                           title:"成功",
                           message:"成功提交长租申请，请等待管理员审核",
                           type:"success"
                        });
                    }).catch((error) => {
                        console.warn(error)
                    });
                    this.longRentPop = false;
                }
            },

        },
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .tag {
    margin: 3%;
  }
</style>
