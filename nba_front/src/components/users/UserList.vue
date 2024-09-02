<template>
  <div>
    <!-- 面包屑区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区域 -->
    <el-card class="box-card">
      <!-- 搜索与输入框 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-input
            placeholder="请输入内容"
            v-model="queryInfo.query"
            clearable
            @clear="getUserList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getUserList"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true"
            >添加用户</el-button
          >
        </el-col>
      </el-row>
      <!-- 表格区域 -->
      <el-table :data="userlist" border stripe>
        <el-table-column type="index" :index="indexMethod"></el-table-column>
        <el-table-column label="姓名" prop="username"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="电话" prop="mobile"></el-table-column>
        <!-- <el-table-column label="角色" prop="role_name"></el-table-column>
        <el-table-column label="状态" prop="mg_state"> -->
          <!-- <template slot-scope="scope">
            <el-switch
              v-model="scope.row.mg_state"
              @change="userStateChanged(scope.row)"
            >
            </el-switch>
          </template>
        </el-table-column> -->
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <!-- 修改按钮 -->
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="getUserFormById(scope.row.id)"
            ></el-button>
            <!-- 删除角色按钮 -->
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="deletUser(scope.row.id)"
            ></el-button>
            <!-- 分配角色按钮
            <el-tooltip
              effect="dark"
              content="分配角色"
              placement="top"
              :enterable="false"
            > -->
              <!-- <el-button
                @click="setRole(scope.row)"
                type="warning"
                icon="el-icon-setting"
                size="mini"
              ></el-button>
            </el-tooltip> -->
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum"
        :page-sizes="[1, 2, 5, 10]"
        :page-size="queryInfo.pageSize"
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
    </el-card>

    <!-- 添加用户的对话框 -->
    <el-dialog
      title="提示"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="closeDiaglog"
    >
      <!-- 内容主题区域 -->
      <!-- 添加用户表单 -->
      <el-form
        :model="userForm"
        :rules="userRules"
        ref="addFormRef"
        label-width="70px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="userForm.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="mobile">
          <el-input v-model="userForm.mobile"></el-input>
        </el-form-item>
      </el-form>

      <!-- 底部区域,点击取消或者确定的时候隐藏对话框 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改用户的对话框 -->
    <el-dialog title="提示" :visible.sync="resetDialogVisible" width="50%">
      <!-- 内容主题区域 -->
      <!-- 添加用户表单 -->
      <el-form
        :model="resetuserForm"
        :rules="resetuserRules"
        ref="resetFormRef"
        label-width="70px"
      >
        <el-form-item label="用户名">
          <el-input v-model="resetuserForm.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="resetuserForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="mobile">
          <el-input v-model="resetuserForm.mobile"></el-input>
        </el-form-item>
      </el-form>

      <!-- 底部区域,点击取消或者确定的时候隐藏对话框 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="resetUser">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 分配角色的对话框-->
    <el-dialog
      @close="setRoleClose"
      title="分配角色"
      :visible.sync="setRoledialogVisible"
      width="50%"
    >
      <div>
        <p>当前用户：{{ userInfo.username }}</p>
        <p>当前角色：{{ userInfo.role_name }}</p>
        <p>
          分配新角色：
          <el-select v-model="selectedRoleId" placeholder="请选择">
            <el-option
              v-for="item in rolesList"
              :key="item.id"
              :label="item.roleName"
              :value="item.id"
            ></el-option>
          </el-select>
        </p>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="setRoledialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRole">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>


