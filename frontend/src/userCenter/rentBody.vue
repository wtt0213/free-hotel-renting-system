<template>
  <div>
    <el-row style="margin-top:2%">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="我的租房申请" name="first">
          <h2>我的租房申请</h2>
          <el-tabs v-model="activeIndex" type="card" style="max-width: 80%;margin-left:10%">
            <el-tab-pane label="短租" name="1">
              <el-table :data="shortRent.slice(6*(currentpage1-1),6*currentpage1)">
                <!-- <el-table-column property="iijdd" label="申请编号" width="150"></el-table-column> -->
                <el-table-column property="type" label="租房类型" width="200"></el-table-column>
                <el-table-column property="address" label="租房地址"></el-table-column>
                <el-table-column property="startDate" label="开始时间"></el-table-column>
                <el-table-column property="endDate" label="截止时间"></el-table-column>
                <el-table-column property="price" label="总金额"></el-table-column>

                <el-table-column property="_status" label="状态">
                  <template slot-scope="scope">
                    <el-popover trigger="hover" placement="top">
                      <p>
                        <el-steps
                          :active="scope.row.activeNum"
                          align-center
                          finish-status="success"
                          :process-status="scope.row.process_status"
                        >
                          <el-step title="已申请" description="您的租房申请已成功提交。"></el-step>

                          <el-step
                            v-if="scope.row._status==='未审核'"
                            title="正在审核"
                            description="客服正在处理您的租房申请。"
                          ></el-step>
                          <el-step
                            v-else-if="scope.row._status==='审核未通过'"
                            title="审核未通过"
                            description="很抱歉，您的租房申请未通过审核。"
                          ></el-step>
                          <el-step v-else title="审核通过" description="您的租房申请已经审核通过。"></el-step>

                          <el-step
                            v-if="scope.row.payed"
                            title="已缴费"
                            description="您已成功缴纳该短期租房的全部房租。"
                          ></el-step>
                          <el-step v-else title="未缴费" description="您还没有缴纳房租，请尽快缴纳。"></el-step>

                          <el-step v-if="scope.row.isLive && scope.row.payed" title="入住"></el-step>
                          <el-step v-else-if="scope.row.end" title="入住结束"></el-step>
                          <el-step v-else title="未入住"></el-step>
                        </el-steps>
                      </p>
                      <div slot="reference" class="name-wrapper">
                        <el-tag size="medium">{{ scope.row._status }}</el-tag>
                      </div>
                    </el-popover>
                  </template>
                </el-table-column>


                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <el-dropdown>
                      <span class="el-dropdown-link">
                        更多
                        <i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                      <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item @click.native="handleShortRentPay(scope.$index, scope.row)">缴租
                        </el-dropdown-item>
                        <el-dropdown-item
                          @click.native="handleShortRentDelete(scope.$index, scope.row)"
                        >撤销
                        </el-dropdown-item>
                        <el-dropdown-item @click.native="dialogAlertFixMS(scope.$index)">我要报修</el-dropdown-item>
                      </el-dropdown-menu>
                    </el-dropdown>
                  </template>
                </el-table-column>


              </el-table>
              <el-dialog title="请对你的房间维修信息进行填写" :visible.sync="dialogAlertFixS" width="50%">
                <el-form
                  title="报修申请"
                  :model="ruleForm"
                  status-icon
                  :rules="rules"
                  style="max-width: 80%;margin-left:10%"
                  ref="ruleForm"
                  label-width="100px"
                  class="demo-ruleForm"
                >
                  <h2 style="margin-bottom:2%">保修申请</h2>
                  <el-form-item label="邮箱" prop="email">
                    <el-input type="email" v-model="ruleForm.email" placeholder="请填写正确格式的邮箱"></el-input>
                  </el-form-item>
                  <el-form-item label="保修描述" prop="desc">
                    <el-input
                      type="textarea"
                      v-model="ruleForm.desc"
                      autocomplete="off"
                      placeholder="输入报修描述"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="损坏物品图片">
                    <Plupload4 @fun="addUrl1"></Plupload4>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="submitFormRegShort('ruleForm',shortRent)">提交</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                  </el-form-item>
                </el-form>
              </el-dialog>
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange1"
                :current-page.sync="currentpage1"
                :page-size="6"
                layout="prev, pager, next, jumper"
                :total="currentpagesum1"
                class="page-divided"
                style="padding-left: 35%"
              ></el-pagination>
            </el-tab-pane>

            <el-tab-pane label="长租" name="2">
              <el-table :data="longRent.slice(6*(currentpage2-1),6*currentpage2)">
                <el-table-column property="type" label="租房类型" width="200"></el-table-column>
                <el-table-column property="address" label="租房地址"></el-table-column>
                <el-table-column property="startDate" label="开始时间"></el-table-column>
                <el-table-column property="endDate" label="截止时间"></el-table-column>
                <el-table-column property="longPrice" label="月租金额"></el-table-column>

                <el-table-column property="_status" label="状态">
                  <template slot-scope="scope">
                    <el-popover trigger="hover" placement="top">
                      <p>
                        <el-steps
                          :active="scope.row.activeNum"
                          align-center
                          finish-status="success"
                          :process-status="scope.row.process_status"
                        >
                          <el-step title="已申请" description="您的租房申请已成功提交。"></el-step>

                          <el-step
                            v-if="scope.row._status==='未审核'"
                            title="正在审核"
                            description="客服正在处理您的租房申请。"
                          ></el-step>
                          <el-step
                            v-else-if="scope.row._status==='审核未通过'"
                            title="审核未通过"
                            description="很抱歉，您的租房申请未通过审核。"
                          ></el-step>
                          <el-step v-else title="审核通过" description="您的租房申请已经审核通过。"></el-step>

                          <el-step
                            v-if="scope.row.pass"
                            title="合同已签署"
                            description="您已在线下和客服人员签署长期租房合同。"
                          ></el-step>
                          <el-step v-else title="合同未签署" description="请您尽快查看、打印，并签署合同。"></el-step>

                          <el-step v-if="scope.row.isLive && scope.row.pass" title="入住" description="您已开始入住。"></el-step>
                          <el-step v-else-if="scope.row.end" title="入住结束"></el-step>
                          <el-step v-else title="未入住"></el-step>
                        </el-steps>
                      </p>
                      <div slot="reference" class="name-wrapper">
                        <el-tag size="medium">{{ scope.row._status }}</el-tag>
                      </div>
                    </el-popover>
                  </template>
                </el-table-column>

                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <el-dropdown>
                      <span class="el-dropdown-link">
                        更多
                        <i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                      <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item @click.native="payLongRent(scope.$index)">缴租</el-dropdown-item>
                        <el-dropdown-item @click.native="handleExtendEndTime(scope.$index)">续约</el-dropdown-item>
                        <el-dropdown-item
                          @click.native="handleLongRentDelete(scope.$index, scope.row)"
                        >撤销
                        </el-dropdown-item>
                        <el-dropdown-item @click.native="dialogAlertFixML(scope.$index)">我要报修</el-dropdown-item>
                      </el-dropdown-menu>
                    </el-dropdown>
                  </template>
                </el-table-column>
              </el-table>
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange2"
                :current-page.sync="currentpage2"
                :page-size="6"
                layout="prev, pager, next, jumper"
                :total="currentpagesum2"
                class="page-divided"
                style="padding-left: 35%"
              ></el-pagination>
              <div style="margin-top: 10px">
                <el-button type="info" round @click="showContract">查看合同</el-button>
              </div>
            </el-tab-pane>
          </el-tabs>
        </el-tab-pane>
        <el-tab-pane label="我的报修" name="second">
          <h2>我的报修</h2>
          <el-table style="width: 90%;padding-left: 10%" :data="repairData.slice(6*(currentpage3-1),6*currentpage3)">
            <el-table-column property="rentId" label="租房ID"></el-table-column>
            <el-table-column property="description" label="报修描述"></el-table-column>
            <el-table-column property="_status" label="报修状态">
              <template slot-scope="scope">
                <el-popover trigger="hover" placement="top" v-show="isLeagal">
                  <p>
                    <el-steps :active=scope.row.activeNum align-center finish-status="success"
                              :process-status=scope.row.process_status>
                      <el-step title="已申请" description="您的租房申请已成功提交。"></el-step>

                      <el-step v-if="scope.row._status==='工单未处理'" title="未受理" description="客服暂未处理您的报修。"></el-step>
                      <el-step v-else title="已受理" description="客服处理您的报修，请等待师傅维修"></el-step>


                      <el-step v-if="scope.row._status==='工单未处理'" title="未处理" description="您的订单暂未被受理"></el-step>
                      <el-step v-else-if="scope.row._status==='工单进行中'" title="处理中" description="师傅正在维修，请耐心等候"></el-step>
                      <el-step v-else title="已完成" description="您的报修已经处理完成"></el-step>

                      <el-step v-if="scope.row._status==='工单已完成'" title="完成"></el-step>
                      <el-step v-else title="未完成"></el-step>

                    </el-steps>
                  </p>
                  <div slot="reference" class="name-wrapper" v-show="isLeagal">
                    <el-tag size="medium">{{ scope.row._status }}</el-tag>
                  </div>
                </el-popover>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="primary" @click="fixDetail(scope.$index)" v-show="isLeagal">
                  查看详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="我的举报" name="third">
          <h2>我的举报</h2>
          <el-tabs v-model="this.activeIndex2" type="card" style="max-width: 80%;margin-left:10%">
            <el-tab-pane label="已回复举报" name="1">
              <el-table :data="ComplainReplied.slice(6*(currentpage4-1),6*currentpage4)">
                <el-table-column label="举报id" property="id">
                </el-table-column>
                <el-table-column label="举报内容" property="description">
                </el-table-column>
                <el-table-column label="回复内容" property="review">
                </el-table-column>
              </el-table>
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange4"
                :current-page.sync="currentpage4"
                :page-size="6"
                layout="prev, pager, next, jumper"
                :total="currentpagesum4"
                class="page-divided"
                style="padding-left: 35%;margin-top:20px"
              ></el-pagination>
              <el-button style="margin-top: 20px" type="primary" @click="DialogForComplaint=true">我要举报</el-button>
            </el-tab-pane>

            <el-tab-pane label="未回复举报" name="2">
              <el-table :data="ComplainNoReplied.slice(6*(currentpage5-1),6*currentpage5)">
                <el-table-column label="举报id" property="id">
                </el-table-column>
                <el-table-column label="举报内容" property="description">
                </el-table-column>
              </el-table>
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange5"
                :current-page.sync="currentpage5"
                :page-size="6"
                layout="prev, pager, next, jumper"
                :total="currentpagesum5"
                class="page-divided"
                style="padding-left: 35%;margin-top:20px"
              ></el-pagination>
              <el-button style="margin-top: 20px" type="primary" @click="DialogForComplaint=true">我要举报</el-button>
            </el-tab-pane>
          </el-tabs>
        </el-tab-pane>
        <el-tab-pane label="个人信息" name="fourth">
          <h2 style="margin-bottom:2%">个人资料</h2>
          <el-form
            :label-position="labelPosition"
            label-width="80px"
            :model="formLabelAlign"
            style="max-width: 80%;margin-left:10%"
          >
            <el-form-item label="邮箱">
              <el-input :disabled="true" v-model="formLabelAlign.email"></el-input>
            </el-form-item>
            <el-form-item label="用户名">
              <el-input v-model="formLabelAlign.name">
                <el-button slot="append" icon="el-icon-edit" @click="userDialogVisible = true"></el-button>
              </el-input>
              <el-dialog title="提示" :visible.sync="userDialogVisible" width="30%" center>
                <span>您确定要更改吗？</span>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="userDialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="changeName">确 定</el-button>
                </span>
              </el-dialog>
            </el-form-item>
            <el-form-item label="密码">
              <el-input type="password" v-model="formLabelAlign.password">
                <el-button slot="append" icon="el-icon-edit" @click="pwdDialogVisible=true"></el-button>
              </el-input>
              <el-dialog title="提示" :visible.sync="pwdDialogVisible" width="30%" center>
                <span>您确定要更改吗？</span>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="pwdDialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="changePwd">确 定</el-button>
                </span>
              </el-dialog>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-row>

    <el-dialog title="确定要撤销租房申请吗？" :visible.sync="dialogAlert" width="30%">
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogAlert = false">取 消</el-button>
        <el-button type="primary" @click="deleteShortRent">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="报修详情" :visible.sync="dialogFixDetail" width="30%">
      <el-form ref="form" label-width="80px" border>
        <el-divider></el-divider>
        <el-form-item label="师傅工号:">
          <span>{{this.repairData[INDEX3].worker_name}}</span>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="报修描述:">
          <span>{{this.repairData[INDEX3].description}}</span>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="报修图片:" style="width: 300px">
          <img v-for="item in repairData[this.INDEX3].images" v-bind:src="item"/>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item>
          <el-button type="primary" style="width: 100px" @click="dialogFixDetail=false">确定</el-button>
          <el-button type="primary" style="width: 100px" @click="DialogForScore=true">打分师傅</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="确定要撤销租房申请吗？" :visible.sync="dialogAlertLong" width="30%">
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogAlertLong = false">取 消</el-button>
        <el-button type="primary" @click="deleteLongRent">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="请对你的房间维修信息进行填写" :visible.sync="dialogAlertFixL" width="50%">
      <el-form
        title="保修申请"
        :model="ruleForm"
        status-icon
        :rules="rules"
        style="max-width: 80%;margin-left:10%"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <h2 style="margin-bottom:2%">保修申请</h2>
        <el-form-item label="邮箱" prop="email">
          <el-input type="email" v-model="ruleForm.email" placeholder="请填写正确格式的邮箱"></el-input>
        </el-form-item>
        <el-form-item label="保修描述" prop="desc">
          <el-input type="textarea" v-model="ruleForm.desc" autocomplete="off" placeholder="输入报修描述"></el-input>
        </el-form-item>
        <el-form-item label="上传损坏物品图片" prop="pic">
          <plupload4 @fun="addUrl2"></plupload4>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitFormRegLong('ruleForm',longRent)">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="我要举报" :visible.sync="DialogForComplaint">
      <el-form
        title="举报"
        style="max-width: 80%;margin-left:10%"
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="投诉信息" prop="desc">
          <el-input
            type="textarea"
            v-model="ruleForm.desc"
            autocomplete="off"
            placeholder="输入投诉理由"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitRequest('ruleForm')">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="长租续约" :visible.sync="dialogFormExtendEndTimeVisible">
      <el-form>
        <el-form-item label="续租月数" :label-width="formLabelWidth">
          <el-select v-model="extendNum" placeholder="请选择续租月数">
            <el-option label="1" value="1"></el-option>
            <el-option label="2" value="2"></el-option>
            <el-option label="3" value="3"></el-option>
            <el-option label="4" value="4"></el-option>
            <el-option label="5" value="5"></el-option>
            <el-option label="6" value="6"></el-option>
            <el-option label="7" value="7"></el-option>
            <el-option label="8" value="8"></el-option>
            <el-option label="9" value="9"></el-option>
            <el-option label="10" value="10"></el-option>
            <el-option label="11" value="11"></el-option>
            <el-option label="12" value="12"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormExtendEndTimeVisible = false">取 消</el-button>
        <el-button type="primary" @click="extendEndTime">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="请给师傅打分" :visible.sync="DialogForScore">
      <div class="block">
        <el-rate
          v-model="value4"
          :colors="['#99A9BF', '#F7BA2A', '#FF9900']" show-text>
        </el-rate>
      </div>
      <div style="margin-top:30px">
        <el-button type="primary" @click="showRate">确认打分</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
    import Plupload4 from "../components/Plupload4";

    export default {
        name: "rentBody",
        components: {
            Plupload4
        },
        created: function () {
            this.getAllShortRents();
            this.getAllLongRents();
            this.getAllComplaintData();
            this.getAllFixData();
        },
        methods:
            {
                handleExtendEndTime(index) {
                    if (this.longRent[6 * (this.currentpage2 - 1) + index]['renew']) {
                        this.$notify.error({
                            title: "续约失败",
                            message: "您已续约过一次，无法再次续约。"
                        });
                        return;
                    }else if (!(this.longRent[6 * (this.currentpage2 - 1) + index]['_status']==='入住')) {
                        this.$notify.error({
                            title: "续约失败",
                            message: "您未入住，无法续约。"
                        });
                        return;
                    }
                    this.tmpIndex = 6 * (this.currentpage2 - 1) + index;
                    this.dialogFormExtendEndTimeVisible = true;
                },

                extendEndTime() {
                    this.dialogFormExtendEndTimeVisible = false;
                    this.$http({
                        url: "/api/long_rent/renew",
                        method: "post",
                        data: {
                            id: this.longRent[this.tmpIndex]["id"],
                            num: this.extendNum
                        }
                    })
                        .then(res => {
                            console.log(res.data);

                            this.longRent[this.tmpIndex] = res.data;
                            this.changeLongRentStatus();
                            this.$notify({
                                title: "续约成功",
                                message: "您已成功续约" + this.extendNum + '个月。',
                                type: "success"
                            });
                            setTimeout(function () {
                                window.location.reload();
                            },500)

                        })
                        .catch(res => {
                            console.log(JSON.stringify(this.longRent));
                            this.$notify.error({
                                title: "续约失败",
                                message: "未知错误，续约失败。"
                            });
                        });
                },

                isStartLive(time) {
                    let now = new Date();
                    let year = now.getFullYear();
                    let month = now.getMonth() + 1;
                    if (month < 10) {
                        month = '0' + month.toString();
                    }
                    let day = now.getDate();
                    if(day<10){
                        day = '0'+day.toString();
                    }
                    now = year.toString() + '-' + month.toString() + '-' + day.toString();
                    //alert(now);
                    return time <= now;
                },
                showRate() {
                    this.$http({
                        url: "/api/mark_work_order",
                        method: "post",
                        data: {
                            work_order_id: this.repairData[this.INDEX3].workOrderId,
                            mark: this.value4
                        }
                    }).then(res => {
                        this.$notify({
                            title: "成功",
                            message: "打分成功！感谢您对我们的支持",
                            type: "success"
                        });
                    });
                    this.DialogForScore = false;
                    this.dialogFixDetail = false;
                },
                fixDetail(index) {
                    if (!this.repairData[index].haveWordOrder) {
                        this.$notify({
                            title: "失败",
                            message: "工单尚未处理，无法查看详情",
                            type: "warning"
                        });
                    } else if (!this.repairData[index].isCompleted) {
                        this.$notify({
                            title: "失败",
                            message: "工单正在处理，请耐心等候",
                            type: "warning"
                        });
                    } else {
                        this.INDEX3 = index;
                        this.dialogFixDetail = true;
                    }
                },
                getAddressByRoomId(ID) {
                    this.$http({
                        url: "/api/get_room_by_id",
                        method: "get",
                        params: {
                            id: ID
                        }
                    }).then(res => {
                        this.addresses.push(res.data);
                    });
                },
                getAddressByShortId(ID) {
                    this.$http({
                        url: "/api/short_rent/get/id",
                        method: "get",
                        params: {
                            id: ID
                        }
                    }).then(res => {
                        this.getAddressByRoomId(res.data.roomId);
                    });
                },
                getAddressByLongId(ID) {
                    this.$http({
                        url: "/api/long_rent/get/id",
                        method: "get",
                        params: {
                            id: ID
                        }
                    }).then(res => {
                        this.getAddressByRoomId(res.data.roomId);
                    });
                },
                changeFixStatus() {
                    for (var i = 0; i < this.repairData.length; i++) {
                        this.repairData[i]['process_status'] = 'process';
                        if (!this.repairData[i]["haveWordOrder"]) {
                            this.repairData[i]["_status"] = "工单未处理";
                            this.repairData[i]['activeNum'] = 1;
                        } else if (!this.repairData[i]["isCompleted"]) {
                            this.repairData[i]["_status"] = "工单进行中";
                            this.repairData[i]['activeNum'] = 2;
                        } else {
                            this.repairData[i]["_status"] = "工单已完成";
                            this.repairData[i]['activeNum'] = 4;
                        }
                    }
                },
                getAllFixData() {
                    this.$http({
                        url: "/api/get_all_repairs_by_tenant_id",
                        method: "get",
                        params: {
                            tenant_id: sessionStorage.getItem("id")
                        }
                    }).then(res => {
                        this.repairData = res.data;
                        if (res.data.length === 0) {
                            console.log(res.data);
                            this.repairData = [{worker_name: 'no data'}]
                            this.isLeagal = false;
                        }
                        this.changeFixStatus();
                        for (let i = 0; i < this.repairData.length; i++) {
                            if (this.repairData[i].shortRentId === '') {
                                this.repairData[i].rentId = this.repairData[i].longRentId;
                                this.getAddressByLongId(this.repairData[i].longRentId);
                            } else {
                                this.repairData[i].rentId = this.repairData[i].shortRentId;
                                this.getAddressByShortId(this.repairData[i].shortRentId);
                            }
                        }
                    });
                },
                handleCurrentChange1(val) {
                    this.currentpage1 = val;
                },
                handleCurrentChange2(val) {
                },
                getAllComplaintData() {
                    this.$http({
                        url: "/api/complaint/get_complaints_by_tenantId/",
                        method: "get",
                        params: {
                            tenant_id: sessionStorage.getItem("id")
                        }
                    }).then(res => {
                        this.complaintData = res.data;
                        for (let i = 0; i < this.complaintData.length; i++) {
                            if (this.complaintData[i].review) {
                                this.ComplainReplied.push(this.complaintData[i]);
                            } else {
                                this.ComplainNoReplied.push(this.complaintData[i]);
                            }
                        }
                    });
                },
                // 获取该用户的所有短期租房记录
                getAllShortRents() {
                    this.$http({
                        url: "/api/short_rent/get_short_rent_and_their_room_by_id/",
                        method: "get",
                        params: {
                            tenant_id: sessionStorage.getItem("id")
                        }
                    }).then(res => {
                        this.shortRent = res.data;
                        this.changeShortRentStatus();
                        this.currentpagesum1 = res.data.length;
                    });
                },
                submitRequest(formName) {
                    let tenant_id = sessionStorage.getItem("id");
                    let description = this.ruleForm.desc;
                    this.$http({
                        method: "post",
                        url: "/api/complaint/save/",
                        data: {
                            id: sessionStorage.getItem("id"),
                            description: this.ruleForm.desc
                        }
                    }).then(res => {
                        this.$notify({
                            title: "成功",
                            message: "您已成功提交相应投诉，请等待客服回应",
                            type: "success"
                        });
                        this.complaintData.push({description: description});
                        this.DialogForComplaint = false;
                    });
                },
                // 获取该用户所有的短租申请
                getAllLongRents() {
                    this.$http({
                        url: "/api/long_rent/get_long_rent_and_their_room_by_id/",
                        method: "get",
                        params: {
                            tenant_id: sessionStorage.getItem("id")
                        }
                    }).then(res => {
                        this.longRent = res.data;
                        console.log(JSON.stringify(this.shortRent));
                        this.changeLongRentStatus();
                        this.currentpagesum2 = res.data.length;
                    });
                },
                handleOpen(key, keyPath) {
                    console.log(key, keyPath);
                },
                handleClose(key, keyPath) {
                    console.log(key, keyPath);
                },
                exit: function () {
                    sessionStorage.removeItem("email");
                    this.logininf = "登录/注册";
                    this.$notify({
                        title: "成功",
                        message: "您已成功退出",
                        type: "success"
                    });
                },
                deleteRow(index, rows) {
                    rows.splice(index, 1);
                },
                resetForm(formName) {
                    this.$refs[formName].resetFields();
                    this.Usernames = "";
                    this.checkPass = "";
                },
                changeName: function () {
                    var newname = this.formLabelAlign.name;
                    //this.userDialogVisible = true;
                    this.$http({
                        method: "post",
                        url: "/api/update_tenant_name",
                        data: {
                            id: sessionStorage.getItem("id"),
                            name: newname
                        }
                    })
                        .then(res => {
                            this.userDialogVisible = false;
                            this.$notify({
                                title: "成功",
                                message: "您已成功修改",
                                type: "success"
                            });
                            setTimeout(function () {
                                sessionStorage.clear();
                                window.location.href = "/";
                            }, 500);

                        })
                        .catch(error => {
                            console.warn(error);
                        });
                },
                changePwd: function () {
                    var newpwd = this.formLabelAlign.password;
                    //this.userDialogVisible = true;
                    this.$http({
                        method: "post",
                        url: "/api/update_tenant_password",
                        data: {
                            id: sessionStorage.getItem("id"),
                            password: this.$md5(newpwd)
                        }
                    })
                        .then(res => {
                            this.PwDialogVisible = false;
                            this.$notify({
                                title: "成功",
                                message: "您已成功修改",
                                type: "success"
                            });
                            setTimeout(function () {
                                sessionStorage.clear();
                                window.location.href = "/";
                            }, 500);
                        })
                        .catch(error => {
                            console.warn(error);
                        });
                },
                submitFormReg(formName) {
                    this.$refs[formName].validate(valid => {
                        if (valid) {
                            const url = "/api/register";
                            this.$http({
                                method: "post",
                                url: url,
                                data: {
                                    name: this.ruleForm.Usernames,
                                    password: this.ruleForm.pass,
                                    email: this.ruleForm.email
                                }
                            })
                                .then(res => {
                                    if (res.data["ok"] === true) {
                                        this.dialogFormVisible = false;
                                        this.$notify({
                                            title: "成功",
                                            message: "您已成功注册,请前往邮箱验证身份信息",
                                            type: "success"
                                        });
                                    } else if (res.data["name"] === false) {
                                        this.$notify({
                                            title: "失败",
                                            message: "此用户名已被注册",
                                            type: "warning"
                                        });
                                    } else if (res.data["email"] === false) {
                                        this.$notify({
                                            title: "失败",
                                            message: "此邮箱已被注册",
                                            type: "warning"
                                        });
                                    }
                                })
                                .catch(error => {
                                    console.warn(error);
                                });
                        } else {
                            console.log("error submit!!");
                            return false;
                        }
                    });
                },
                submitFormLogin(formName) {
                    this.$refs[formName].validate(valid => {
                        if (valid) {
                            const url = "/api/login";
                            this.$http({
                                method: "get",
                                url: url,
                                params: {
                                    password: this.ruleForm.pass,
                                    email: this.ruleForm.email
                                }
                            })
                                .then(res => {
                                    if (res.data["ok"] === true && res.data["is_active"] === true) {
                                        this.dialogFormVisible = false;
                                        this.$notify({
                                            title: "成功",
                                            message: "您已成功登录",
                                            type: "success"
                                        });
                                        sessionStorage.setItem("email", this.ruleForm.email);
                                    } else if (
                                        res.data["ok"] === true &&
                                        res.data["is_active"] === false
                                    ) {
                                        this.$notify({
                                            title: "失败",
                                            message: "请前往邮箱确认您的身份信息",
                                            type: "warning"
                                        });
                                    } else {
                                        this.$notify({
                                            title: "失败",
                                            message: "错误的身份信息",
                                            type: "warning"
                                        });
                                    }
                                })
                                .catch(error => {
                                    console.warn(error);
                                });
                        } else {
                            console.log("error submit!!");
                            return false;
                        }
                    });
                },
                handleSelect(key, keyPath) {
                    console.log(key, keyPath);
                },

                // 根据index删除短期租房数组的数据
                deleteShortRent() {
                    this.dialogAlert = false;
                    var index = this.tmpIndex;
                    var shortRentId = this.shortRent[index]["id"];
                    this.shortRent.splice(index, 1);
                    this.changeShortRentStatus();
                    this.$http({
                        method: "post",
                        url: "/api/short_rent/delete",
                        data: {
                            id: shortRentId
                        }
                    }).then(res => {
                        console.log(res.data);
                    });
                },

                // 根据index删除长期租房数组的数据
                deleteLongRent() {
                    this.dialogAlertLong = false;
                    var index = this.tmpIndex;
                    var longRentId = this.longRent[index]["id"];
                    this.longRent.splice(index, 1);
                    this.$http({
                        method: "post",
                        url: "/api/long_rent/delete",
                        data: {
                            id: longRentId
                        }
                    }).then(res => {
                        console.log(res.data);
                    });
                },

                handleShortRentDelete(index, row) {
                    this.tmpIndex = index;
                    this.dialogAlert = true;
                },

                // 跳转到合同展示页面
                showContract() {
                    window.location.href = "/longrent";
                },

                handleLongRentDelete(index, row) {
                    this.tmpIndex = index;
                    this.dialogAlertLong = true;
                },

                handleShortRentPay(index, row) {
                    var tmpShortRent = this.shortRent[index];
                    if (tmpShortRent["_status"] === "未审核") {
                        this.$notify.error({
                            title: "无法缴费",
                            message: "您的租房申请未被客服审核，无法缴费。"
                        });
                    } else if (tmpShortRent["_status"] === "审核未通过") {
                        this.$notify.error({
                            title: "无法缴费",
                            message: "您的租房申请未被审核通过，无法缴费。"
                        });
                    } else {
                        if (tmpShortRent["payed"] === true) {
                            this.$notify.error({
                                title: "无法缴费",
                                message: "您已缴费，无法重复缴费。"
                            });
                        } else {
                            this.$http({
                                url: "/api/short_rent/update/payed",
                                method: "post",
                                data: {
                                    payed: true,
                                    id: tmpShortRent["id"]
                                }
                            })
                                .then(res => {
                                    console.log(res.data);
                                    if (res.data === "success") {
                                        this.$notify({
                                            title: "缴费成功",
                                            message: "您已成功缴费",
                                            type: "success"
                                        });
                                        this.shortRent[index]["isPayed"] = "已缴费";
                                        this.shortRent[index]["payed"] = true;
                                        setTimeout(function () {
                                            window.location.reload();
                                        }, 500)
                                    }
                                })
                                .catch(res => {
                                    console.log(res.data);
                                    this.$notify.error({
                                        title: "缴费失败",
                                        message: "未知错误，缴费失败。"
                                    });
                                });
                        }
                    }
                },
                addUrl2(url, type) {
                    this.url2.push("https://lzmmmmm.oss-cn-beijing.aliyuncs.com/" + url);
                },
                addUrl1(url, type) {
                    this.url1.push("https://lzmmmmm.oss-cn-beijing.aliyuncs.com/" + url);
                },
                submitFormRegShort(formName, data, type) {
                    if (formName) {
                        let tenant_email = data[this.INDEX1].tenant_email;
                        let description = this.ruleForm.desc;
                        if (tenant_email !== this.ruleForm.email) this.$notify({
                            title: "错误",
                            message: "邮箱错误",
                            type: "warning"
                        });
                        else if (description === "") {
                            this.$notify({
                                title: "错误",
                                message: "请填写描述信息",
                                type: "warning"
                            });
                        } else {
                            this.$http({
                                method: "post",
                                url: "/api/add_repair",
                                data: {
                                    tenant_id: sessionStorage.getItem("id"),
                                    long_rent_id: "",
                                    short_rent_id: data[this.INDEX1].id,
                                    images: this.url1,
                                    description: description
                                }
                            })
                                .then(res => {
                                    this.$notify({
                                        title: "成功",
                                        message: "报修成功",
                                        type: "success"
                                    });
                                })
                                .catch(error => {
                                    console.warn(error);
                                });
                        }

                        this.dialogAlertFixS = false;
                    }
                },

                submitFormRegLong(formName, data) {
                    if (formName) {
                        let tenant_email = sessionStorage.getItem("email");
                        let description = this.ruleForm.desc;
                        if (tenant_email !== this.ruleForm.email)
                            this.$notify({
                                title: "失败",
                                message: "邮箱错误",
                                type: "warning"
                            });
                        else if (description === "") {
                            this.$notify({
                                title: "失败",
                                message: "请填写描述信息",
                                type: "wanring"
                            });
                        } else {
                            this.$http({
                                method: "post",
                                url: "/api/add_repair",
                                data: {
                                    tenant_id: sessionStorage.getItem("id"),
                                    long_rent_id: data[this.INDEX2].id,
                                    short_rent_id: "",
                                    images: this.url2,
                                    description: description
                                }
                            })
                                .then(res => {
                                    this.$notify({
                                        title: "成功",
                                        message: "报修成功",
                                        type: "success"
                                    });
                                })
                                .catch(error => {
                                    console.warn(error);
                                });
                        }

                        this.dialogAlertFixL = false;
                    }
                },

                dialogAlertFixMS(index) {
                    this.INDEX1 = index;
                    if (this.shortRent[index]._status !== "已缴费" && this.shortRent[index]._status !== "入住") {
                        this.$notify({
                            title: "失败",
                            message: "此房间尚未审核通过或尚未缴费，无法报修",
                            type: "warning"
                        })
                    } else {
                        this.dialogAlertFixS = true;
                    }
                },
                dialogAlertFixML(index) {
                    this.INDEX2 = index;
                    if (this.longRent[index].status === "未审核") {
                        this.$notify({
                            title: "失败",
                            message: "此房间尚未审核，无法报修",
                            type: "warning"
                        });
                    } else if (this.longRent[index].status === '未通过') {
                        this.$notify({
                            title: "失败",
                            message: "此房间尚未通过，无法报修",
                            type: "warning"
                        });
                    } else if (this.longRent[index]._status === '合同未签署') {
                        this.$notify({
                            title: "失败",
                            message: "此房间尚未签署，无法报修",
                            type: "warning"
                        });
                    } else if (!this.longRent[index].pass) {
                        this.$notify({
                            title: "失败",
                            message: "此房间尚未签订合约，无法报修",
                            type: "warning"
                        });
                    } else {
                        this.dialogAlertFixL = true;
                    }
                },

                // 下面是一些实用的方法
                changeShortRentStatus() {
                    for (var i = 0; i < this.shortRent.length; i++) {
                         if(this.isStartLive(this.shortRent[i].startDate)){
                             this.shortRent[i].isLive=true;
                         }else{
                             this.shortRent[i].isLive=false;
                         }
                        this.shortRent[i]["process_status"] = "process";
                        if (this.shortRent[i]["status"] === "未审核") {
                            this.shortRent[i]["_status"] = "未审核";
                            this.shortRent[i]["activeNum"] = 1;
                        } else if (this.shortRent[i]["status"] === "未通过") {
                            this.shortRent[i]["_status"] = "审核未通过";
                            this.shortRent[i]["activeNum"] = 1;
                            this.shortRent[i]["process_status"] = "error";
                        } else {
                            this.shortRent[i]["_status"] = this.shortRent[i]["isPayed"];
                            if (this.shortRent[i]["payed"]) {
                                if (this.shortRent[i]["isLive"]) {
                                    this.shortRent[i]["_status"] = "入住";
                                    this.shortRent[i]["activeNum"] = 4;
                                } else {
                                    this.shortRent[i]["activeNum"] = 3;
                                }
                            } else {
                                this.shortRent[i]["activeNum"] = 2;
                            }
                        }
                    }
                },

                changeLongRentStatus() {
                    for (var i = 0; i < this.longRent.length; i++) {
                         if(this.isStartLive(this.longRent[i].startDate))
                             this.longRent[i].isLive=true;
                         else
                             this.longRent[i].isLive=false;
                        this.longRent[i]["process_status"] = "process";
                        if (this.longRent[i]["status"] === "未审核") {
                            this.longRent[i]["_status"] = "未审核";
                            this.longRent[i]["activeNum"] = 1;
                        } else if (this.longRent[i]["status"] === "未通过") {
                            this.longRent[i]["_status"] = "审核未通过";
                            this.longRent[i]["activeNum"] = 1;
                            this.longRent[i]["process_status"] = "error";
                        } else {
                            if (this.longRent[i]["contract"]) {
                                this.longRent[i]["_status"] = "合同已签署";
                                if (this.longRent[i]["isLive"]) {
                                    this.longRent[i]["_status"] = "入住";
                                    this.longRent[i]["activeNum"] = 4;
                                } else {
                                    this.longRent[i]["activeNum"] = 3;
                                }
                            } else {
                                this.longRent[i]["_status"] = "合同未签署";
                                this.longRent[i]["activeNum"] = 2;
                            }
                        }
                    }
                },

                handleCommand(command) {
                    this.$message("click on item " + command);
                },

                payLongRent(index) {
                    var tmpLongtRent = this.longRent[index];
                    if (tmpLongtRent["_status"] === "未审核") {
                        this.$notify.error({
                            title: "无法缴费",
                            message: "您的租房申请未被客服审核，无法缴费。"
                        });
                    } else if (tmpLongtRent["_status"] === "未通过") {
                        this.$notify.error({
                            title: "无法缴费",
                            message: "您的租房申请未被审核通过，无法缴费。"
                        });
                    } else if (tmpLongtRent["_status"] !== "入住") {

                        this.$notify.warning({
                            title: "无法缴费",
                            message: "您还没有入住，无法缴费。"
                        });
                    } else if (
                        tmpLongtRent["payed"][tmpLongtRent["currentMonthNum"]] === true
                    ) {
                        this.$notify.warning({
                            title: "无法缴费",
                            message: "您已缴费，无法重复缴费。"
                        });
                    } else {
                        this.$http({
                            url: "/api/long_rent/update/payed",
                            method: "post",
                            data: {
                                payed: true,
                                id: tmpLongtRent["id"],
                                num: tmpLongtRent["currentMonthNum"]
                            }
                        })
                            .then(res => {
                                console.log(res.data);
                                if (res.data === "success") {
                                    this.$notify({
                                        title: "缴费成功",
                                        message: "您已成功缴费",
                                        type: "success"
                                    });
                                    setTimeout(function () {
                                        this.longRent[index]["payed"][tmpLongtRent["currentMonthNum"]] = true;
                                        window.location.reload();
                                    }, 500)
                                }
                            })
                            .catch(res => {
                                console.log(res.data);
                                this.$notify.error({
                                    title: "缴费失败",
                                    message: "未知错误，缴费失败。"
                                });
                            });
                    }
                }
            },


        data() {
            var validatePass = (rule, value, callback) => {
                if (value === "") {
                    callback(new Error("请输入密码"));
                } else {
                    if (this.ruleForm.checkPass !== "") {
                        this.$refs.ruleForm.validateField("checkPass");
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === "") {
                    callback(new Error("请再次输入密码"));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error("两次输入密码不一致!"));
                } else {
                    callback();
                }
            };
            return {
                value4: '',
                DialogForScore: false,
                isLeagal: true,
                dialogFixDetail: false,
                repairData: [{"worker_name": 'no data'}],
                addresses: [],
                extendNum: 0,
                currentpage1: 1,
                currentpagesum1: 0,
                currentpage2: 1,
                currentpagesum2: 0,
                currentpage3: 1,
                INDEX1: "",
                INDEX2: "",
                INDEX3: 0,
                dialogFormExtendEndTimeVisible: false,
                dialogAlertFixS: false,
                dialogAlertFixL: false,
                userDialogVisible: false,
                pwdDialogVisible: false,
                labelPosition: "right",
                formLabelAlign: {
                    name: "",
                    email: "",
                    password: ""
                },
                logininf: "",
                ruleForm: {
                    pass: "",
                    checkPass: "",
                    email: "",
                    Usernames: "",
                    Id: "",
                    desc: "",
                    Pic: ""
                },
                input4: "",
                activeName: "first",
                rules: {
                    pass: [{required: true, validator: validatePass, trigger: "blur"}],
                    checkPass: [
                        {required: true, validator: validatePass2, trigger: "change"}
                    ],
                    email: [
                        {required: true, message: "请输入邮箱地址", trigger: "change"},
                        {
                            type: "email",
                            message: "请输入正确的邮箱地址",
                            trigger: ["blur", "change"]
                        }
                    ],
                    Usernames: [
                        {required: true, message: "请输入用户名", trigger: "change"},
                        {
                            min: 3,
                            max: 10,
                            message: "长度在 3 到 10 个字符",
                            trigger: ["blur", "change"]
                        }
                    ]
                },
                shortRent: [],
                longRent: [],
                dialogTableVisible: false,
                dialogFormVisible: false,
                dialogTableVisible2: false,
                dialogAlert: false,
                dialogAlertLong: false,
                activeIndex: "1",
                formLabelWidth: "100px",
                imageUrl: "",
                tmpIndex: 0,
                url1: [],
                url2: [],
                activeIndex2: "1",
                ComplainReplied: [],
                ComplainNoReplied: [],
                currentpage4: 1,
                currentpagesum4: 0,
                currentpage5: 1,
                currentpagesum5: 0,
                DialogForComplaint: false,

            };


        },
        updated() {
            var email = sessionStorage.getItem("email");
            console.log(email);
            if (email === null) this.logininf = "注册/登录";
            else this.logininf = "欢迎" + "！";
        },
        mounted() {
            this.checkLogin(1);
            var email = sessionStorage.getItem("email");
            console.log(email);
            if (email === null) this.logininf = "注册/登录";
            else this.logininf = "欢迎" + "！";
            this.formLabelAlign.name = sessionStorage.getItem("name");
            this.formLabelAlign.email = sessionStorage.getItem("email");
            this.formLabelAlign.password = "********";
        },
    };
</script>

<style scoped>
  .el-dropdown-link {
    cursor: pointer;
    color: #409eff;
  }

  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>
