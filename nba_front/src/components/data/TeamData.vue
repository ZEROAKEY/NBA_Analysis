<template>
  <div>
    <!-- 面包屑区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">{{ $t('message.home') }}</el-breadcrumb-item>
      <el-breadcrumb-item>{{ $t('message.data_statistics') }}</el-breadcrumb-item>
      <el-breadcrumb-item>{{ $t('message.team_data') }}</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡牌视图 -->
    <el-card>
      <div id="chooseList">
        <!-- 选择栏-赛季 -->
        <div class="blockY">
          <el-cascader v-model="valueY" :options="optionsY" @change="handleChangeY"></el-cascader>
        </div>
        <div class="searchTeam">
          <!-- 搜索与输入框 -->
          <el-row :gutter="70">
            <el-col :span="60">
              <el-input :placeholder="$t('message.search') + $t('message.team')" v-model="queryinfoT.team_name"
                clearable @clear="getTeamDataList">
                <el-button slot="append" icon="el-icon-search" @click="getTeamDataList"></el-button>
              </el-input>
            </el-col>
          </el-row>
        </div>
      </div>

      <el-table :data="PlayerDataList" border stripe>
        <el-table-column type="index" :label="$t('message.rank')" :index="indexMethod"></el-table-column>
        <el-table-column prop="logo">
          <template slot-scope="scope">
            <div style="text-align: center">
              <img :src="scope.row.team_logo" alt=""
                style="width: 50px; height: 50px; display: block; margin: 0 auto" />
            </div>
          </template>
        </el-table-column>
        <el-table-column :label="$t('message.team')" prop="team_name">
          <template slot-scope="scope">
            <span>{{ $t(`message.${scope.row.team_name.toLowerCase().replace(/\s+/g, '_')}`) }}</span>
          </template>
        </el-table-column>
        <el-table-column :label="$t('message.points')" prop="points" sortable></el-table-column>
        <el-table-column :label="$t('message.field_goals_attempted')" prop="field_goals_attempted"
          sortable></el-table-column>
        <el-table-column :label="$t('message.field_goal_percentage')" prop="field_goal_percentage"
          sortable></el-table-column>
        <el-table-column :label="$t('message.three_point_attempted')" prop="three_pointer_attempted"
          sortable></el-table-column>
        <el-table-column :label="$t('message.three_point_percentage')" prop="three_pointer_percentage"
          sortable></el-table-column>
        <el-table-column :label="$t('message.free_throw_attempts')" prop="free_throws_attempted"
          sortable></el-table-column>
        <el-table-column :label="$t('message.free_throw_percentage')" prop="free_throw_percentage"
          sortable></el-table-column>
        <el-table-column :label="$t('message.rebounds')" prop="total_rebounds" sortable></el-table-column>
        <el-table-column :label="$t('message.offensive_rebounds')" prop="offensive_rebounds" sortable></el-table-column>
        <el-table-column :label="$t('message.defensive_rebounds')" prop="defensive_rebounds" sortable></el-table-column>
        <el-table-column :label="$t('message.assists')" prop="assists" sortable></el-table-column>
        <el-table-column :label="$t('message.steals')" prop="steals" sortable></el-table-column>
        <el-table-column :label="$t('message.blocks')" prop="blocks" sortable></el-table-column>
        <el-table-column :label="$t('message.turnovers')" prop="turnovers" sortable></el-table-column>
        <el-table-column :label="$t('message.personal_fouls')" prop="personal_fouls" sortable></el-table-column>
        <el-table-column :label="$t('message.games_played')" prop="games_played" sortable></el-table-column>
      </el-table>
    </el-card>

    <!-- 分页组件 -->
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
      :current-page="queryinfoT.pageNum" :page-sizes="[10, 20, 50]" :page-size="queryinfoT.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="total">
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
        for (let i = 0; i < res.data.length; i++) {
          this.optionsY.push({
            value: res.data[i],
            label: res.data[i] - 1 + '-' + res.data[i]
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
        // 在获取数据后处理 team_name 为国际化格式
        res.data.pageData.forEach(team => {
          team.team_name = team.team_name.toLowerCase().replace(/\s+/g, '_');
        });

        this.PlayerDataList = res.data.pageData;
        this.total = res.data.totalNum;
        this.$message.success("获取球队成绩列表成功");
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
