<template>
  <el-container class="home_container">
    <!-- 头部区域 -->
    <el-header>
      <div>
        <img class="home_logo" src="../assets/NBA.jpg" alt="" />
        <span>{{ $t('message.welcome') }}</span>
      </div>
      <div>
        <el-dropdown @command="changeLanguage" style="margin-right: 10px;">
    <el-button type="info">
      <i class="el-icon-s-tools" style="margin-right: 10px;"></i>
      {{ $t('message.language') }}
      <i class="el-icon-arrow-down el-icon--right"></i>
    </el-button>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item 
        :class="{'selected-language': $i18n.locale === 'en'}"
        icon="el-icon-flag" 
        command="en">
        English
        <!-- 仅显示勾选图标，不影响hover效果 -->
        <i v-if="$i18n.locale === 'en'" class="el-icon-check selected-language-icon"></i>
      </el-dropdown-item>
      <el-dropdown-item 
        :class="{'selected-language': $i18n.locale === 'zh'}"
        icon="el-icon-flag" 
        command="zh">
        中文
        <!-- 仅显示勾选图标，不影响hover效果 -->
        <i v-if="$i18n.locale === 'zh'" class="el-icon-check selected-language-icon"></i>
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
      <el-button type="info" @click="logout">{{ $t('message.logout') }}</el-button>
    </div>
    </el-header>
    <!-- 页面主体区域 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="isCollapse ? '64px' : menuWidth">
        <!-- 侧边栏菜单区域 -->
        <!-- 折叠框 -->
        <!-- <div class="toggle-button" @click="toggleClose">点击折叠</div> -->
        <div class="toggle-button" @click="toggleClose">
          <i :class="isCollapse ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"></i>
        </div>
        <el-menu background-color="#545c64" text-color="#fff" active-text-color="#409EFF" :unique-opened="true"
          :collapse="isCollapse" :collapse-transition="false" :router="true" :default-active="activePath">
          <!-- 一级菜单 -->
          <el-submenu :index="'' + item.id" v-for="item of menulist.slice(0, 3)" :key="item.id">
            <!-- 一级菜单的模板区 -->
            <template slot="title">
              <!-- 图标 -->
              <i :class="iconsObj[item.id]"></i>
              <!-- 文本 -->
              <span>{{ item.authName }}</span>
            </template>
            <!-- 二级菜单 -->
            <el-menu-item :index="'/' + subItem.path" @click="setActivePath('/' + subItem.path)"
              v-for="subItem in item.children" :key="subItem.id">
              <template slot="title">
                <!-- 图标 -->
                <i class="el-icon-menu"></i>
                <!-- 文本 -->
                <span>{{ subItem.authName }}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <!-- 右侧主体区域 -->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      //左侧菜单数据
      menulist: [],
      isCollapse: false,//默认侧边栏不展开
      iconsObj: {
        '100': 'el-icon-user',
        '103': 'el-icon-news',
        '101': 'el-icon-goods',
        '102': 'el-icon-s-claim',
        '145': 'el-icon-s-data'
      },
      activePath: '',
      menuWidth: '200px', // 默认菜单宽度
    }
  },
  created() {//生命周期函数，页面刚一加载的时候就要立即获取左侧菜单
    this.getMenuList();
    this.activePath = window.sessionStorage.getItem('activePath');
    this.adjustMenuWidth()
  },
  watch: {
    // 监听 vue-i18n 的 locale 变化，重新渲染菜单
    '$i18n.locale'(newLocale) {
      this.getMenuList(); // 语言切换时重新获取菜单数据并渲染
      this.adjustMenuWidth(); // 调整菜单宽度
    },
  },
  methods: {
    adjustMenuWidth() {
      if (this.$i18n.locale === 'en') {
        this.menuWidth = '220px'; // 英文时增加宽度
      } else {
        this.menuWidth = '200px'; // 中文时默认宽度
      }
    },
    logout() {
      window.sessionStorage.clear();
      this.$router.push("/login");
    },
    //获取所有菜单
    async getMenuList() {
    const { data: res } = await this.$http.get('menus');
    if (res.meta.status !== 200) {
      return this.$message.error(res.meta.msg);
    }

    // 遍历菜单数据，进行国际化处理
    this.menulist = res.data.map(item => {
      return {
        ...item,
        authName: this.$t(`message.${this.getMenuKey(item.authName)}`), // 根据authName获取翻译
        children: item.children.map(subItem => {
          return {
            ...subItem,
            authName: this.$t(`message.${this.getMenuKey(subItem.authName)}`) // 翻译子菜单
          };
        })
      };
    });
  },

  // 用于将 authName 转换为国际化键值
  getMenuKey(authName) {
    const menuMap = {
      '用户管理': 'user_management',
      '用户列表': 'user_list',
      '球队管理': 'team_management',
      '球队详情': 'team_details',
      '数据统计': 'data_statistics',
      '球员数据': 'player_data',
      '球队数据': 'team_data',
      '比赛日程': 'schedule',
    };
    return menuMap[authName] || authName; // 返回对应的键，如果没有匹配到，直接返回原authName
  },
    toggleClose() {
      this.isCollapse = !this.isCollapse;
    },
    //保存连接的激活状态
    setActivePath(activePath) {
      window.sessionStorage.setItem('activePath', activePath);
      this.activePath = activePath;
    },
    // 切换语言
    changeLanguage(lang) {
      this.$i18n.locale = lang;
      localStorage.setItem('language', lang); // 持久化语言设置
    },
  },
};
</script>

<style lang="less" scoped>
.home_container {
  height: 100%;
}

.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: white;
  font-size: 20px;

  div {
    display: flex;
    align-items: center;

    span {
      margin-left: 10px;
    }
  }

  .home_logo {
    height: 50px;
    width: 50px;
  }
}

.el-aside {
  background-color: #333744;

  .el-menu {
    border-right: none;
  }
}

.el-main {
  background-color: #eaedf1;
}

.toggle-button {
  background-color: #6b7783;
  color: white;
  font-size: 10px;
  line-height: 24px;
  text-align: center; //文本居中

}

.selected-language-icon {
  margin: 10px 0 0 10px;
  float: right;
  color: #409EFF; /* 选中图标颜色与主题颜色保持一致 */
}
.selected-language{
  border-color: #c6e2ff;
  background-color: #ecf5ff;
}
</style>