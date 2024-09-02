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
          <el-cascader
            v-model="valueY"
            :options="optionsY"
            @change="handleChangeY"
          ></el-cascader>
        </div>
        <!-- 选择栏-季度 -->
        <div class="blockM">
          <span class="demonstration"></span>
          <el-cascader
            v-model="valueM"
            :options="optionsM"
            @change="handleChangeM"
          ></el-cascader>
        </div>
        <div class="searchTeam">
         <!-- 搜索与输入框 -->
            <el-row :gutter="70">
              <el-col :span="60">
                <el-input
                  placeholder="请输入球队名"
                  v-model="queryinfoT.name"
                  clearable
                  @clear="getTeamDataList"
                >
                  <el-button
                    slot="append"
                    icon="el-icon-search"
                    @click="getTeamDataList"
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
        <el-table-column prop="logo">
          <!--插入图片链接的代码-->
          <template slot-scope="scope">
            <img
              :src="scope.row.logo"
              alt=""
              style="width: 50px; height: 50px"
            />
          </template>
        </el-table-column>
        <el-table-column label="球队" prop="cnName"></el-table-column>
        <el-table-column label="得分" prop="pointsPG" sortable>
        </el-table-column>
        <el-table-column
          label="出手数"
          prop="fgAttemptedPG"
          sortable
        ></el-table-column>
        <el-table-column label="命中率" prop="fgPCT" sortable></el-table-column>
        <el-table-column
          label="3分出手"
          prop="threesAttemptedPG"
          sortable
        ></el-table-column>
        <el-table-column
          label="3分命中率"
          prop="threesMadePG"
          sortable
        ></el-table-column>
        <el-table-column
          label="罚球次数"
          prop="ftAttemptedPG"
          sortable
        ></el-table-column>
        <el-table-column
          label="罚球命中率"
          prop="ftPCT"
          sortable
        ></el-table-column>
        <el-table-column
          label="篮板"
          prop="reboundsPG"
          sortable
        ></el-table-column>
        <el-table-column
          label="前场篮板"
          prop="offensiveReboundsPG"
          sortable
        ></el-table-column>
        <el-table-column
          label="后场篮板"
          prop="defensiveReboundsPG"
          sortable
        ></el-table-column>
        <el-table-column
          label="助攻"
          prop="assistsPG"
          sortable
        ></el-table-column>
        <el-table-column
          label="抢断"
          prop="stealsPG"
          sortable
        ></el-table-column>
        <el-table-column
          label="盖帽"
          prop="blocksPG"
          sortable
        ></el-table-column>
        <el-table-column
          label="失误"
          prop="turnoversPG"
          sortable
        ></el-table-column>
        <el-table-column label="犯规" prop="foulsPG" sortable></el-table-column>
        <el-table-column label="场次" prop="games" sortable></el-table-column>
      </el-table>
    </el-card>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryinfoT.pageNum"
      :page-sizes="[10,20,50]"
      :page-size="queryinfoT.pageSize"
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
    this.getTeamDataList();
  },
  data() {
    return {
      //权限列表
      PlayerDataList: [],
      total: 0,
      //获取球员成绩的参数
      queryinfoT: {
        seasonId: 2020,
        seasonType: 1,
        pageNum: 1,
        pageSize: 10, //默认50个 一页
        index: "pointsPG",
        position: "",
        name:"",
      },
      //选择栏数据
      valueY: [2020],
      valueM: [1],
      valueP: [],
      optionsY: [
        {
          value: 2020,
          label: "2020-2021赛季",
        },
        {
          value: 2019,
          label: "2019-2020赛季",
        },
        {
          value: 2018,
          label: "2018-2019赛季",
        },
      ],
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
      //   optionsP: [
      //     {
      //       value: "前锋",
      //       label: "前锋",
      //     },
      //     {
      //       value: "中锋",
      //       label: "中锋",
      //     },
      //     {
      //       value: "后卫",
      //       label: "后卫",
      //     },
      //   ],
    };
  },
  methods: {
    //获取球员成绩列表
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
      this.queryinfoT.seasonId = valueY[0];
      this.getTeamDataList();
    },
    handleChangeM(valueM) {
      //   console.log(valueM);
      this.queryinfoT.seasonType = valueM[0];
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
.searchTeam{
  margin-left: 20px;
}
</style>
