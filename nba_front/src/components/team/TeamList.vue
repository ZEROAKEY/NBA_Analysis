<template>
  <div>
    <!-- 面包屑区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>球队管理</el-breadcrumb-item>
      <el-breadcrumb-item>球队详情</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="tbox">
      <!-- 球队列表 -->
      <div class="teambox">
        <el-card>
          <el-table :data="eInfoList">
            <el-table-column label="东部" prop="logo">
              <!--插入图片链接的代码-->
              <template slot-scope="scope">
                <img
                  :src="scope.row.logo"
                  alt=""
                  style="width: 50px; height: 50px"
                  @click="changeTeam(scope.row.teamId)"
                />
              </template>
            </el-table-column>
          </el-table>
          <el-table :data="wInfoList">
            <el-table-column label="西部" prop="path">
              <!--插入图片链接的代码-->
              <template slot-scope="scope">
                <img
                  :src="scope.row.logo"
                  alt=""
                  style="width: 50px; height: 50px"
                  @click="changeTeam(scope.row.teamId)"
                />
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </div>
      <div class="infobox">
        <!-- 球队详情 -->
        <div class="tinfo">
          <el-card>
            <div class="teamPic">
              <img
                :src="chooseInfoList.logo"
                alt=""
                style="width: 200px; height: 200px"
              />
            </div>
            <div class="teamInfo">
              <span>队名：{{ this.chooseInfoList.cnName }}</span>
              <el-divider></el-divider>
              <span>全名：{{ this.chooseInfoList.fullCnName }}</span>
              <el-divider></el-divider>
              <span>城市：{{ this.chooseInfoList.city }}</span>
              <el-divider></el-divider>
              <span>联盟：{{ this.chooseInfoList.conference }}</span>
              <el-divider></el-divider>
              <span>教练：{{ this.chooseInfoList.coach }}</span>
              <el-divider></el-divider>
              <span>主场馆：{{ this.chooseInfoList.venue }}</span>
            </div>
          </el-card>
          <el-card>
            <div class="teamecharts">
              <div id="myChart"></div>
            </div>
          </el-card>
        </div>
        <!-- 卡牌视图 -->
        <!-- 球队对应球员个人信息 -->
        <div class="pinfo">
          <el-card>
            <!-- 搜索与输入框 -->
            <el-row :gutter="20">
              <el-col :span="12">
                <el-input
                  placeholder="请输入球员姓名（支持中英模糊搜索）"
                  v-model="queryAllPlayer.name"
                  clearable
                  @clear="getAllPlayerInfoList"
                >
                  <el-button
                    slot="append"
                    icon="el-icon-search"
                    @click="getAllPlayerInfoList"
                  ></el-button>
                </el-input>
              </el-col>
            </el-row>
            <el-table :data="pInfoList" border stripe>
              <el-table-column
                type="index"
                :index="indexMethod"
              ></el-table-column>
              <el-table-column label="姓名" prop="cnName"></el-table-column>
              <el-table-column label="位置" prop="position"></el-table-column>
              <el-table-column label="队伍" prop="teamName"></el-table-column>
              <el-table-column label="英文" prop="enName"></el-table-column>
              <el-table-column label="号码" prop="jerseyNum"></el-table-column>
              <el-table-column label="身高(cm)" prop="height"></el-table-column>
              <el-table-column label="体重(kg)" prop="weight"></el-table-column>
              <el-table-column label="生日" prop="birthDate"></el-table-column>
              <el-table-column
                label="被选秀年"
                prop="draftYear"
              ></el-table-column>
            </el-table>
            <!-- 分页组件 -->
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="queryPlayer.pageNum"
              :page-sizes="[10, 20, 50]"
              :page-size="queryPlayer.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
            >
              <!-- size-change 每页显示多少条改变的事件
      current-change 改变页数的事件
      current-page 当前是第几页
      page-sizes 几种每页多少条的选项显示
      page-size 每页显示x条数据 -->
              <!-- layout:total：共x条 sizes:每页显示多少条的显示菜单 -->
            </el-pagination>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
