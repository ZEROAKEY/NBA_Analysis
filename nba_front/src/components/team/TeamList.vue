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

      <div class="infobox">
        <!-- 球队详情 -->
        <el-card class="tinfo">
          <div>
            <div class="teamPic">
              <img :src="chooseInfoList.team_logo" alt="" style="width: 200px; height: 200px" />
            </div>
            <h1>{{ this.chooseInfoList.team_name }}</h1>
            <div class="teamInfo">
              <span>地点：{{ this.chooseInfoList.team_location }}</span>
              <el-divider></el-divider>
              <span>球队名称：{{ this.chooseInfoList.team_alias }}</span>
              <el-divider></el-divider>
              <span>赛季：{{ this.chooseInfoList.seasons }}</span>
              <el-divider></el-divider>
              <span>战绩：{{ this.chooseInfoList.record }}</span>
              <el-divider></el-divider>
              <span>季后赛出场次数：{{ this.chooseInfoList.playoff_appearances }}</span>
              <el-divider></el-divider>
              <span>冠军次数：{{ this.chooseInfoList.championships_won }}</span>
              <el-divider></el-divider>
            </div>
          </div>
        </el-card>
        <el-card style="width: 45%;">
          <div class="teamecharts">
            <div id="myChart"></div>
          </div>
        </el-card>
        <div class="teambox">
          <el-card style="height: 99.75%;">
            <div class="team-grid">
              <div class="team-item" v-for="team in InfoList" :key="team.team_id">
                <img :src="team.team_logo" alt="" style="width: 60px; height: 60px; cursor: pointer"
                  @click="changeTeam(team.team_id)" />
              </div>
            </div>
          </el-card>

        </div>
        <!-- 卡牌视图 -->
        <!-- 球队对应球员个人信息 -->

      </div>
      <div class="pinfo">
        <el-card>
          <!-- 搜索与输入框 -->
          <el-row :gutter="20">
            <el-col :span="12">
              <el-input placeholder="请输入球员姓名（支持模糊搜索）" v-model="queryAllPlayer.player_name" clearable
                @clear="getAllPlayerInfoList">
                <el-button slot="append" icon="el-icon-search" @click="getAllPlayerInfoList"></el-button>
              </el-input>
            </el-col>
          </el-row>
          <el-table :data="pInfoList" border stripe>
            <el-table-column type="index" :index="indexMethod"></el-table-column>
            <el-table-column label="姓名" prop="player_name"></el-table-column>
            <el-table-column label="出手数" prop="field_goals_attempted"></el-table-column>
            <el-table-column label="命中率" prop="field_goal_percentage"></el-table-column>
            <el-table-column label="场均得分" prop="points_per_game"></el-table-column>
            <el-table-column label="得分" prop="points"></el-table-column>
            <el-table-column label="参赛场次" prop="games_played"></el-table-column>
            <el-table-column label="场均出场时间" prop="minutes_per_game"></el-table-column>
            <el-table-column label="总出场时间" prop="minutes_played"></el-table-column>
            <el-table-column label="职业生涯开始年份" prop="career_start_year"></el-table-column>
            <el-table-column label="职业生涯结束年份" prop="career_end_year"></el-table-column>
            <el-table-column label="职业生涯年数" prop="years_played"></el-table-column>
          </el-table>
          <!-- 分页组件 -->
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="queryPlayer.pageNum" :page-sizes="[10, 20, 50]" :page-size="queryPlayer.pageSize"
            layout="total, sizes, prev, pager, next, jumper" :total="total">
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
</template>

