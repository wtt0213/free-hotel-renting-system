<template>
  <div class="content-wrapper">
    <div class="container">
      <div class="row pad-botm">
        <div class="col-md-12">
          <h4 class="header-line" style="font-size:40px">欢迎来到服务端</h4>
        </div>
      </div>
      <div class="row">
        <div class="col-md-8 col-sm-8 col-xs-12">
          <div id="carousel-example" class="carousel slide slide-bdr" data-ride="carousel">
            <div class="carousel-inner">
              <div class="item active">
                <img src="/static/img/1.jpg" alt=""/>
              </div>
              <div class="item">
                <img src="/static/img/2.jpg" alt=""/>

              </div>
              <div class="item">
                <img src="/static/img/3.jpg" alt=""/>

              </div>
            </div>
            <!--INDICATORS-->
            <ol class="carousel-indicators">
              <li data-target="#carousel-example" data-slide-to="0" class="active"></li>
              <li data-target="#carousel-example" data-slide-to="1"></li>
              <li data-target="#carousel-example" data-slide-to="2"></li>
            </ol>
            <!--PREVIUS-NEXT BUTTONS-->
            <a class="left carousel-control" href="#carousel-example" data-slide="prev">
              <span class="glyphicon glyphicon-chevron-left"></span>
            </a>
            <a class="right carousel-control" href="#carousel-example" data-slide="next">
              <span class="glyphicon glyphicon-chevron-right"></span>
            </a>
          </div>
        </div>

        <div class="col-md-4 col-sm-4 col-xs-12">
          <div class="panel panel-primary" style="height: 400px">
            <div class="panel-heading">
              师傅维修记录
            </div>
            <div>
              <el-card :key="index" v-for="item in completedWorkOrder">
                <div style="padding-left: 5%;">
                  <div class="text item"><b>师傅工号:</b><span style="margin-left: 4%">{{item.worker_id}}</span></div>
                  <div class="text item"><b>租客用户名:</b><span style="margin-left: 4%">{{item.tenant_name}}</span></div>
                  <div class="text item"><b>师傅回话:</b><span style="margin-left: 4%">{{item.review}}</span></div>
                  <div class="text item"><b>用户评分:</b><span style="margin-left: 4%">{{item.mark}}</span></div>
                </div>

              </el-card>
            </div>

          </div>
        </div>

      </div>

      <div class="row" style="margin-top:auto">
        <div class="col-md-12 col-sm-4 col-xs-8">
          <div class="panel panel-default">
            <div class="panel-heading">
              租客与申请
            </div>
            <div class="panel-body text-center recent-users-sec">
              <div class="row">
                <el-tabs :tab-position="tabPosition" style="height:auto;width:90%;margin-left:6%">
                  <el-tab-pane label="租客管理">
                    <el-input
                      placeholder="请输入内容"
                      prefix-icon="el-icon-search"
                      v-model="searchName" style="width: 30%">
                    </el-input>
                    <el-button type="primary" @click="searchByUserName">搜索</el-button>
                    <el-table :data="this.renters.slice(6*(currentUserPage - 1), 6*currentUserPage)">
                      <el-table-column property="id" label="序号"></el-table-column>
                      <el-table-column property="name" label="个人昵称"></el-table-column>
                      <el-table-column property="email" label="电子邮件"></el-table-column>

                      <el-table-column label="删除用户" width="120">
                        <template slot-scope="scope">
                          <el-button
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)"
                          >删除
                          </el-button>
                        </template>
                      </el-table-column>
                      <el-table-column label="编辑信息" width="120">
                        <template slot-scope="scope">
                          <el-button
                            size="mini"
                            class="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)"
                          >
                          </el-button>
                          <el-dialog title="编辑信息" :visible.sync="handleEditFlag">
                            <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign"
                                     style="max-width: 80%;margin-left:10%">
                              <el-form-item label="邮箱">
                                <el-input :disabled="true" v-model="userinfo.email"></el-input>
                              </el-form-item>
                              <el-form-item label="用户名">
                                <el-input v-model="userinfo.name">
                                  <el-button slot="append" icon="el-icon-edit"
                                             @click="handleEditNameFlag = true"></el-button>
                                </el-input>
                                <el-dialog
                                  title="提示"
                                  :visible.sync="handleEditNameFlag"
                                  width="30%"
                                  center
                                  append-to-body>
                                  <span>您确定要更改吗？</span>
                                  <span slot="footer" class="dialog-footer">
    <el-button @click="handleEditNameFlag = false">取 消</el-button>
    <el-button type="primary" @click="changeName(scope.$index)">确 定</el-button>
  </span>
                                </el-dialog>
                              </el-form-item>
                              <el-form-item label="密码">
                                <el-input type="password" v-model="userinfo.password">
                                  <el-button slot="append" icon="el-icon-edit"
                                             @click="handleEditPwdFlag=true"></el-button>
                                </el-input>
                                <el-dialog
                                  title="提示"
                                  :visible.sync="handleEditPwdFlag"
                                  width="30%"
                                  center
                                  append-to-body>
                                  <span>您确定要更改吗？</span>
                                  <span slot="footer" class="dialog-footer">
    <el-button @click="handleEdiePwdFlag = false">取 消</el-button>
    <el-button type="primary" @click="changePwd(scope.$index)">确 定</el-button>
  </span>
                                </el-dialog>
                              </el-form-item>
                            </el-form>
                          </el-dialog>
                        </template>
                      </el-table-column>
                    </el-table>
                    <el-pagination
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange1"
                      :current-page.sync="currentUserPage"
                      :page-size="6"
                      layout="prev, pager, next, jumper"
                      :total="currentUserSum"
                      class="page-divided">
                    </el-pagination>
                  </el-tab-pane>
                  <el-tab-pane label="申请审核(短租)">
                    <el-table :data="ShortRentData.slice(6 * (currentpage - 1), 6 * (currentpage))" border="true">
                      <el-table-column property="id" label="申请id"></el-table-column>
                      <el-table-column property="tenant_name" label="租客用户名"></el-table-column>
                      <el-table-column property="address" label="房子位置"></el-table-column>
                      <el-table-column property="startDate" label="申请开始时间"></el-table-column>
                      <el-table-column property="endDate" label="申请结束时间"></el-table-column>
                      <el-table-column property="price" label="结算金额"></el-table-column>
                      <el-table-column property="status" label="审核状态"></el-table-column>
                      <el-table-column property="isPayed" label="有无缴费"></el-table-column>
                      <el-table-column label="操作" width="120">

                        <template slot-scope="scope">
                          <el-button
                            style="display: block;margin-left: 10%"
                            size="mini"
                            type="success"
                            @click="shortRentApply(scope.$index, scope.row, 1)"
                          >通过
                          </el-button>
                          <el-button
                            style="display: block"
                            size="mini"
                            type="warning"
                            @click="shortRentApply(scope.$index, scope.row, 2)"
                          >拒绝
                          </el-button>
                          <el-button
                            style="display: block"
                            size="mini"
                            type="danger"
                            @click="confirm('确认要删除它吗？',scope.$index)"
                          >删除
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                    <el-pagination
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange2"
                      :current-page.sync="currentpage"
                      :page-size="6"
                      layout="prev, pager, next, jumper"
                      :total="shortRentSum"
                      class="page-divided">
                    </el-pagination>

                  </el-tab-pane>

                  <el-tab-pane label="房源管理">
                    <template>
                      <el-tabs v-model="activeName1" @tab-click="handleClick1">
                        <el-tab-pane label="不可以出租" name="first">
                          <el-table style="padding-left: 5%"
                                    :data="unavailableHouse.slice(6*(currentHousePage1 - 1),6*currentHousePage1)">
                            <el-table-column property="id" label="房源编号" width="120"></el-table-column>
                            <el-table-column property="city" label="房源所在区域" width="120"></el-table-column>
                            <el-table-column property="location" label="房源地址" width="120"></el-table-column>
                            <el-table-column property="shortPrice" label="短租价格(/天)" width="120"></el-table-column>
                            <el-table-column property="longPrice" label="长租价格(/月)" width="120"></el-table-column>
                            <el-table-column property="type" label="房间类型" width="120"></el-table-column>
                            <el-table-column label="操作">
                              <template slot-scope="scope">
                                <el-dropdown>
                      <span class="el-dropdown-link">
                        更多
                        <i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                                  <el-dropdown-menu slot="dropdown">

                                    <el-dropdown-item @click.native="confirmDeleteRoom('您确定要删除吗？',scope.$index, 1)">删除房源
                                    </el-dropdown-item>
                                    <el-dropdown-item @click.native="EditHouse(scope.$index, scope.row, 1)">
                                      查看详细信息
                                    </el-dropdown-item>
                                  </el-dropdown-menu>
                                </el-dropdown>
                              </template>
                            </el-table-column>
                          </el-table>
                          <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange9"
                            :current-page.sync="currentHousePage1"
                            :page-size="6"
                            layout="prev, pager, next, jumper"
                            :total="currentNoRentHouse"
                            class="page-divided">
                          </el-pagination>
                          <div style="margin-top: 20px">
                            <el-button @click="dialogAddVisible=true">添加房源</el-button>
                          </div>
                        </el-tab-pane>

                        <el-tab-pane label="可以出租" name="second">
                          <el-table style="padding-left: 10%" :data="this.availableHouse.slice(6*(currentHousePage2 - 1),6*currentHousePage2)">
                            <el-table-column property="id" label="房源编号" width="120"></el-table-column>
                            <el-table-column property="city" label="房源所在区域" width="120"></el-table-column>
                            <el-table-column property="location" label="房源地址" width="120"></el-table-column>
                            <el-table-column property="shortPrice" label="短租价格(/天)" width="120"></el-table-column>
                            <el-table-column property="longPrice" label="长租价格(/月)" width="120"></el-table-column>
                            <el-table-column property="type" label="房间类型" width="120"></el-table-column>
                            <el-table-column label="操作">
                              <template slot-scope="scope">
                                <el-dropdown>
                      <span class="el-dropdown-link">
                        更多
                        <i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                                  <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item @click.native="confirmDeleteRoom('您确定要删除吗？',scope.$index, 2)">删除房源
                                    </el-dropdown-item>
                                    <el-dropdown-item
                                      @click.native="stopRentRoom('您确定要暂停出租该房源吗？',scope.$index)">暂停出租
                                    </el-dropdown-item>
                                    <el-dropdown-item @click.native="EditHouse(scope.$index, scope.row,2)">
                                      详细信息
                                    </el-dropdown-item>
                                  </el-dropdown-menu>
                                </el-dropdown>
                              </template>
                            </el-table-column>
                          </el-table>
                          <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange10"
                            :current-page.sync="currentHousePage2"
                            :page-size="6"
                            layout="prev, pager, next, jumper"
                            :total="currentYesRentHouseSum"
                            class="page-divided">
                          </el-pagination>
                          <div style="margin-top: 20px">
                            <el-button @click="dialogAddVisible=true">添加房源</el-button>
                          </div>
                        </el-tab-pane>
                      </el-tabs>
                    </template>
                  </el-tab-pane>
                  <el-tab-pane label="申请审核(长租)">
                    <el-table :data="this.LongRentData.slice(6*(longRentPage-1),6*longRentPage)" border="true">
                      <el-table-column property="id" label="申请id"></el-table-column>
                      <el-table-column property="tenant_name" label="租客用户名"></el-table-column>
                      <el-table-column property="city" label="房子区域"></el-table-column>
                      <el-table-column property="address" label="房子地址"></el-table-column>
                      <el-table-column property="startDate" label="申请开始时间"></el-table-column>
                      <el-table-column property="endDate" label="申请结束时间"></el-table-column>
                      <el-table-column property="longPrice" label="总价"></el-table-column>
                      <el-table-column property="contractStatus" label="合同状态"></el-table-column>
                      <el-table-column property="status" label="审核状态"></el-table-column>
                      <el-table-column label="操作" width="120">
                        <template slot-scope="scope">
                          <el-button
                            style="margin-left: 10%"
                            size="mini"
                            type="success"
                            @click="longRentApply(scope.$index, scope.row, 1)"
                          >通过
                          </el-button>
                          <el-button
                            size="mini"
                            type="warning"
                            @click="longRentApply(scope.$index, scope.row, 2)"
                          >拒绝
                          </el-button>
                          <el-button
                            size="mini"
                            type="danger"
                            @click="confirm2('确认要删除它吗？',scope.$index)"
                          >删除
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                    <el-pagination
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange4"
                      :current-page.sync="longRentPage"
                      :page-size="6"
                      layout="prev, pager, next, jumper"
                      :total="longRentSum"
                      class="page-divided">
                    </el-pagination>
                  </el-tab-pane>

                  <el-tab-pane label="合同管理">
                    <el-input
                      placeholder="请输入内容"
                      prefix-icon="el-icon-search"
                      v-model="contrastSearch" style="width: 30%">
                    </el-input>
                    <el-button type="primary" @click="searchbyContrast">搜索</el-button>
                    <el-table style="margin-top: 2%" :data="this.longRentDataNew.slice(6*(currentpage7-1),6*currentpage7)" border="true">
                      <el-table-column property="id" label="申请id"></el-table-column>
                      <el-table-column property="tenant_name" label="租客用户名"></el-table-column>
                      <el-table-column property="city" label="房子区域"></el-table-column>
                      <el-table-column property="address" label="房子地址"></el-table-column>
                      <el-table-column property="startDate" label="申请开始时间"></el-table-column>
                      <el-table-column property="endDate" label="申请结束时间"></el-table-column>
                      <el-table-column property="contractStatus" label="合同状态"></el-table-column>
                      <el-table-column property="longPrice" label="总价"></el-table-column>
                      <el-table-column label="操作" width="120">
                        <template slot-scope="scope">
                          <el-button
                            style="margin-left: 10%"
                            size="mini"
                            type="success"
                            @click="signContract(scope.$index, scope.row)"
                          >签约合同
                          </el-button>
                          <el-button
                            size="mini"
                            type="warning"
                            @click="toLongRent"
                          >查看合同
                          </el-button>
                          <el-button
                            size="mini"
                            type="danger"
                            @click="confirm3('确认要删除它吗？',scope.$index)"
                          >删除合同
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                    <el-pagination
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange7"
                      :current-page.sync="currentpage7"
                      :page-size="6"
                      layout="prev, pager, next, jumper"
                      :total="htSum"
                      class="page-divided">
                    </el-pagination>
                  </el-tab-pane>
                  <el-tab-pane label="师傅管理">
                    <el-table :data="this.workers.slice(6*(currentpage8-1),6*currentpage8)">
                      <el-table-column property="id" label="师傅id"></el-table-column>
                      <el-table-column property="name" label="师傅用户名"></el-table-column>
                      <el-table-column property="phone" label="师傅电话"></el-table-column>
                      <el-table-column label="操作">
                        <template slot-scope="scope">
                          <el-button-group>
                            <el-button class="el-icon-delete" type="danger"
                                       @click="DeleteFixer(scope.$index)"></el-button>
                          </el-button-group>
                        </template>
                      </el-table-column>

                    </el-table>
                    <div class="pull-left" style="margin-top: 20px">
                      <el-button type="primary" @click="DialogWorkerAdd=true">添加师傅</el-button>
                    </div>
                    <el-pagination
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange8"
                      :current-page.sync="currentpage8"
                      :page-size="6"
                      layout="prev, pager, next, jumper"
                      :total="currentRepairSum"
                      class="page-divided">
                    </el-pagination>
                  </el-tab-pane>
                </el-tabs>
              </div>
            </div>
          </div>
        </div>
      </div>
      <el-dialog title="查看详细信息" :visible.sync="editHousePop">
        <el-form>
          <el-form-item label="区域">
            <el-input disabled="disabled" v-model="houseInfo.city"></el-input>
          </el-form-item>
          <el-form-item label="详细地址">
            <el-input disabled="disabled" v-model="houseInfo.location"></el-input>
          </el-form-item>
          <el-form-item label="短租价格(/天)">
            <el-input disabled="disabled" v-model="houseInfo.shortPrice"></el-input>
          </el-form-item>
          <el-form-item label="长期价格(/月)">
            <el-input disabled="disabled" v-model="houseInfo.longPrice"></el-input>
          </el-form-item>
          <el-form-item label="描述">
            <el-input disabled="disabled" autosize="{minRows:4,maxRows:9}" type="textarea"
                      v-model="houseInfo.description"></el-input>
          </el-form-item>
        </el-form>
      </el-dialog>
      <el-dialog title="添加房源" :visible.sync="this.dialogAddVisible" @close="closeAddRoom">
        <housemanage @funn="dialogAddVisible=false"></housemanage>
      </el-dialog>
      <div class="row">
        <div class="col-md-6 col-sm-6 col-xs-8">
          <div class="panel panel-warning">
            <div class="panel-heading">
              报修
            </div>
            <template>
              <el-tabs v-model="activeName11" @tab-click="handleClick1">
                <el-tab-pane label="未处理" name="first">
                  <div class="panel-body">
                    <el-table :data="fixersUnFixed.slice(6*(currentRepairPage1-1),6*currentRepairPage1)"
                              tooltip-effect="dark">
                      <el-table-column property="tenant_name" label="昵称"></el-table-column>
                      <el-table-column property="description" label="描述" show-overflow-tooltip></el-table-column>
                      <el-table-column
                        fixed="right"
                        label="操作"
                        width="120">
                        <template slot-scope="scope">
                          <el-button-group>
                            <el-button size="mini" style="display: inline" type='primary' class="el-icon-view"
                                       @click="dialogFixer(scope.$index, 1)">

                            </el-button>
                            <el-button class="el-icon-thumb" size="mini" style="display: inline" type='success'
                                       @click="SendFixer(scope.$index)">

                            </el-button>
                          </el-button-group>
                        </template>
                      </el-table-column>
                    </el-table>
                    <el-pagination
                      style="padding-left: 25%;margin-top: 3%"
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange51"
                      :current-page.sync="currentRepairPage1"
                      :page-size="6"
                      layout="prev, pager, next, jumper"
                      :total="currentUnfiRepairSum">
                    </el-pagination>
                  </div>
                </el-tab-pane>
                <el-tab-pane label="已处理" name="second">
                  <div class="panel-body">
                    <el-table :data="fixersFixed.slice(6*(currentRepairPage2-1),6*currentRepairPage2)"
                              tooltip-effect="dark">
                      <el-table-column property="tenant_name" label="昵称"></el-table-column>
                      <el-table-column property="description" label="描述" show-overflow-tooltip></el-table-column>
                      <el-table-column
                        fixed="right"
                        label="操作"
                        width="120">
                        <template slot-scope="scope">
                          <el-button-group>
                            <el-button size="mini" style="display: inline" type='primary' class="el-icon-view"
                                       @click="dialogFixer(scope.$index,2)">

                            </el-button>
                            <el-button class="el-icon-delete" size="mini" style="display: inline" type='danger'
                                       @click="DeleteFix(scope.$index)">

                            </el-button>
                          </el-button-group>
                        </template>
                      </el-table-column>
                    </el-table>
                    <el-pagination
                      style="padding-left: 25%;margin-top: 3%"
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange52"
                      :current-page.sync="currentRepairPage2"
                      :page-size="6"
                      layout="prev, pager, next, jumper"
                      :total="currentfiRepairSum">
                    </el-pagination>
                  </div>
                </el-tab-pane>
              </el-tabs>
            </template>

          </div>

        </div>
        <div class="col-md-6 col-sm-6 col-xs-8">
          <div class="panel panel-primary">
            <div class="panel-heading">
              举报信息
            </div>
            <el-tabs v-model="activeName2" @tab-click="handleClick">
              <el-tab-pane label="未回复" name="first">
                <el-tabs :tab-position="tabPosition" style="height:auto;width:90%;margin-left:6%">
                  <el-table :row-class-name="tableRowClassName"
                            :data="this.complainDataUnReviewed.slice(6*(currentComplainPage1-1),6*currentComplainPage1)">
                    <el-table-column property="tenant_name" label="租客用户名"></el-table-column>
                    <el-table-column property="description" label="投诉内容"></el-table-column>
                    <el-table-column label="操作" width="120">
                      <template slot-scope="scope">
                        <el-button-group>
                          <el-button
                            size="mini"
                            type="primary"
                            @click="returnComplaint(scope.$index)"
                          >回复
                          </el-button>
                        </el-button-group>
                      </template>
                    </el-table-column>
                  </el-table>
                  <el-pagination
                    style="padding-left: 25%;margin-top: 3%"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange61"
                    :current-page.sync="currentComplainPage1"
                    :page-size="6"
                    layout="prev, pager, next, jumper"
                    :total="currentComplainSum1">
                  </el-pagination>
                </el-tabs>
              </el-tab-pane>
              <el-tab-pane label="已回复" name="second">
                <el-tabs :tab-position="tabPosition" style="height:auto;width:90%;margin-left:6%">
                  <el-table :row-class-name="tableRowClassName"
                            :data="this.complainDataReviewed.slice(6*(currentComplainPage2-1),6*currentComplainPage2)">
                    <el-table-column property="tenant_name" label="租客用户名"></el-table-column>
                    <el-table-column property="description" label="投诉内容"></el-table-column>
                    <el-table-column label="操作" width="120">
                      <template slot-scope="scope">
                        <el-button-group>
                          <el-button
                            class="el-icon-delete"
                            size="mini"
                            type="danger"
                            @click="deleteRepliedComplain(scope.$index)"
                          >
                          </el-button>
                          <el-button
                            class="el-icon-edit"
                            size="mini"
                            @click="EditRepliedComplain(scope.$index)"
                          >
                          </el-button>
                        </el-button-group>
                        <el-dialog title="您的回复" :visible.sync="editReplyPop">
                          <el-input v-model="replyMsg">
                          </el-input>
                          <el-button style="margin-top: 3%" @click="updateReply(scope.$index)">确定回复</el-button>
                        </el-dialog>
                      </template>
                    </el-table-column>
                  </el-table>
                  <el-pagination
                    style="padding-left: 25%;margin-top: 3%"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange62"
                    :current-page.sync="currentComplainPage2"
                    :page-size="6"
                    layout="prev, pager, next, jumper"
                    :total="currentComplainSum2">
                  </el-pagination>
                </el-tabs>
              </el-tab-pane>
            </el-tabs>
          </div>


        </div>
      </div>
    </div>
    <el-dialog title="报修详情" :visible.sync="dialogFix1" :center="true">
      <el-form ref="form" label-width="80px" border>
        <el-divider></el-divider>
        <el-form-item label="租客昵称:">
          <span>{{fixersUnFixed[INDEX].tenant_name}}</span>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="房屋地址:">
          <span>{{fixersUnFixed[INDEX].address}}</span>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="维修描述:">
          <span>{{fixersUnFixed[INDEX].description}}</span>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="图片显示:" style="width: 400px">
          <img v-for="item in fixersUnFixed[this.INDEX].images" v-bind:src="item"/>
        </el-form-item>
        <el-divider></el-divider>

        <el-form-item>
          <el-button type="primary" style="width: 100px" @click="dialogFix1=false">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="报修详情" :visible.sync="dialogFix2" :center="true">
      <el-form ref="form" label-width="80px" border>
        <el-divider></el-divider>
        <el-form-item label="租客昵称:">
          <span>{{fixersFixed[INDEX].tenant_name}}</span>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="房屋地址:">
          <span>{{fixersFixed[INDEX].address}}</span>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="维修描述:">
          <span>{{fixersFixed[INDEX].description}}</span>
        </el-form-item>
        <el-divider></el-divider>
        <el-form-item label="图片显示:" style="width: 400px">
          <img v-for="item in fixersFixed[this.INDEX].images" v-bind:src="item"/>
        </el-form-item>
        <el-divider></el-divider>

        <el-form-item>
          <el-button type="primary" style="width: 100px" @click="dialogFix2=false">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="选择师傅" :visible.sync="sendFixerVisible">
      <el-card class="box-card" v-for="i in workers.length" :key="key" style="margin-top: 3%;margin-left: 10%">
        <div slot="header" class="clearfix">
          <span>师傅信息</span>
          <el-button v-on:click="sendSpecificFixer(i)" style="float: right; padding: 3px 0" type="text">指派师傅</el-button>
        </div>
        <div class="text item">
          <template>
            <el-table
              :data="workers.slice(i-1,i)"
              stripe
              style="width: 100%">
              <el-table-column
                prop="name"
                label="姓名"
                width="180">
              </el-table-column>
              <el-table-column
                prop="phone"
                label="电话">
              </el-table-column>
              <el-table-column
                prop="mark"
                label="评分">
              </el-table-column>
            </el-table>
          </template>
        </div>
      </el-card>

    </el-dialog>
    <el-dialog title="添加师傅" :visible.sync="DialogWorkerAdd">
      <el-form ref="form"  label-width="80px"
               style="overflow-y:hidden;overflow-x: hidden;padding-top: 2%">
        <el-form-item label="师傅工号">
          <el-input v-model="fixerInfo.name"></el-input>
        </el-form-item>
        <el-form-item label="师傅密码">
          <el-input type="password" v-model="fixerInfo.password"></el-input>
        </el-form-item>
        <el-form-item label="师傅电话">
          <el-input v-model="fixerInfo.phone"></el-input>
        </el-form-item>
        <el-form-item label="操作">
          <el-button type="primary" @click="addWorkers">添加师傅</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
    import housemanage from "./housemanage";

    export default {
        name: "serverBody",
        components: {
            housemanage
        },
        data: function () {
            return {
                contrastSearch:"",
                DialogWorkerAdd: false,
                activeName1: 'first',
                activeName11:'first',
                currentComplainPage1: 1,
                currentComplainSum1: 0,
                currentComplainPage2: 1,
                currentComplainSum2: 0,
                currentRepairPage: 1,
                currentRepairSum: 0,
                availableHouse: [],
                unavailableHouse: [],
                INDEX: 0,
                replyMsg: '',
                dialogFix1: false,
                dialogFix2: false,
                fixers: [],
                searchName: '',
                yes: false,
                yes2: true,
                Renttype: '',
                RentId: '',
                myRouter: '/services',
                Number: '12345678',
                renters: [],
                ShortRentData: [],
                dialogTableVisible: false,
                sendFixerVisible: false,
                LongRentData: [],
                houseData: [],
                dialogAddVisible: false,
                handleEditFlag: false,
                currentpage: 1,
                currentUserPage: 1,
                currentUserSum: 0,
                currentHousePage: 1,
                currentHouseSum: 0,
                shortRentSum: 0,
                longRentPage: 1,
                longRentSum: 0,
                userinfo: {},
                handleEditNameFlag: false,
                handleEditPwdFlag: false,
                editHousePop: false,
                houseInfo: {},
                complaintData: [],
                workers: [],
                tenant_fix_id: "",
                fixersFixed: [],
                fixersUnFixed: [],
                complainDataReviewed: [],
                complainDataUnReviewed: [],
                longRentDataNew: [],
                activeName2: "first",
                currentUnfiRepairSum: 0,
                currentRepairPage1: 1,
                currentfiRepairSum: 0,
                currentRepairPage2: 1,
                completedWorkOrder: [],
                htSum: 0,
                currentpage7: 1,
                currentpage8: 1,
                editReplyPop: false,
                fixerInfo:{
                    name:'',
                    password:'',
                    phone:''
                },
                url1: [],
                url2: [],
                currentHousePage1: 1,
                currentNoRentHouse: 0,
                currentHousePage2: 1,
                currentYesRentHouseSum: 0,
                currentpage5:1

            }
        },
        beforeCreate() {
            this.$http({
                url: "/api/get_all_tenants/",
                method: "get",
            }).then((res) => {
                this.renters = res.data;
                this.currentUserSum = res.data.length;
            });
            this.$http({
                url: "/api/short_rent/get_all_short_rents/",
                method: "get",
            }).then((res) => {
                this.ShortRentData = res.data;
                this.shortRentSum = res.data.length;
            });
            this.$http({
                url: "/api/long_rent/get_all_long_rents/",
                method: "get",
            }).then((res) => {
                this.LongRentData = res.data;
                for (let i = 0; i < this.LongRentData.length; i++) {
                    if (this.LongRentData[i].status === '通过') {
                        this.longRentDataNew.push(this.LongRentData[i]);
                    }
                }
                this.longRentSum = res.data.length;
                this.htSum = this.longRentDataNew.length;
            });
            this.$http({
                url: "/api/get_all_rooms/",
                method: "get",
            }).then((res) => {
                this.houseData = res.data;
                this.currentHouseSum = res.data.length;
                for (let i = 0; i < this.houseData.length; i++) {
                    if (this.houseData[i]['available'] === false) {
                        //this.complaintData[i].reviewInfo = "未回复";
                        this.unavailableHouse.push(this.houseData[i]);
                    } else {
                        //this.complaintData[i].reviewInfo = "已回复";
                        this.availableHouse.push(this.houseData[i]);
                    }
                }
                this.currentNoRentHouse = this.unavailableHouse.length;
                this.currentYesRentHouseSum = this.availableHouse.length;
            });
            this.$http({
                url: "/api/complaint/get_all_complaint/",
                method: "get",
            }).then((res) => {
                this.complaintData = res.data;
                for (let i = 0; i < this.complaintData.length; i++) {
                    if (this.complaintData[i]['is_reviewed'] === false) {
                        this.complaintData[i].reviewInfo = "未回复";
                        this.complainDataUnReviewed.push(this.complaintData[i]);
                    } else {
                        this.complaintData[i].reviewInfo = "已回复";
                        this.complainDataReviewed.push(this.complaintData[i]);
                    }
                }
                this.$http({
                    method: 'get',
                    url: "/api/get_all_work_orders"
                }).then((res) => {
                    for (let i = 0; i < res.data.length; i++) {
                        if (res.data[i]['is_complete'])
                            this.completedWorkOrder.push(res.data[i])
                    }
                    if(res.data.length === 0)
                        this.completedWorkOrder[0] = {"tenant_name":"no data"}
                });
                this.currentComplainSum1 = this.complainDataUnReviewed.length;
                this.currentComplainSum2 = this.complainDataReviewed.length;
            });
            this.$http({
                url: "/api/get_all_repairs/",
                method: "get",
            }).then((res) => {
                this.fixers = res.data;
                if (this.fixers.length === 0) {
                    this.fixers[0] = {"tenant_name": "no data"};
                    this.fixersFixed[0] = {"tenant_name": "no data"};
                    this.fixersUnFixed[0] = {"tenant_name": "no data"};
                } else {
                    for (let i = 0; i < this.fixers.length; i++) {
                        if (this.fixers[i]['haveWordOrder'] === false)
                            this.fixersUnFixed.push(this.fixers[i]);
                        else
                            this.fixersFixed.push(this.fixers[i]);
                    }
                }
                this.currentRepairSum = res.data.length;
                this.currentUnfiRepairSum = this.fixersUnFixed.length;
                this.currentfiRepairSum = this.fixersFixed.length;
                //alert(this.fixersFixed[0]['id']);
                if(this.fixersUnFixed.length === 0)
                    this.fixersUnFixed[0] = {"tenant_name":"no data"}
                if(this.fixersFixed.length === 0)
                    this.fixersFixed[0] = {"tenant_name":"no data"}
            });
            this.$http({
                url: "/api/worker/get",
                method: "get",
            }).then((res) => {
                this.workers = res.data;
                this.currentRepairSum = res.data.length;

            });
        },
        methods: {
            DeleteFixer(index) {
                this.$http({
                    method: "post",
                    url: "/api/worker/delete",
                    data: {
                        id: this.workers[6*(this.currentpage8 - 1)+index]['id']
                    }
                }).then((res) => {
                    this.$notify({
                        title: "成功",
                        message: "您已成功删除该师傅",
                        type: "success"
                    });
                    this.workers.splice(6*(this.currentpage8 - 1)+index, 1);
                })
            },
            addWorkers() {
                this.$http({
                    url: "/api/worker/save",
                    method: "post",
                    data: {
                        name: this.fixerInfo.name,
                        password: this.$md5(this.fixerInfo.password),
                        phone: this.fixerInfo.phone,
                        image: ''
                    }
                }).then((res) => {
                    this.$notify({
                        title:"成功",
                        message:"成功添加",
                        type:"success"
                    });
                    this.fixerInfo.id = '5d6721NMSL23455LJXXQ2f';
                    this.workers.push(this.fixerInfo);
                    this.DialogWorkerAdd = false;
                });
            },
            updateReply(index) {
                let newReply = this.replyMsg;
                this.$http({
                    method: "post",
                    url: "/api/complaint/update_review",
                    data: {
                        id: this.complainDataReviewed[6*(this.currentComplainPage2 - 1) +index]['id'],
                        review: newReply
                    }
                }).then((res) => {
                    this.$notify({
                        title: "成功",
                        message: "更新回复成功",
                        type: "success"
                    });
                    this.editReplyPop = false;
                })
            },
            EditRepliedComplain(index) {
                this.editReplyPop = true;
                let review = '';
                this.$http({
                    method: "get",
                    url: "/api/complaint/get/id",
                    params: {
                        id: this.complainDataReviewed[6*(this.currentComplainPage2 - 1) + index]['id']
                    }
                }).then((res) => {
                    this.replyMsg = res.data['review'];

                })
            },
            deleteRepliedComplain(index) {
                this.$http({
                    method: "post",
                    url: "/api/complaint/delete",
                    data: {
                        id: this.complainDataReviewed[6*(this.currentComplainPage2 - 1) +index]['id']
                    }
                }).then((res) => {
                    this.$notify({
                        title: "成功",
                        message: "您已成功删除举报投诉信息",
                        type: "success"
                    });
                    this.complainDataReviewed.splice(6*(this.currentComplainPage2 - 1) +index, 1);
                })
            },
            handleClick1(tab, event) {
            },
            searchbyContrast(){
                this.$http({
                    method:'post',
                    url:"/api/longrent/get_all_contract_by_tenant_name_blurry"
                }).then((res)=>{
                    this.longRentDataNew = res.data;
                })
            },
            stopRentRoom(msg, index) {
                //alert(this.availableHouse[6*(this.currentHousepage2-1)+index]['id']);
                this.$confirm(msg).then(
                    _ => {
                        this.$http({
                            method: 'post',
                            url: '/api/set_room_available',
                            data: {
                                room_id: this.availableHouse[6*(this.currentHousePage2-1)+index]['id'],
                                available: false,
                            }
                        }).then((res) => {
                            this.$notify({
                                title: '成功',
                                message: '成功暂停出租该房源',
                                type: 'success'
                            });
                            //this.availableHouse[6*(this.currentHousepage2-1)+index]['available'] = false;
                            //alert('ddddd');
                            this.unavailableHouse.push(this.availableHouse[6*(this.currentHousePage2-1)+index]);
                            this.availableHouse.splice(6*(this.currentHousePage2-1)+index, 1);
                        }).catch((err) => {
                            console.log(err);
                        });
                    }).catch(
                    _ => {
                    });
            },
            sendSpecificFixer(i) {
                //alert(this.workers[i-1]['id']);
                this.$http({
                    method: "post",
                    url: "/api/add_work_order",
                    data: {
                        worker_id: this.workers[i - 1]['id'],
                        repair_id: this.tenant_fix_id,
                        is_complete: false,
                        mark: 5,
                        review: ''
                    }
                }).then((res) => {
                    let work_order_id = res.data['work_order_id'];
                    this.$http({
                        method: "post",
                        url: "/api/create_order_for_repair",
                        data: {
                            id: this.tenant_fix_id,
                            work_order_id: work_order_id
                        }
                    }).then((res) => {
                        this.$notify({
                            title: "成功",
                            message: "成功提交",
                            type: "success"
                        });
                        this.fixersFixed.push(this.fixersUnFixed[i - 1]);
                        this.fixersUnFixed.splice(i - 1, 1);
                        if(this.fixersUnFixed.length === 0)
                            this.fixersUnFixed[0]={"tenant_name":"no data"};
                        if(this.fixersFixed.length === 0)
                            this.fixersFixed[0] = {"tenant_name":"no data"};
                    });
                })
            },
            tableRowClassName({row, rowIndex}) {
                if (rowIndex === 2) {
                    return 'warning-row';
                } else {
                    return 'success-row'
                }
                return '';
            },
            SendFixer(index) {
                this.sendFixerVisible = true;
                this.tenant_fix_id = this.fixers[6*(this.currentRepairPage1 - 1)+index]['id'];
            },
            returnComplaint(index) {
                this.$prompt('请输入回复举报的内容', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                }).then(({value}) => {
                    this.$http({
                        method: "post",
                        url: "/api/complaint/update_review",
                        data: {
                            id: this.complaintData[6*(this.currentComplainPage1-1)+index]['id'],
                            review: value
                        }
                    }).then((res) => {
                        this.$notify({
                            title: "成功",
                            message: "您已成功回复举报信息",
                            type: "success"
                        });
                        this.complainDataReviewed.push(this.complainDataUnReviewed[6*(this.currentComplainPage1-1)+index]);
                        this.complainDataUnReviewed.splice(6*(this.currentComplainPage1-1)+index, 1);
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });
            },
            handleCurrentChange1(val) {
                this.currentUserPage = val;
            },
            handleCurrentChange2(val) {
                this.currentpage = val;
            },
            handleCurrentChange3(val) {
                this.currentHousePage = val;
            },
            handleCurrentChange4(val) {
                this.longRentPage = val;
            },
            handleCurrentChange5(val){
                this.currentpage5 = val;
            },
            handleCurrentChange51(val) {
                this.currentRepairPage1 = val;
            },
            handleCurrentChange52(val) {
                this.currentRepairPage2 = val;
            },
            handleCurrentChange61(val) {
                this.currentComplainPage1 = val;
            },
            handleCurrentChange62(val) {
                this.currentComplainPage2 = val;
            },
            handleCurrentChange7(val) {
                this.currentpage7 = val;
            },
            handleCurrentChange8(val) {
                this.currentpage8 = val;
            },
            handleCurrentChange9(val) {
                this.currentHousePage1 = val;
            },
            handleCurrentChange10(val) {
                this.currentHousePage2 = val;
            },
            DeleteFix(index) {
                this.$http({
                    method: "post",
                    url: "/api/delete_repair",
                    data: {
                        id: this.fixersFixed[6*(this.currentRepairPage2 - 1)+index]['id']
                    }
                }).then((res) => {
                    this.$notify({
                        title: "成功",
                        message: "您已成功删除相应报修记录",
                        type: "success"
                    });
                    this.fixersFixed.splice(6*(this.currentRepairPage2 - 1)+index, 1);
                })
            },
            searchByUserName() {
                this.$http({
                    url: "/api/get_all_tenants_blurry/",
                    method: "get",
                    params: {
                        name: this.searchName
                    }
                }).then((res) => {
                    this.renters = res.data;
                    this.currentUserSum = res.data.length;
                });
            },
            EditHouse(index, row, type) {
                this.editHousePop = true;
                if(type === 1)
                  this.houseInfo = this.unavailableHouse[6*(this.currentHousePage1 - 1)+index];
                else
                    this.houseInfo = this.availableHouse[6*(this.currentHousePage2 - 1)+index];
            },
            changeName: function (index) {
                let newname = this.userinfo['name'];
                //this.userDialogVisible = true;
                this.$http({
                    method: 'post',
                    url: '/api/update_tenant_name',
                    data: {
                        id: this.renters[6*(this.currentUserPage - 1) + index]['id'],
                        name: newname
                    }
                }).then((res) => {
                    this.handleEditNameFlag = false;
                    this.$notify({
                        title: '成功',
                        message: '您已成功修改',
                        type: 'success'
                    });
                }).catch((error) => {
                    console.warn(error)
                });
            },
            changePwd: function (index) {
                let newpwd = this.userinfo['password'];
                //this.userDialogVisible = true;
                this.$http({
                    method: 'post',
                    url: '/api/update_tenant_password',
                    data: {
                        id: this.renters[index]['id'],
                        password: this.$md5(newpwd)
                    }
                }).then((res) => {
                    this.handleEditPwdFlag = false;
                    this.$notify({
                        title: '成功',
                        message: '您已成功修改',
                        type: 'success'
                    });
                }).catch((error) => {
                    console.warn(error)
                });
            },
            handleEdit(index, row) {
                this.handleEditFlag = true;
                this.userinfo = this.renters[6*(this.currentUserPage - 1) + index];
            },
            confirmDeleteRoom(msg, index, type) {
                let id = '';
                if(type === 1)
                    id = this.unavailableHouse[6*(this.currentHousePage1 - 1)+index]['id'];
                else
                    id = this.availableHouse[6*(this.currentHousePage2 - 1)+index]['id'];
                this.$confirm(msg).then(
                    _ => {
                        this.$http({
                            method: 'post',
                            url: '/api/delete_room_by_id',
                            data: {
                                id: id,
                            }
                        }).then((res) => {
                            this.$notify({
                                title: '成功',
                                message: '成功删除相应记录',
                                type: 'success'
                            });
                            if (type === 1)
                                this.unavailableHouse.splice(6*(this.currentHousePage1 - 1)+index, 1);
                            else
                                this.availableHouse.splice(6*(this.currentHousePage2 - 1)+index, 1);
                        }).catch((err) => {
                            console.log(err);
                        });
                    }).catch(
                    _ => {
                    });
            },
            toLongRent() {
                window.location.href = "/longrent";
            },
            confirm(msg, index) {
                this.$confirm(msg).then(
                    _ => {
                        this.$http({
                            method: 'post',
                            url: '/api/short_rent/delete',
                            data: {
                                id: this.ShortRentData[6*(this.currentpage - 1) + index]['id']
                            }
                        }).then((res) => {
                            this.$notify({
                                title: '成功',
                                message: '成功删除相应记录',
                                type: 'success'
                            });
                            this.ShortRentData.splice(6*(this.currentpage - 1) +index, 1);
                        }).catch((err) => {
                            console.log(err);
                        });
                    }).catch(
                    _ => {
                    });
            },

            confirm2(msg, index) {
                this.$confirm(msg).then(
                    _ => {
                        this.$http({
                            method: 'post',
                            url: '/api/long_rent/delete',
                            data: {
                                id: this.LongRentData[index]['id']
                            }
                        }).then((res) => {
                            this.$notify({
                                title: '成功',
                                message: '成功删除相应记录',
                                type: 'success'
                            });
                            this.LongRentData.splice(index, 1);
                        }).catch((err) => {
                            console.log(err);
                        });
                    }).catch(
                    _ => {
                    });
            },


            confirm3(msg, index) {
                this.$confirm(msg).then(
                    _ => {
                        this.$http({
                            method: 'post',
                            url: '/api/long_rent/delete',
                            data: {
                                id: this.LongRentData[6*(this.currentpage7 - 1)+index]['id']
                            }
                        }).then((res) => {
                            this.$notify({
                                title: '成功',
                                message: '成功删除相应记录',
                                type: 'success'
                            });
                            this.LongRentData.splice(6*(this.currentpage7 - 1)+index, 1);
                        }).catch((err) => {
                            console.log(err);
                        });
                    }).catch(
                    _ => {
                    });
            },

            shortRentApply: function (index, row, type) {
                if (type === 1) {
                    if (this.ShortRentData[6*(this.currentpage - 1) +index].status === '通过') {
                        this.$notify({
                            title:"失败",
                            message:"合同已审核，无需重复操作",
                            type:"warning"
                        });
                        //alert('合同已经审核，无需重复操作');
                    } else {
                        this.$http({
                            method: 'post',
                            url: '/api/notice/save_notice',
                            data: {
                                tenant_id: row.tenantId,
                                object_id: this.ShortRentData[6*(this.currentpage - 1) +index]['id'],
                                type: "shortRentApply",
                                content: "您的短租订单" + row.id + "已经被审核辣",
                                is_read: false
                            }
                        }).then((res) => {
                            this.$http({
                                method: 'post',
                                url: '/api/short_rent/update/pass',
                                data: {
                                    id: this.ShortRentData[6*(this.currentpage - 1) +index]['id'],
                                    pass: true
                                }
                            }).then((res) => {
                                this.ShortRentData[6*(this.currentpage - 1) +index].status = '通过';
                            }).catch((err) => {
                                alert(err);
                            });
                            this.$notify({
                                title:"成功",
                                message:"操作成功",
                                type:"success"
                            });
                        }).catch((err) => {
                            console.log(err);
                        });
                    }
                } else {
                    if (this.ShortRentData[6*(this.currentpage - 1) +index].status === '通过') {
                        this.$notify({
                            title:"失败",
                            message:"合同已通过，无法操作",
                            type:"warning"
                        });
                    } else if (this.ShortRentData[6*(this.currentpage - 1) +index].isPayed === "未通过") {
                        this.$notify({
                            title:"失败",
                            message:"合同已拒绝，无需重复操作",
                            type:"warning"
                        });
                    } else {
                        this.$http({
                            method: 'post',
                            url: '/api/notice/save_notice',
                            data: {
                                tenant_id: row.tenantId,
                                object_id: this.ShortRentData[6*(this.currentpage - 1) +index]['id'],
                                type: "shortRentApply",
                                content: "您的短租订单" + row.id + "审核被拒绝",
                                is_read: false
                            }
                        }).then((res) => {
                            this.$http({
                                method: 'post',
                                url: '/api/short_rent/update/pass',
                                data: {
                                    id: this.ShortRentData[6*(this.currentpage - 1) +index]['id'],
                                    pass: false
                                }
                            }).then((res) => {
                                this.ShortRentData[6*(this.currentpage - 1) +index].status = '未通过';
                            }).catch((err) => {
                                alert(err);
                            });
                            this.$notify({
                                title:"成功",
                                message:"操作成功",
                                type:"success"
                            });
                        }).catch((err) => {
                            console.log(err);
                        });
                    }
                }
            },

            longRentApply: function (index, row, type) {
                if (type === 1) {
                    if (this.LongRentData[6*(this.longRentPage - 1) + index].status === '通过') {
                        this.$notify({
                            title:"失败",
                            message:"合同已审核，无需重复操作",
                            type:"warning"
                        });
                    } else {
                        this.$http({
                            method: 'post',
                            url: '/api/notice/save_notice',
                            data: {
                                tenant_id: row.tenantId,
                                object_id: this.LongRentData[6*(this.longRentPage - 1) +index]['id'],
                                type: "longRentApply",
                                content: "您的长租订单" + row.id + "已经被审核辣",
                                is_read: false
                            }
                        }).then((res) => {
                            this.$http({
                                method: 'post',
                                url: '/api/long_rent/update/pass',
                                data: {
                                    id: this.LongRentData[6*(this.longRentPage - 1) +index]['id'],
                                    pass: true
                                }
                            }).then((res) => {
                                this.LongRentData[6*(this.longRentPage - 1) +index].status = '通过';
                                this.$notify({
                                    title:"成功",
                                    message:"操作成功",
                                    type:"success"
                                });
                            }).catch((err) => {
                                alert(err);
                            })
                        }).catch((err) => {
                            console.log(err);
                        });
                    }
                } else {
                   if (this.LongRentData[6*(this.longRentPage - 1) +index].contractStatus === '已签署') {
                       this.$notify({
                           title:"失败",
                           message:"合同已签约，无法改变审核状态",
                           type:"warning"
                       });
                    } else {
                        this.$http({
                            method: 'post',
                            url: '/api/notice/save_notice',
                            data: {
                                tenant_id: row.tenantId,
                                object_id: this.LongRentData[6*(this.longRentPage - 1) +index]['id'],
                                type: "longRentApply",
                                content: "您的长租订单" + row.id + "审核被拒绝",
                                is_read: false
                            }
                        }).then((res) => {
                            this.$http({
                                method: 'post',
                                url: '/api/long_rent/update/pass',
                                data: {
                                    id: this.LongRentData[6*(this.longRentPage - 1) +index]['id'],
                                    pass: false
                                }
                            }).then((res) => {
                                this.LongRentData[6*(this.longRentPage - 1) +index].status = '未通过';
                                this.$notify({
                                    title:"成功",
                                    message:"操作成功",
                                    type:"success"
                                });
                            }).catch((err) => {
                                alert(err);
                            })
                        }).catch((err) => {
                            console.log(err);
                        });
                    }
                }
            },

            signContract: function (index, row) {
                if (this.longRentDataNew[6*(this.currentpage7 - 1)+index].contractStatus === '已签署') {
                    this.$notify({
                        title:"失败",
                        message:"合同已签署，无需重复操作",
                        type:"warning"
                    });
                } else {
                    this.$http({
                        method: 'post',
                        url: '/api/notice/save_notice',
                        data: {
                            tenant_id: row.tenantId,
                            object_id: this.longRentDataNew[6*(this.currentpage7 - 1)+index]['id'],
                            type: "longRentApply",
                            content: "您的长租订单" + row.id + "已经被签署辣",
                            is_read: false
                        }
                    }).then((res) => {
                        this.$http({
                            method: 'post',
                            url: '/api/long_rent/update_contract',
                            data: {
                                long_rent_id: this.longRentDataNew[6*(this.currentpage7 - 1)+index]['id'],
                            }
                        }).then((res) => {
                            this.longRentDataNew[6*(this.currentpage7 - 1)+index].contractStatus = '已签署';
                            this.$notify({
                                title:"成功",
                                message:"操作成功",
                                type:"success"
                            });
                        }).catch((err) => {
                            alert(err);
                        })
                    }).catch((err) => {
                        console.log(err);
                    });
                }
            },
            handleSizeChange() {

            },
            closeAddRoom() {
                this.dialogAddVisible = false;
            },
            open() {
                this.$alert('联系方式:' + this.Number);
            },
            deleteRow(index, rows) {
                rows.splice(index, 1);
            },
            handleShortRentDelete(index, row) {
                this.tmpIndex = index;
                this.dialogAlert = true;
            },
            handleDelete(index,row) {
                this.$http({
                    method:"post",
                    url:"/api/delete_tenant",
                    data:{
                        id:this.renters[6*(this.currentUserPage - 1)+index]['id']
                    }
                }).then((res)=>{
                    this.$notify({
                        title:"成功",
                        message:"您已成功删除此用户",
                        type:"success"
                    });
                    this.renters.splice(6*(this.currentUserPage - 1)+index,1);
                })
            },
            dialogFixer(index, type) {
                if(type === 1){
                    this.INDEX = 6*(this.currentRepairPage1 - 1)+index;
                    if (this.fixersUnFixed[this.INDEX].shortRentId !== '') {
                        this.Renttype = '短租';
                        this.RentId = this.fixersUnFixed[this.INDEX].shortRentId;
                    } else {
                        this.Renttype = '长租';
                        this.RentId = this.fixersUnFixed[this.INDEX].longRentId;
                    }
                    console.log(this.fixersFixed[this.INDEX]['shortRentId']);
                    this.dialogFix1 = true;
                }
                else{
                    this.INDEX = 6*(this.currentRepairPage2 - 1)+index;
                    //alert(this.INDEX);
                    if (this.fixersFixed[this.INDEX].shortRentId !== '') {
                        this.Renttype = '短租';
                        this.RentId = this.fixersFixed[this.INDEX].shortRentId;
                    } else {
                        this.Renttype = '长租';
                        this.RentId = this.fixersFixed[this.INDEX].longRentId;
                    }
                    console.log(this.fixersFixed[this.INDEX]['shortRentId']);
                    this.dialogFix2 = true;
                }
            },

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

  .el-table .warning-row {
    background: red;
  }

  .el-table .success-row {
    background: green;
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

  .page-divided {
    margin-top: 3%;
  }

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
  }


</style>
