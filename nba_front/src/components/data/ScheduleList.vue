<template>
  <div>
    <!-- 面包屑区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>数据统计</el-breadcrumb-item>
      <el-breadcrumb-item>比赛日程</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="game-schedule">
      <el-row :gutter="20">
        <!-- 左侧表格 -->
        <el-col :span="12">
          <div class="title-container past-title">
            <i class="el-icon-data-line"></i>
            <h3>过去十场比赛结果</h3>
          </div>
          <table>
            <thead>
              <tr class="past_list">
                <th>时间</th>
                <th>客队</th>
                <th>比分结果</th>
                <th>主队</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(game, index) in pastGames" :key="index">
                <td>{{ game.date }}</td>
                <td>{{ game.leftName }}</td>
                <td>{{ game.leftGoal + '-' + game.rightGoal  }}</td>
                <td>{{ game.rightName }}</td>
              </tr>
            </tbody>
          </table>
        </el-col>
        <!-- 右侧表格 -->
        <el-col :span="12">
          <div class="title-container future-title">
            <i class="el-icon-sunny"></i>
            <h3>未来十场比赛预测</h3>
          </div>
          <table>
            <thead>
              <tr class="future_list">
                <th>时间</th>
                <th>客队</th>
                <th>比分预测</th>
                <th>主队</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(game, index) in next" :key="index">
                <td>{{ game.date }}</td>
                <td>{{ game.leftName }}</td>
                <td>{{ game.leftGoal + '-' + game.rightGoal  }}</td>
                <td>{{ game.rightName }}</td>
              </tr>
            </tbody>
          </table>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  created() {
    this.getGameScheduleList();
  },
  data() {
    return {
      pastGames:[],
      nextGames:[]
    }
  },
  methods: {
    //获取比赛日程列表
    async getGameScheduleList() {

      const { data: res } = await this.$http.get("search/last10");
      if (res.meta.status !== 200) {
        return this.$message.error("获取过去十场比赛日程表失败");
      } else {
        this.pastGames = res.data;
        this.$message.success("获取过去十场比赛日程列表成功");
      }

      const { data: res_next } = await this.$http.get("search/next10");
      if (res_next.meta.status !== 200) {
        return this.$message.error("获取未来十场比赛日程表失败");
      } else {
        this.nextGames = res_next.data;
        this.$message.success("获取未来十场比赛日程列表成功");
      }
    },
  }
}
</script>

<style scoped>
/* 表格样式 */
.game-schedule {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
}

table {
  width: 100%;
  border: 1px solid #ddd;
  border-spacing: 0;
}

th,
td {
  padding: 12px 15px;
  text-align: center;
}

th {
  background-color: #f8f8f8;
}

td {
  border-bottom: 1px solid #ddd;
}

.past_list > th {
  background-color: #EC6A86;
}

.future_list > th {
  background-color: gold;
}

/* 斑马纹 */
tbody tr:nth-child(odd) {
  background-color: #f9f9f9;
}

/* 标题样式 */
.title-container {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  padding: 10px;
  background-color: #f3f3f3;
  border-radius: 5px;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
}

.title-container h3 {
  font-size: 20px;
  font-weight: bold;
  margin-left: 10px;
}

.past-title {
  background-color: #579EDE;
  color: white;
}

.future-title {
  background-color: #579EDE;
  color: white;
}

/* 图标样式 */
.title-container i {
  font-size: 24px;
  color: white;
}

.past-title i {
  background-color: #579EDE;
  padding: 5px;
  border-radius: 50%;
}

.future-title i {
  background-color: #579EDE;
  padding: 5px;
  border-radius: 50%;
}
</style>
