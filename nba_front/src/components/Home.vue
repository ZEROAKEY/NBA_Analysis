<template>
  <el-container class="home_container">
    <!-- 头部区域 -->
    <el-header>
      <div>
        <img class="home_logo" src="../assets/NBA.jpg" alt="" />
        <span>NBA大数据分析平台</span>
      </div>
      <el-button type="info" @click="logout">退出</el-button>
    </el-header>
    <!-- 页面主体区域 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="isCollapse ? '64px':'200px'">
        <!-- 侧边栏菜单区域 -->
        <!-- 折叠框 -->
        <!-- <div class="toggle-button" @click="toggleClose">点击折叠</div> -->
        <div class="toggle-button" @click="toggleClose">
      <i :class="isCollapse ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"></i>
    </div>
        <el-menu
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#409EFF"
          :unique-opened="true"
          :collapse="isCollapse"
          :collapse-transition="false"
          :router="true"
          :default-active="activePath"
        >
          <!-- 一级菜单 -->
          <el-submenu :index="''+item.id" v-for="item of menulist.slice(0,3)" :key="item.id">
            <!-- 一级菜单的模板区 -->
            <template slot="title">
              <!-- 图标 -->
              <i :class="iconsObj[item.id]"></i>
              <!-- 文本 -->
              <span>{{item.authName}}</span>
            </template>
            <!-- 二级菜单 -->
            <el-menu-item :index="'/'+subItem.path" @click="setActivePath('/'+subItem.path)" v-for="subItem in item.children" :key="subItem.id">
              <template slot="title">
                <!-- 图标 -->
                <i class="el-icon-menu"></i>
                <!-- 文本 -->
                <span>{{subItem.authName}}</span>
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
    data(){
        return{
            //左侧菜单数据
            menulist:[],
            isCollapse:false,//默认侧边栏不展开
            iconsObj:{
                '100':'el-icon-user',
                '103':'el-icon-news',
                '101':'el-icon-goods',
                '102':'el-icon-s-claim',
                '145':'el-icon-s-data'
            },
            activePath:'',
        }
    },
    created(){//生命周期函数，页面刚一加载的时候就要立即获取左侧菜单
        this.getMenuList();
        this.activePath=window.sessionStorage.getItem('activePath');
    },
  methods: {
    logout() {
      window.sessionStorage.clear();
      this.$router.push("/login");
    },
    //获取所有菜单
    async getMenuList(){
       const{data:res} =await this.$http.get('menus');
       if(res.meta.status !== 200) return this.$message.error(res.meta.msg);
       this.menulist =res.data;
      //  console.log(res);
    },
    toggleClose(){
        this.isCollapse=!this.isCollapse;
    },
    //保存连接的激活状态
    setActivePath(activePath){
      window.sessionStorage.setItem('activePath',activePath);
      this.activePath=activePath;
    }
    
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
  .el-menu{
      border-right:none ;
  }
}

.el-main {
  background-color: #eaedf1;
}

.toggle-button{
    background-color: #6b7783;
    color:white;
    font-size: 10px;
    line-height: 24px;
    text-align: center;//文本居中

}
</style>