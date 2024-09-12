<template>
  <div>
    <!-- 面包屑区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>数据统计</el-breadcrumb-item>
      <el-breadcrumb-item>球员数据</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡牌视图 -->
    <el-card>
      <div id="chooseList">
        <!-- 选择栏-赛季 -->
        <div class="blockY">
          <span class="demonstration"></span>
          <el-cascader
            v-model="valueY"
            :options="optionsY"
            @change="handleChangeY"
          ></el-cascader>
        </div>
        <!-- 选择栏-位置 -->
        <div class="blockP">
          <span class="demonstration"></span>
          <el-cascader
            v-model="valueP"
            :options="optionsP"
            @change="handleChangeP"
          ></el-cascader>
        </div>
        <div class="searchPlayer">
         <!-- 搜索与输入框 -->
            <el-row :gutter="70">
              <el-col :span="60">
                <el-input
                  placeholder="请输入球员名"
                  v-model="queryinfoP.player_name"
                  clearable
                  @clear="getPlayerDataList"
                >
                  <el-button
                    slot="append"
                    icon="el-icon-search"
                    @click="getPlayerDataList"
                  ></el-button>
                </el-input>
              </el-col>
            </el-row>
        </div>
      </div>

      <el-table :data="PlayerDataList" border stripe>
        <el-table-column
          type="index"
          label="排名"
          :index="indexMethod"
        ></el-table-column>
        <el-table-column label="球员" prop="player_name"></el-table-column>
        <el-table-column label="球队" prop="team_name"></el-table-column>
        <el-table-column label="位置" prop="position"></el-table-column>
        <el-table-column label="场均得分" prop="points_per_game" sortable>
        </el-table-column>
        <el-table-column
          label="出手数"
          prop="field_goals_attempted"
          sortable
        ></el-table-column>
        <el-table-column label="命中率" prop="field_goal_pct" sortable></el-table-column>
        <el-table-column
          label="3分出手"
          prop="three_pointer_attempted"
          sortable
        ></el-table-column>
        <el-table-column
          label="3分命中率"
          prop="three_pointer_pct"
          sortable
        ></el-table-column>
        <el-table-column
          label="罚球次数"
          prop="free_throws_attempted"
          sortable
        ></el-table-column>
        <el-table-column
          label="罚球命中率"
          prop="free_throw_pct"
          sortable
        ></el-table-column>
        <el-table-column
          label="篮板"
          prop="total_rebounds"
          sortable
        ></el-table-column>
        <el-table-column
          label="前场篮板"
          prop="offensive_rebounds"
          sortable
        ></el-table-column>
        <el-table-column
          label="后场篮板"
          prop="defensive_rebounds"
          sortable
        ></el-table-column>
        <el-table-column
          label="助攻"
          prop="assists"
          sortable
        ></el-table-column>
        <el-table-column
          label="抢断"
          prop="steals"
          sortable
        ></el-table-column>
        <el-table-column
          label="盖帽"
          prop="blocks"
          sortable
        ></el-table-column>
        <el-table-column
          label="失误"
          prop="turnovers"
          sortable
        ></el-table-column>
        <el-table-column label="犯规" prop="personal_fouls" sortable></el-table-column>
        <el-table-column label="场次" prop="games_played" sortable></el-table-column>
        <el-table-column
          label="场均出场时间"
          prop="minutes_per_game"
          sortable
        ></el-table-column>
      </el-table>
    </el-card>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryinfoP.pageNum"
      :page-sizes="[10,20, 50]"
      :page-size="queryinfoP.pageSize"
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
  </div>
</template>

<script>
export default {
  created() {
    //获取所有的权限
    this.getSeasonList();
    this.getPositionList();
    this.getPlayerDataList();
  },
  data() {
    return {
      //权限列表
      PlayerDataList: [],
      total: 0,
      //获取球员成绩的参数
      queryinfoP: {
        season_year: 2020,
        pageNum: 1,
        pageSize: 10, //默认10个 一页
        index: "points",
        position: "",
        player_name:"",
      },
      //选择栏数据
      valueY: [2020],
      valueM: [1],
      valueP: [],
      optionsY: [],
      optionsM: [
        {
          value: 0,
          label: "季前赛",
        },
        {
          value: 1,
          label: "常规赛",
        },
        {
          value: 2,
          label: "季后赛",
        },
      ],
      optionsP: [],
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
    //获取位置列表
    async getPositionList() {
      const { data: res } = await this.$http.get("search/position", {
      });
      if (res.meta.status !== 200) {
        return this.$message.error("获取位置列表失败");
      } else {
        for(let i =0; i< res.data.length;i++){
          this.optionsP.push({
            value: res.data[i]['position'],
            label: res.data[i]['position']
          })

        }
      }
    },
    //获取球员成绩列表
    async getPlayerDataList() {
      const { data: res } = await this.$http.get("search/PlayerTotal", {
        params: this.queryinfoP,
      });
      if (res.meta.status !== 200) {
        return this.$message.error("获取球员成绩列表失败");
      } else {
        this.PlayerDataList = res.data.pageData;
        // console.log(res.data);
        this.total = res.data.totalNum;
        this.$message.success("获取球员成绩列表成功");
      }
    },

    // 监听pageSize改变的事件
    handleSizeChange(newSize) {
      this.queryinfoP.pageSize = newSize;
      //重新向网页请求数据
      this.getPlayerDataList();
    },
    // 监听页码值改变的事件
    handleCurrentChange(newPage) {
      // console.log(newPage);
      this.queryinfoP.pageNum = newPage;
      this.getPlayerDataList();
    },
    handleChangeY(valueY) {
      //   console.log(valueY);
      this.queryinfoP.season_year = valueY[0];
      this.getPlayerDataList();
    },
    handleChangeP(valueP) {
      // console.log(valueP);
      this.queryinfoP.position = valueP[0];
      this.getPlayerDataList();
    },
    //序列自增且不受分页影响，(当前的页码数-1)x当前的每一页中的总条数+1(因为index从0开始计算)
    indexMethod(index) {
        return (this.queryinfoP.pageNum-1)*(this.queryinfoP.pageSize)+1+index;
  },
  }
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
.searchPlayer {
  margin-left: 20px;
}
</style>
