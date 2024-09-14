<template>
  <div>
    <!-- 面包屑区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">{{ $t('message.home') }}</el-breadcrumb-item>
      <el-breadcrumb-item>{{ $t('message.team_management') }}</el-breadcrumb-item>
      <el-breadcrumb-item>{{ $t('message.team_details') }}</el-breadcrumb-item>
    </el-breadcrumb>

    <div class="tbox">
      <!-- 球队详情 -->
      <div class="infobox">
        <el-card class="tinfo">
          <div>
            <div class="teamPic">
              <img :src="chooseInfoList.team_logo" alt="" style="width: 200px; height: 200px" />
            </div>
            <h1>{{ this.chooseInfoList.team_name }}</h1>
            <div class="teamInfo">
              <span>{{ $t('message.location') }}: {{ chooseInfoList.team_location }}</span>
              <div class="underline"></div>
              <span>{{ $t('message.team_name') }}: {{ chooseInfoList.team_alias }}</span>
              <div class="underline"></div>
              <span>{{ $t('message.season') }}: {{ chooseInfoList.seasons }}</span>
              <div class="underline"></div>
              <span>{{ $t('message.record') }}: {{ chooseInfoList.record }}</span>
              <div class="underline"></div>
              <span>{{ $t('message.playoff_appearances') }}: {{ chooseInfoList.playoff_appearances }}</span>
              <div class="underline"></div>
              <span>{{ $t('message.championships_won') }}: {{ chooseInfoList.championships_won }}</span>
              <div class="underline"></div>
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
              <div class="team-item" v-for="team in InfoList" :key="team.team_id"
                :class="{ 'selected-team': team.team_id === queryC.team_id }">
                <img :src="team.team_logo" alt="" style="width: 60px; height: 60px; cursor: pointer"
                  @click="changeTeam(team.team_id)" />
              </div>
            </div>
          </el-card>
        </div>
      </div>

      <!-- 球员个人信息 -->
      <div class="pinfo">
        <el-card>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-input :placeholder="$t('message.search') + $t('message.player')" v-model="queryAllPlayer.player_name"
                clearable @clear="getAllPlayerInfoList">
                <el-button slot="append" icon="el-icon-search" @click="getAllPlayerInfoList"></el-button>
              </el-input>
            </el-col>
          </el-row>

          <el-table :data="pInfoList" border stripe>
            <el-table-column type="index" :index="indexMethod"></el-table-column>
            <el-table-column :label="$t('message.player')" prop="player_name"></el-table-column>
            <el-table-column :label="$t('message.field_goals_attempted')"
              prop="field_goals_attempted"></el-table-column>
            <el-table-column :label="$t('message.field_goal_percentage')"
              prop="field_goal_percentage"></el-table-column>
            <el-table-column :label="$t('message.avg_score')" prop="points_per_game"></el-table-column>
            <el-table-column :label="$t('message.points')" prop="points"></el-table-column>
            <el-table-column :label="$t('message.games_played')" prop="games_played"></el-table-column>
            <el-table-column :label="$t('message.minutes_per_game')" prop="minutes_per_game"></el-table-column>
            <el-table-column :label="$t('message.career_start_year')" prop="career_start_year"></el-table-column>
            <el-table-column :label="$t('message.career_end_year')" prop="career_end_year"></el-table-column>
            <el-table-column :label="$t('message.years_played')" prop="years_played"></el-table-column>
          </el-table>

          <!-- 分页组件 -->
          <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="queryPlayer.pageNum" :page-sizes="[10, 20, 50]" :page-size="queryPlayer.pageSize"
            layout="total, sizes, prev, pager, next, jumper" :total="total">
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
  watch: {
    // 监听 vue-i18n 的 locale 变化，重新渲染图表
    '$i18n.locale'(newLocale) {
      this.drawLine(); // 语言切换时，重新绘制图表
    }
  },
  methods: {
    drawLine() {
      // 获取当前语言下的文本
      const teamAvgText = this.$t('message.team_avg');
      const leagueAvgText = this.$t('message.league_avg');
      const leagueMaxText = this.$t('message.league_max');
      const titleText = this.$t('message.season_data_comparison');
      const categories = [
        this.$t('message.points_per_game'),
        this.$t('message.rebounds_per_game'),
        this.$t('message.assists_per_game'),
        this.$t('message.steals_per_game'),
        this.$t('message.blocks_per_game')
      ];

      const rankCategories = [
        this.$t('message.rank_prefix') + this.RankList.points_per_game_rank,
        this.$t('message.rank_prefix') + this.RankList.rebounds_per_game_rank,
        this.$t('message.rank_prefix') + this.RankList.assists_per_game_rank,
        this.$t('message.rank_prefix') + this.RankList.steals_per_game_rank,
        this.$t('message.rank_prefix') + this.RankList.blocks_per_game_rank,
      ];

      // 基于准备好的 dom，初始化 echarts 实例
      this.myChart = echarts.init(document.getElementById("myChart"));

      // 绘制图表
      this.myChart.setOption({
        title: { text: titleText, top: '5%' },
        tooltip: {
          trigger: "axis",
          axisPointer: { type: "shadow" },
          textStyle: { fontSize: 18 },
        },
        grid: {
          top: '20%',
        },
        legend: {
          data: [teamAvgText, leagueAvgText, leagueMaxText],
          textStyle: { fontSize: 18 },
          top: '10%',
        },
        xAxis: [
          {
            type: "category",
            data: categories,
            textStyle: { fontSize: 20 },
          },
          {
            type: "category",
            position: "bottom",
            offset: 25,
            axisPointer: { type: "none" },
            axisTick: { show: false },
            axisLine: { show: false },
            data: rankCategories,
            textStyle: { fontSize: 20 },
          },
        ],
        yAxis: { type: "value" },
        series: [
          {
            name: teamAvgText,
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
            name: leagueAvgText,
            type: "bar",
            data: [
              this.RankList.points_per_game_avg,
              this.RankList.rebounds_per_game_avg,
              this.RankList.assists_per_game_avg,
              this.RankList.steals_per_game_avg,
              this.RankList.blocks_per_game_avg,
            ],
          },
          {
            name: leagueMaxText,
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

.selected-team img {
  box-shadow: 0px 0px 10px 3px rgba(76, 76, 77, 0.7);
  /* 蓝色阴影效果 */
  border-radius: 50%;
  /* 使图片边缘圆滑 */
}

.underline {
  width: 100%;
  height: 2px;
  /* 下划线的高度 */
  background-color: #EAEDF1;
  /* 下划线的颜色，可以根据需要调整 */
  margin: 5px 0 15px 0;
  /* 上下间距调整 */
}

.teamPic {
  margin-bottom: 20px;
}

#myChart {
  width: 100%;
  /* 设置为 100% 宽度 */
  height: 600px;
  /* 高度可以固定 */
}
</style>
