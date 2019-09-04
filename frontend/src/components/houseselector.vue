<template>
  <div id="selector">
    <div style="padding-left: 15%">
      <el-select v-model="value1" clearable placeholder="请选择" v-on:change="changeTerm">
        <el-option
          v-for="item in options1"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <el-select
        v-model="value2"
        clearable
        placeholder="请选择"
        style="margin-left: 20px"
        v-show="islongrent"
      >
        <el-option
          v-for="item in options2"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>

      <el-select
        v-model="value3"
        clearable
        collapse-tags
        style="margin-left: 20px;"
        placeholder="请选择"
        v-show="isshortrent"
      >
        <el-option
          v-for="item in options3"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <el-select
        v-model="value4"
        clearable
        collapse-tags
        style="margin-left: 20px;"
        placeholder="请选择"
      >
        <el-option
          v-for="item in options4"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <el-select
        v-model="value5"
        clearable
        collapse-tags
        style="margin-left: 20px;"
        placeholder="请选择"
      >
        <el-option
          v-for="item in options5"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <el-button type="primary" icon="el-icon-search" class="searchbtn" v-on:click="searchhouse">搜索</el-button>
    </div>
    <div class="details agileits w3layouts" style="margin-top: 3%;">
      <div class="container">
        <transition name="el-zoom-in-center">
          <div class="details-grids agileits w3layouts" v-show="showSearch">
            <div
              class="col-md-4 col-sm-4 details-grid agileits w3layouts details-grid-1 wow slideInUp"
              v-for="(item,index) in items.slice((this.currentPage3-1)*6,6*this.currentPage3)"
              style="margin-top: 3%"
              :key="index"
            >
              <div class="details-grid1 agileits w3layouts">
                <img v-bind:src="root+item['hdpiImages'][0]" />
                <div class="details-grid-info agileits w3layouts" style="height: 200px">
                  <p>{{item['description']}}</p>
                </div>
                <el-button @click="toGallery(item['id'],item['location'])" type="success">点击前往</el-button>
                <div class="clearfix"></div>
              </div>
            </div>
          </div>
        </transition>
      </div>
    </div>
    <div id="pagination" style="padding-left: 35%;margin-top: 3%">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage3"
        :page-size="6"
        layout="prev, pager, next, jumper"
        :total="this.items.length"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "houseselector",
  data: function() {
    return {
      showSearch: false,
      root: "https://lzmmmmm.oss-cn-beijing.aliyuncs.com/",
      options1: [
        {
          value: "1",
          label: "短租"
        },
        {
          value: "2",
          label: "长租"
        }
      ],
      options2: [
        {
          value: "1",
          label: "0-200每天"
        },
        {
          value: "2",
          label: "200-350每天"
        },
        {
          value: "3",
          label: "350-500每天"
        }
      ],
      options3: [
        {
          value: "1",
          label: "1000-3500每月"
        },
        {
          value: "2",
          label: "3500-6000每月"
        },
        {
          value: "3",
          label: "6000-8500每月"
        }
      ],
      options4: [
        {
          value: "1",
          label: "昌平区"
        },
        {
          value: "2",
          label: "海淀区"
        },
        {
          value: "3",
          label: "朝阳区"
        },
        {
          value: "4",
          label: "房山区"
        }
      ],
      options5: [
        {
          value: 1,
          label: "单人间"
        },
        {
          value: 2,
          label: "双人间"
        },
        {
          value: 3,
          label: "四人间"
        }
      ],
      value1: "",
      value2: "",
      value3: "",
      value4: "",
      value5: "",
      islongrent: false,
      isshortrent: false,
      items: [],
      currentPage3: 1
    };
  },
  methods: {
    toGallery: function(val, loc) {
      window.location.href = "/gallery?index=" + val + "&loc=" + loc;
    },
    changeTerm: function() {
      console.log(this.value1);
      if (this.value1 === "1") {
        this.islongrent = true;
        this.isshortrent = false;
      } else if (this.value1 === "2") {
        this.islongrent = false;
        this.isshortrent = true;
      } else {
        this.islongrent = false;
        this.isshortrent = false;
      }
    },
    searchhouse: function() {
      this.showSearch = true;
      let type;
      let up = 1000;
      let down = 0;
      let url = "/api/search_rooms_by_type_short_price_range_city";
      let city = null;
      let is_type = true;
      let is_ls = true;
      let is_city = true;
      let rentType = 0;
      if (this.value1 === "1") {
        rentType = 1;
        switch (this.value2) {
          case "1":
            up = 200;
            down = 0;
            break;
          case "2":
            up = 350;
            down = 200;
            break;
          case "3":
            up = 500;
            down = 350;
            break;
          default:
            up = 1000;
            down = 0;
        }
      } else if (this.value1 === "2") {
        rentType = 2;
        switch (this.value3) {
          case "1":
            up = 3500;
            down = 1000;
            break;
          case "2":
            up = 6000;
            down = 3500;
            break;
          case "3":
            up = 8500;
            down = 6000;
            break;
          default:
            up = 8500;
            down = 1000;
        }
      } else {
        is_ls = false;
      }
      switch (this.value4) {
        case "1":
          city = "昌平区";
          break;
        case "2":
          city = "海淀区";
          break;
        case "3":
          city = "朝阳区";
          break;
        case "4":
          city = "房山区";
          break;
        default:
          is_city = false;
      }
      switch (this.value5) {
        case 1:
          type = 1;
          break;
        case 2:
          type = 2;
          break;
        case 3:
          type = 3;
          break;
        default:
          is_type = false;
      }
      if (is_city && is_type && is_ls) {
        if (rentType === 1) {
          this.$http({
            method: "get",
            url: "/api/search_rooms_by_type_short_price_range_city",
            params: {
              up: up,
              down: down,
              city: city,
              type: type
            }
          })
            .then(res => {
              this.items = res.data;
              console.log(res.data);
            })
            .catch(error => {
              console.warn(error);
            });
        } else if (rentType === 2) {
          this.$http({
            method: "get",
            url: "/api/search_rooms_by_type_long_price_range_city",
            params: {
              up: up,
              down: down,
              city: city,
              type: type
            }
          })
            .then(res => {
              this.items = res.data;
              console.log(res.data);
            })
            .catch(error => {
              console.warn(error);
            });
        }
      } else if (is_city && !is_type && !is_ls) {
        this.$http({
          method: "get",
          url: "/api/search_rooms_by_city",
          params: {
            city: city
          }
        })
          .then(res => {
            this.items = res.data;
            console.log(res.data);
          })
          .catch(error => {
            console.warn(error);
          });
      } else if (is_ls && !is_type && !is_city) {
        if (rentType === 1) {
          this.$http({
            method: "get",
            url: "/api/search_rooms_by_short_price_range",
            params: {
              up: up,
              down: down
            }
          })
            .then(res => {
              this.items = res.data;
              console.log(res.data);
            })
            .catch(error => {
              console.warn(error);
            });
        } else if (rentType === 2) {
          this.$http({
            method: "get",
            url: "/api/search_rooms_by_long_price_range",
            params: {
              up: up,
              down: down
            }
          })
            .then(res => {
              this.items = res.data;
              console.log(res.data);
            })
            .catch(error => {
              console.warn(error);
            });
        }
      } else if (!is_city && is_type && !is_ls) {
        this.$http({
          method: "get",
          url: "/api/search_rooms_by_type",
          params: {
            type: type
          }
        })
          .then(res => {
            this.items = res.data;
            console.log(res.data);
          })
          .catch(error => {
            console.warn(error);
          });
      } else if (!is_city && !is_type && !is_ls) {
        this.$http({
          method: "get",
          url: "/api/get_all_available_rooms",
          params: {}
        })
          .then(res => {
            this.items = res.data;
            console.log(res.data);
          })
          .catch(error => {
            console.warn(error);
          });
      }
    }
  },
  handleSizeChange(val) {
    console.log(`每页 ${val} 条`);
  }
};
</script>

<style scoped>
#selector {
  margin-top: 3%;
}

.searchbtn {
  margin-left: 4%;
}
</style>
