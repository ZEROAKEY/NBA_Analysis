<template>
  <div>
    <!-- 面包屑区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcome' }">{{ $t('message.home') }}</el-breadcrumb-item>
      <el-breadcrumb-item>{{ $t('message.data_statistics') }}</el-breadcrumb-item>
      <el-breadcrumb-item>{{ $t('message.schedule') }}</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="game-schedule">
      <el-row :gutter="20">
        <!-- 左侧表格 -->
        <el-col :span="12">
          <div class="title-container past-title">
            <i class="el-icon-data-line"></i>
            <h3>{{ $t('message.past_games') }}</h3>
          </div>
          <table>
            <thead>
              <tr class="past_list">
                <th>{{ $t('message.date') }}</th>
                <th>{{ $t('message.left_team') }}</th>
                <th>{{ $t('message.score') }}</th>
                <th>{{ $t('message.right_team') }}</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(game, index) in pastGames" :key="index">
                <td>{{ game.date }}</td>
                <td>{{ $t(`message.${game.leftName}`) }}</td>
                <td>{{ game.leftGoal + ' - ' + game.rightGoal }}</td>
                <td>{{ $t(`message.${game.rightName}`) }}</td>
              </tr>
            </tbody>
          </table>
        </el-col>

        <!-- 右侧表格 -->
        <el-col :span="12">
          <div class="title-container future-title">
            <i class="el-icon-sunny"></i>
            <h3>{{ $t('message.future_games') }}</h3>
          </div>
          <table>
            <thead>
              <tr class="future_list">
                <th>{{ $t('message.date') }}</th>
                <th>{{ $t('message.left_team') }}</th>
                <th>{{ $t('message.score_prediction') }}</th>
                <th>{{ $t('message.right_team') }}</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(game, index) in nextGames" :key="index">
                <td>{{ game.date }}</td>
                <td>{{ $t(`message.${game.leftName}`) }}</td>
                <td>{{ game.leftGoal + ' - ' + game.rightGoal }}</td>
                <td>{{ $t(`message.${game.rightName}`) }}</td>
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
      pastGames: [],
      nextGames: []
    }
  },
  methods: {
    //获取比赛日程列表
    async getGameScheduleList() {
      const teamNameMap = {
        '亚特兰大老鹰': 'atlanta_hawks',
        '波士顿凯尔特人': 'boston_celtics',
        '布鲁克林篮网': 'brooklyn_nets',
        '夏洛特黄蜂': 'charlotte_hornets',
        '芝加哥公牛': 'chicago_bulls',
        '克利夫兰骑士': 'cleveland_cavaliers',
        '达拉斯独行侠': 'dallas_mavericks',
        '丹佛掘金': 'denver_nuggets',
        '底特律活塞': 'detroit_pistons',
        '金州勇士': 'golden_state_warriors',
        '休斯顿火箭': 'houston_rockets',
        '印第安纳步行者': 'indiana_pacers',
        '洛杉矶快船': 'los_angeles_clippers',
        '洛杉矶湖人': 'los_angeles_lakers',
        '孟菲斯灰熊': 'memphis_grizzlies',
        '迈阿密热火': 'miami_heat',
        '密尔沃基雄鹿': 'milwaukee_bucks',
        '明尼苏达森林狼': 'minnesota_timberwolves',
        '新奥尔良鹈鹕': 'new_orleans_pelicans',
        '纽约尼克斯': 'new_york_knicks',
        '俄克拉荷马城雷霆': 'oklahoma_city_thunder',
        '奥兰多魔术': 'orlando_magic',
        '费城76人': 'philadelphia_76ers',
        '菲尼克斯太阳': 'phoenix_suns',
        '波特兰开拓者': 'portland_trail_blazers',
        '萨克拉门托国王': 'sacramento_kings',
        '圣安东尼奥马刺': 'san_antonio_spurs',
        '多伦多猛龙': 'toronto_raptors',
        '犹他爵士': 'utah_jazz',
        '华盛顿奇才': 'washington_wizards'
      };

      const { data: res } = await this.$http.get("search/last10");
      if (res.meta.status !== 200) {
        return this.$message.error("获取过去十场比赛日程表失败");
      } else {
        this.pastGames = res.data.map(game => {
          return {
            ...game,
            leftName: teamNameMap[game.leftName],
            rightName: teamNameMap[game.rightName]
          };
        });
        console.log(this.pastGames)
        this.$message.success("获取过去十场比赛日程列表成功");
      }

      const { data: res_next } = await this.$http.get("search/next10");
      if (res_next.meta.status !== 200) {
        return this.$message.error("获取未来十场比赛日程表失败");
      } else {
        this.nextGames = res_next.data.map(game => {
          return {
            ...game,
            leftName: teamNameMap[game.leftName],
            rightName: teamNameMap[game.rightName]
          };
        });
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
  width: 25%;
  padding: 12px 15px;
  text-align: center;
}

th {
  background-color: #f8f8f8;
}

td {
  border-bottom: 1px solid #ddd;
}

.past_list>th {
  background-color: #EC6A86;
}

.future_list>th {
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