let echarts = require("echarts");
export default {
  created() {
    //获取球员信息列表
    this.getPlayerInfoList();
    //获取球队信息列表
    this.getTeamInfoListW();
    this.getTeamInfoListE();
    this.getTeamInfoListC();
    this.getTeamInfoListR();
  },
  data() {
    return {
      total: 0,
      //权限列表
      wInfoList: [],
      eInfoList: [],
      pInfoList: [],
      chooseInfoList: [],
      RankList: [],
      allpInfoList: [],
      //获取所以球员信息列表的参数
      queryAllPlayer: {
        name: "",
        pageNum: 1,
        pageSize: 20,
      },
      //获取球员信息列表的参数
      queryPlayer: {
        teamId: 1, //默认访问老鹰
        pageNum: 1,
        pageSize: 20,
      },
      queryW: {
        pageNum: 1,
        pageSize: 50,
        seasonId: 2020,
        seasonType: 1,
        conference: "west",
      },
      queryE: {
        pageNum: 1,
        pageSize: 50,
        seasonId: 2020,
        seasonType: 1,
        conference: "east",
      },
      queryC: {
        pageNum: 1,
        pageSize: 50,
        seasonId: 2020,
        seasonType: 1,
        teamId: 1,
      },
      //柱状图数据参数
      queryR: {
        seasonId: 2020,
        seasonType: 1,
        teamId: 1,
      },
    };
  },
  mounted() {
    // this.drawLine();
  },
  methods: {
    //echarts表
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById("myChart"));
      // 绘制图表
      myChart.setOption({
        title: { text: "联盟对比" },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
          textStyle: {
            fontSize: 18,
          },
        },
        legend: {
          data: ["球员", "联盟平均值", "联盟最高值"],
          textStyle: {
            fontSize: 18,
          },
        },
        xAxis: [
          {
            type: "category",
            data: ["得分", "篮板", "助攻", "抢断", "盖帽"],
            textStyle: {
              fontSize: 20,
            },
          },
          {
            type: "category",
            position: "bottom",
            offset: 25,
            axisPointer: {
              type: "none",
            },
            axisTick: {
              show: false,
            },
            axisLine: {
              show: false,
            },
            data: [
              "联盟第" + this.RankList.pointsPG_rank,
              "联盟第" + this.RankList.reboundsPG_rank,
              "联盟第" + this.RankList.assistsPG_rank,
              "联盟第" + this.RankList.stealsPG_rank,
              "联盟第" + this.RankList.blocksPG_rank,
            ],
            textStyle: {
              fontSize: 20,
            },
          },
        ],
        yAxis: {
          type: "value",
        },
        series: [
          {
            name: "球员",
            type: "bar",
            data: [
              this.RankList.pointsPG,
              this.RankList.reboundsPG,
              this.RankList.assistsPG,
              this.RankList.stealsPG,
              this.RankList.blocksPG,
            ],
          },
          {
            name: "联盟平均值",
            type: "bar",
            data: [
              this.RankList.pointsPG_avg,
              this.RankList.reboundsPG_avg,
              this.RankList.assistsPG_avg,
              this.RankList.stealsPG_avg,
              this.RankList.blocksPG_avg,
            ],
          },
          {
            name: "联盟最高值",
            type: "bar",
            data: [
              this.RankList.pointsPG_max,
              this.RankList.reboundsPG_max,
              this.RankList.assistsPG_max,
              this.RankList.stealsPG_max,
              this.RankList.blocksPG_rank,
            ],
          },
        ],
      });
    },
    async getAllPlayerInfoList() {
      const { data: res } = await this.$http.get("search/player", {
        params: this.queryAllPlayer,
      });
      if (res.meta.status !== 200) {
        return this.$message.error("获取所有球员信息列表失败");
      } else {
        this.pInfoList = res.data.pageData;
        // console.log(res);
        this.total = res.data.totalNum;
        this.$message.success("获取所有球员信息列表成功");
      }
    },
    async getPlayerInfoList() {
      const { data: res } = await this.$http.get("search/player", {
        params: this.queryPlayer,
      });
      if (res.meta.status !== 200) {
        return this.$message.error("获取球员信息列表失败");
      } else {
        this.pInfoList = res.data.pageData;
        // console.log(res);
        this.total = res.data.totalNum;
        this.$message.success("获取球员信息列表成功");
      }
    },
    async getTeamInfoListW() {
      const { data: res } = await this.$http.get("search/team", {
        params: this.queryW,
      });
      if (res.meta.status !== 200) {
        return this.$message.error("获取东部球队信息列表失败");
      } else {
        this.wInfoList = res.data.pageData;
        // console.log(res);
        this.$message.success("获取东部球队信息列表成功");
      }
    },
    async getTeamInfoListE() {
      const { data: res } = await this.$http.get("search/team", {
        params: this.queryE,
      });
      if (res.meta.status !== 200) {
        return this.$message.error("获取西部球队信息列表失败");
      } else {
        this.eInfoList = res.data.pageData;
        // console.log(res);
        this.$message.success("获取西部球队信息列表成功");
      }
    },
    async getTeamInfoListC() {
      const { data: res } = await this.$http.get("search/team", {
        params: this.queryC,
      });

      if (res.meta.status !== 200) {
        return this.$message.error("获取所选球队信息列表失败");
      } else {
        this.chooseInfoList = res.data.pageData[0];
        // console.log(this.queryC.teamId);
        // console.log(res.data);
        // console.log(this.chooseInfoList);
        this.$message.success("获取所选球队信息列表成功");
        // this.drawLine();
      }
    },
    //获取排行柱状图数据列表
    async getTeamInfoListR() {
      const { data: res } = await this.$http.get("search/teamRank", {
        params: this.queryR,
      });

      if (res.meta.status !== 200) {
        return this.$message.error("获取球队排行信息列表失败");
      } else {
        this.RankList = res.data[0];
        // console.log(res.data);
        this.$message.success("获取所选球队排行列表成功");
        this.drawLine();
      }
    },
    // 监听pageSize改变的事件
    handleSizeChange(newSize) {
      this.queryPlayer.pageSize = newSize;
      //重新向网页请求数据
      this.getPlayerInfoList();
    },
    // 监听页码值改变的事件
    handleCurrentChange(newPage) {
      // console.log(newPage);
      this.queryPlayer.pageNum = newPage;
      this.getPlayerInfoList();
    },
    changeTeam(teamId) {
      this.queryC.teamId = teamId;
      this.queryPlayer.teamId = teamId;
      this.queryR.teamId = teamId;
      this.getPlayerInfoList();
      this.getTeamInfoListC();
      this.getTeamInfoListR();
    },
    //序列自增且不受分页影响，(当前的页码数-1)x当前的每一页中的总条数+1(因为index从0开始计算)
    indexMethod(index) {
      return (
        (this.queryPlayer.pageNum - 1) * this.queryPlayer.pageSize + 1 + index
      );
    },
  },
};
</script>

<style lang="less" scoped>
.tbox {
  display: flex;
  flex: 1;
  flex-direction: row;
  justify-content: flex-start;
  align-items: flex-start;
}
.teambox {
  flex: 0 0 auto;
}
.infobox {
  margin-left: 20px;
  flex: 1 0 auto;
  display: flex;
  flex-direction: column;
}
.tinfo {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
}
.teamecharts {
  float: right;
  flex: auto;
}
#myChart {
  width: 1000px;
  height: 600px;
}
</style>