<script>
let echarts = require("echarts");
export default {
  created() {
    //获取球员信息列表
    this.getPlayerInfoList();
    //获取球队信息列表
    this.getTeamInfoListW();
    this.getTeamInfoListC();
    this.getTeamInfoListR();
  },
  data() {
    return {
      total: 0,
      //权限列表
      InfoList: [],
      eInfoList: [],
      pInfoList: [],
      chooseInfoList: [],
      RankList: [],
      allpInfoList: [],
      //获取所以球员信息列表的参数
      queryAllPlayer: {
        player_name: "",
        pageNum: 1,
        pageSize: 20,
      },
      //获取球员信息列表的参数
      queryPlayer: {
        team_id: 1, //默认访问老鹰
        pageNum: 1,
        pageSize: 20,
      },
      queryW: {
        pageNum: 1,
        pageSize: 50,
        season_year: 2020,
      },
      queryC: {
        pageNum: 1,
        pageSize: 50,
        season_year: 2020,
        team_id: 1,
      },
      //柱状图数据参数
      queryR: {
        season_year: 2020,
        team_id: 1,
      },
      myChart: null, // 将 myChart 保存为实例的一个属性
    };
  },
  mounted() {
    // 初始化图表
    this.drawLine();

    // 监听窗口大小变化事件
    window.addEventListener("resize", this.handleResize);
  },
  beforeDestroy() {
    // 移除窗口大小变化事件监听器
    window.removeEventListener("resize", this.handleResize);
  },
  methods: {
    drawLine() {
      // 基于准备好的 dom，初始化 echarts 实例
      this.myChart = echarts.init(document.getElementById("myChart"));
      // 绘制图表
      this.myChart.setOption({
        title: { text: "2020-2024赛季总数据对比", top: '5%', },
        tooltip: {
          trigger: "axis",
          axisPointer: { type: "shadow" },
          textStyle: { fontSize: 18 },
        },
        grid: {
          top: '20%', // 增加 grid 的 top 值，控制图表与标题和 legend 之间的间距
        },
        legend: {
          data: ["本队场均", "联盟平均值", "联盟最高值"],
          textStyle: { fontSize: 18 },
          top: '10%',
        },
        xAxis: [
          {
            type: "category",
            data: ["场均得分", "场均篮板", "场均助攻", "场均抢断", "场均盖帽"],
            textStyle: { fontSize: 20 },
          },
          {
            type: "category",
            position: "bottom",
            offset: 25,
            axisPointer: { type: "none" },
            axisTick: { show: false },
            axisLine: { show: false },
            data: [
              "联盟第" + this.RankList.points_per_game_rank,
              "联盟第" + this.RankList.rebounds_per_game_rank,
              "联盟第" + this.RankList.assists_per_game_rank,
              "联盟第" + this.RankList.steals_per_game_rank,
              "联盟第" + this.RankList.blocks_per_game_rank,
            ],
            textStyle: { fontSize: 20 },
          },
        ],
        yAxis: { type: "value" },
        series: [
          {
            name: "本队场均",
            type: "bar",
            data: [
              this.RankList.points_per_game,
              this.RankList.rebounds_per_game,
              this.RankList.assists_per_game,
              this.RankList.steals_per_game,
              this.RankList.blocks_per_game,
            ],
          },
          {
            name: "联盟平均值",
            type: "bar",
            data: [
              this.RankList.points_per_game_avg,
              this.RankList.total_rebounds_avg,
              this.RankList.assists_per_game_avg,
              this.RankList.steals_per_game_avg,
              this.RankList.blocks_per_game_avg,
            ],
          },
          {
            name: "联盟最高值",
            type: "bar",
            data: [
              this.RankList.points_per_game_max,
              this.RankList.rebounds_per_game_max,
              this.RankList.assists_per_game_max,
              this.RankList.steals_per_game_max,
              this.RankList.blocks_per_game_max,
            ],
          },
        ],
      });
    },
    handleResize() {
      // 调整图表大小
      if (this.myChart) {
        this.myChart.resize();
      }
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
        this.InfoList = res.data.pageData;
        // console.log(res);
        this.$message.success("获取东部球队信息列表成功");
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
        // console.log(this.queryC.team_id);
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
    changeTeam(team_id) {
      this.queryC.team_id = team_id;
      this.queryPlayer.team_id = team_id;
      this.queryR.team_id = team_id;
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
.infobox {
  margin-left: 20px;
  display: flex;
  flex-direction: row;
}

.tinfo {
  display: flex;
  justify-content: flex-start;
  min-width: 600px;
  /* 设置一个最小宽度，避免收缩 */
  box-sizing: border-box;
  width: 30%;

  .teamInfo {
    width: 100%;
  }
}

.teamecharts {
  /* 如果希望防止内容收缩，给它一个最小宽度 */
  box-sizing: border-box;
}

.teambox {
  
  width: 25%;
}

.team-grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  height: 700px;
}

.team-item {
  width: 16%;
  /* 100% / 6 = 16.66% for 6 items per row */
  margin-bottom: 20px;
  /* Add some margin between rows */
  display: flex;
  justify-content: center;
  align-items: center;
}

.pinfo {
  margin-left: 20px;
}

#myChart {
  width: 100%;
  /* 设置为 100% 宽度 */
  height: 600px;
  /* 高度可以固定 */
}
</style>
