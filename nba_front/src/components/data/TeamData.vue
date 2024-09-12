<template>
  <div>
    <!-- 面包屑区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>数据统计</el-breadcrumb-item>
      <el-breadcrumb-item>球队数据</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡牌视图 -->
    <el-card>
      <div id="chooseList">
        <!-- 选择栏-赛季 -->
        <div class="blockY">
          <span class="demonstration"></span>
          <el-cascader v-model="valueY" :options="optionsY" @change="handleChangeY"></el-cascader>
        </div>
        <div class="searchTeam">
          <!-- 搜索与输入框 -->
          <el-row :gutter="70">
            <el-col :span="60">
              <el-input placeholder="请输入球队名" v-model="queryinfoT.team_name" clearable @clear="getTeamDataList">
                <el-button slot="append" icon="el-icon-search" @click="getTeamDataList"></el-button>
              </el-input>
            </el-col>
          </el-row>
        </div>
      </div>

      <el-table :data="PlayerDataList" border stripe>
        <el-table-column type="index" label="排名" :index="indexMethod"></el-table-column>
        <el-table-column prop="logo">
          <!--插入图片链接的代码-->
          <template slot-scope="scope">
            <div style="text-align: center">
              <img :src="scope.row.team_logo" alt=""
                style="width: 50px; height: 50px; display: block; margin: 0 auto" />
            </div>
          </template>
        </el-table-column>

        <el-table-column label="球队" prop="team_name"></el-table-column>
        <el-table-column label="得分" prop="points" sortable>
        </el-table-column>
        <el-table-column label="出手数" prop="field_goals_attempted" sortable></el-table-column>
        <el-table-column label="命中率" prop="field_goal_percentage" sortable></el-table-column>
        <el-table-column label="3分出手" prop="three_pointer_attempted" sortable></el-table-column>
        <el-table-column label="3分命中率" prop="three_pointer_percentage" sortable></el-table-column>
        <el-table-column label="罚球次数" prop="free_throws_attempted" sortable></el-table-column>
        <el-table-column label="罚球命中率" prop="free_throw_percentage" sortable></el-table-column>
        <el-table-column label="篮板" prop="total_rebounds" sortable></el-table-column>
        <el-table-column label="前场篮板" prop="offensive_rebounds" sortable></el-table-column>
        <el-table-column label="后场篮板" prop="defensive_rebounds" sortable></el-table-column>
        <el-table-column label="助攻" prop="assists" sortable></el-table-column>
        <el-table-column label="抢断" prop="steals" sortable></el-table-column>
        <el-table-column label="盖帽" prop="blocks" sortable></el-table-column>
        <el-table-column label="失误" prop="turnovers" sortable></el-table-column>
        <el-table-column label="犯规" prop="personal_fouls" sortable></el-table-column>
        <el-table-column label="场次" prop="games_played" sortable></el-table-column>
      </el-table>
    </el-card>

    <!-- 分页组件 -->
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current-page="queryinfoT.pageNum" :page-sizes="[10, 20, 50]" :page-size="queryinfoT.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="total">
      <!-- size-change 每页显示多少条改变的事件
      current-change 改变页数的事件
      current-page 当前是第几页
      page-sizes 几种每页多少条的选项显示
      page-size 每页显示x条数据 -->
      <!-- layout:total：共x条 sizes:每页显示多少条的显示菜单 -->
    </el-pagination>
  </div>
</template>

<script>
export default {
  created() {
    this.getSeasonList();
    this.getTeamDataList();
  },
  data() {
    return {
      PlayerDataList: [],
      total: 0,
      //获取成绩的参数
      queryinfoT: {
        season_year: 2020,
        pageNum: 1,
        pageSize: 10, //默认50个 一页
        index: "points",
        position: "",
        team_name: "",
      },
      //选择栏数据
      valueY: [2020],
      valueM: [1],
      valueP: [],
      optionsY: [
      ],
    };
  },
  methods: {
    //获取赛季查询列表
    async getSeasonList() {
      const { data: res } = await this.$http.get("search/season", {
      });
      if (res.meta.status !== 200) {
        return this.$message.error("获取赛季列表失败");
      } else {
        for(let i =0; i< res.data.length;i++){
          this.optionsY.push({
            value: res.data[i],
            label: res.data[i]-1 +'-'+res.data[i]+'赛季'
          })
        }
      }
    },
    //获取球队成绩列表
    async getTeamDataList() {
      const { data: res } = await this.$http.get("search/team", {
        params: this.queryinfoT,
      });
      if (res.meta.status !== 200) {
        return this.$message.error("获取球队成绩列表失败");
      } else {
        this.PlayerDataList = res.data.pageData;
        // console.log(res.data);
        this.total = res.data.totalNum;
        this.$message.success("获取球对成绩列表成功");
      }
    },

    // 监听pageSize改变的事件
    handleSizeChange(newSize) {
      this.queryinfoT.pageSize = newSize;
      //重新向网页请求数据
      this.getTeamDataList();
    },
    // 监听页码值改变的事件
    handleCurrentChange(newPage) {
      // console.log(newPage);
      this.queryinfoT.pageNum = newPage;
      this.getTeamDataList();
    },
    handleChangeY(valueY) {
      //   console.log(valueY);
      this.queryinfoT.season_year = valueY[0];
      this.getTeamDataList();
    },
    handleChangeP(valueP) {
      // console.log(valueP);
      this.queryinfoT.position = valueP[0];
      this.getTeamDataList();
    },
    //序列自增且不受分页影响，(当前的页码数-1)x当前的每一页中的总条数+1(因为index从0开始计算)
    indexMethod(index) {
      return (
        (this.queryinfoT.pageNum - 1) * this.queryinfoT.pageSize + 1 + index
      );
    },
  },
};
</script>

<style lang="less" scoped>
#chooseList {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-content: flex-start;
}

.blockM {
  margin-left: 20px;
}

.blockP {
  margin-left: 20px;
}

.searchTeam {
  margin-left: 20px;
}
</style>