<script>
export default {
  data() {
    // 验证邮箱的校验规则
    var checkEmail = (userRules, value, cb) => {
      //验证邮箱的正则表达式
      const regEmail = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;

      if (regEmail.test(value)) {
        return cb();
      }

      cb(new Error("请输入合法的邮箱"));
    };
    // 验证手机号的规则
    var checkMobile = (userRules, value, cb) => {
      //验证手机号的正则表达式
      const regMobile = /^[1][3,4,5,7,8,9][0-9]{9}$/;

      if (regMobile.test(value)) {
        return cb();
      }

      cb(new Error("请输入合法的电话"));
    };
    return {
      //控制分配角色的对话框的显示
      setRoledialogVisible: false,
      //需要被分配角色的用户信息
      userInfo: {},
      //已选中的角色id值
      selectedRoleId: "",
      //所有角色的数据列表
      rolesList: [],
      //控制修改用户的对话框的显示
      resetDialogVisible: false,
      resetuserForm: {
        id:"",
        username: "",
        rid: "",
        email: "",
        mobile: "",
      },
      alteruserForm: {
        email: "",
        mobile: "",
      },
      //获取用户列表的参数
      queryInfo: {
        query: "",
        pageNum: 1, //默认请求第一页数据
        pageSize: 2, //默认每页显示两条数据
      },
      userlist: [],
      total: 0,
      //添加用户对话框的显示与隐藏
      addDialogVisible: false,
      userForm: {
        username: "",
        password: "",
        rid:"1",
        email: "",
        mobile: "",
      },
      userRules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 15,
            message: "长度在 6 到 15 个字符",
            trigger: "blur",
          },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { validator: checkEmail, trigger: "blur" },
        ],
        mobile: [
          { required: true, message: "请输入电话", trigger: "blur" },
          { validator: checkMobile, trigger: "blur" },
        ],
      },
      resetuserRules: {
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { validator: checkEmail, trigger: "blur" },
        ],
        mobile: [
          { required: true, message: "请输入电话", trigger: "blur" },
          { validator: checkMobile, trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getUserList();
  },
  methods: {
    //根据id删除用户
    async deletUser(id) {
      const confirmInfo = await this.$confirm(
        "此操作将永久删除该用户, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).catch((err) => err);

      //确认删除返回字符串confirm,取消删除返回字符串 cancel
      if (confirmInfo !== "confirm") return this.$message.info("已取消删除");
      else {
        //提交删除请求
        const { data: res } = await this.$http.delete("users/" + id);

        if (res.meta.status !== 200)
          return this.$message.error("提交删除请求失败");
        else {
          this.getUserList();
          this.$message.success("已成功删除");
        }
      }
    },
    //根据id获取对应用户信息
    async getUserFormById(id) {
      this.resetDialogVisible = true;
      const { data: res } = await this.$http.get("users/" + id);
      if (res.meta.status != 200)
        return this.$message.error("获取id对应用户失败");
      else {
        this.resetuserForm = res.data;
        this.alteruserForm= res.data;
      }
    },
    //预验证修改表单，发起修改请求
    resetUser() {
      this.$refs.resetFormRef.validate(async (valid) => {
        if (!valid) return this.$message.error("修改表单不规范");
        else {
          //发起修改用户的请求
          const { data: res } = await this.$http.put(
            "users/" + this.resetuserForm.id,this.alteruserForm
          );
          // console.log(res);
          if (res.meta.status != 200)
            return this.$message.error("提交修改表单失败");
          else {
            this.$message.success("修改表单成功");
            this.resetDialogVisible = false;
            this.getUserList();
          }
        }
      });
    },
    //监听添加用户对话关闭事件，重置表单
    closeDiaglog() {
      this.$refs.addFormRef.resetFields();
    },
    addUser() {
      this.$refs.addFormRef.validate(async (valid) => {
        if (!valid) return this.$message.error("填写信息错误或不合规则");
        //可以发起添加用户的网络请求
        const { data: res } = await this.$http.post("login/register", this.userForm);
        console.log(res);
        if (res.meta.status !== 200) {
          this.$message.error("添加用户失败");
        } else this.$message.success("添加用户成功");

        //隐藏用户的对话框
        this.addDialogVisible = false;
        //重新获取用户数据列表
        this.getUserList();
      });
    },

    async getUserList() {
      const { data: res } = await this.$http.get(`users/search`, {
        params: this.queryInfo,
      });
      if (res.meta.status !== 200) {
        return this.$message.error("获取用户列表失败！");
      }
      this.userlist = res.data.pageData;
      this.total = res.data.totalNum;
      // console.log(res.data.totalPage);
    },
    // 监听pageSize改变的事件
    handleSizeChange(newSize) {
      // console.log(newSize);
      this.queryInfo.pageSize = newSize;
      //重新向网页请求数据
      this.getUserList();
    },
    // 监听页码值改变的事件
    handleCurrentChange(newPage) {
      // console.log(newPage);
      this.queryInfo.pageNum = newPage;
      this.getUserList();
    },
    // 监听switch开关状态的改变
    async userStateChanged(userinfo) {
      //为了方便拼接参数把单引号修改成反引号 ``
      const { data: res } = await this.$http.put(
        `users/${userinfo.id}/state/${userinfo.mg_state}`
      );
      if (res.meta.status != 200) {
        //操作失败提示用户,并取反状态
        userinfo.mg_state = !userinfo.mg_state;
        return this.$message.error("更新用户状态失败");
      }
      this.$message.success("更新用户状态成功！");
    },
    //分配用户权限
    async setRole(userinfo) {
      this.userInfo = userinfo;
      //在展示对话框之前获取所有角色的列表
      const { data: res } = await this.$http.get("roles");
      if (res.meta.status != 200) {
        return this.$message.error("获取用户角色失败！");
      }
      this.$message.success("获取用户角色成功！");
      this.rolesList = res.data;
      this.setRoledialogVisible = true;
    },
    //点击按钮分配角色
    async saveRole() {
      if (!this.selectedRoleId) {
        return this.$message.error("请选择要分配的角色！");
      }
      // console.log(this.userInfo.id);
      const { data: res } = await this.$http.put(
        `users/${this.userInfo.id}/role`,
        {
          rid: this.selectedRoleId,
        }
      );
      if (res.meta.status !== 200) {
        return this.$message.error(res.meta.msg);
      }
      this.$message.success("更新用户角色成功！");
      this.setRoledialogVisible = false;

      this.getUserList();
    },
    setRoleClose() {
      this.selectedRoleId = "";
      this.userInfo = {};
    },
    //序列自增且不受分页影响，(当前的页码数-1)x当前的每一页中的总条数+1(因为index从0开始计算)
    indexMethod(index) {
        return (this.queryInfo.pageNum-1)*(this.queryInfo.pageSize)+1+index;
  },
  },
};
</script>

<style lang="less" scoped>
.box-card {
  width: 100%x;
}
</style>
