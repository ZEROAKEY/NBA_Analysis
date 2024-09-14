<template>
    <div class="login_container">
        <div class="login_box">
            <!-- 头像区域 -->
            <div class="avatar_box">
                <img src="../assets/NBA.jpg" alt="">
            </div>
            <!-- 登录表单区域 -->
            <el-form ref="loginFormRef" :model="loginForm" :rules="loginFormRules" label-width="0px" class="login_form">
                <!-- 用户名 -->
                <el-form-item prop="username">
                    <el-input v-model="loginForm.username" prefix-icon="el-icon-user"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" prefix-icon="el-icon-lock" type="password"></el-input>
                </el-form-item>
                <!-- 按钮区域 -->
                <el-form-item class="btns">
                    <el-button @click="login" type="primary">{{ $t('message.login') }}</el-button>
                    <el-button @click="resetLoginForm" type="info">{{ $t('message.reset') }}</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            //这是登录表单的数据绑定对象
            loginForm: {
                username: 'ldy',
                password: '123456'
            },
            //这是表单的验证规则对象
            loginFormRules: {
                //验证用户名是否合法
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },//必填，提示文本，触发条件：鼠标对文本框失去焦点
                    { min: 3, max: 10, message: '长度在 3 到 10个字符', trigger: 'blur' }
                ],

                //验证密码
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 6, max: 15, message: '长度在6到15个字符', trigger: 'blur' }
                ]
            }
        }
    },
    created() {
        // 在页面创建时获取用户的IP及位置
        this.detectUserLocation();
    },
    methods: {
        // 获取用户IP地址和地理位置信息
        async detectUserLocation() {
            try {
                // 使用第三方API来获取地理位置，返回用户的IP和位置信息
                const response = await axios.get('https://ipapi.co/json/');
                const locationData = response.data;

                // 获取用户所在的国家代码
                const countryCode = locationData.country_code;

                // 根据国家代码匹配语言
                if (countryCode === 'CN') {
                    this.$i18n.locale = 'zh';  // 设置中文
                } else {
                    this.$i18n.locale = 'en';  // 默认英文
                }

                console.log(`Detected country: ${countryCode}, setting locale to: ${this.$i18n.locale}`);
            } catch (error) {
                console.error('Error detecting user location:', error);
            }
        },
        //重置
        resetLoginForm() {
            this.$refs.loginFormRef.resetFields();
        },
        login() {
            this.$refs.loginFormRef.validate(async valid => {
                if (!valid) return;
                const { data: res } = await this.$http.post("login", this.loginForm);
                if (res.meta.status !== 200) return this.$message.error('登录失败！');;
                this.$message.success('登录成功！')
                // 1．将登录成功之后的 token，保存到客户端的sessionStorage中 （sessionStorage绘画期间的存储机制，localStorage持久化的存储机制
                // 1.1项目中出了登录之外的其他API接口，必须在登录之后才能访问
                //1.2 token 只应在当前网站打开期间生效，所以将 token保存在 sessionStorage 中．
                window.sessionStorage.setItem('token', res.data.token);
                //2.通过编程式导航跳转到后台主页，路由地址是/home
                this.$router.push("/home");
            })
        }
    }

}
</script>

<style lang="less" scoped>
.login_container {
    background-color: #2b4b6b;
    height: 100%;
}

.login_box {
    width: 450px;
    height: 300px;
    background-color: white;
    border-radius: 3px; //圆角边框
    position: absolute;
    left: 50%;
    top: 50%;
    transform: (translate(-50%, -50%)); //横纵轴各移动-50%

}

.avatar_box {
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    border-radius: 50%; //圆形边框
    padding: 5px;
    box-shadow: 0 0 10px #ddd; //阴影向外扩散10px
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;

    img {
        height: 100%;
        width: 100%;
        border-radius: 50%;
    }
}

.login_form {
    position: absolute;
    bottom: 0; //把图像的底边缘设置在其包含元素底边缘之上 0 像素高的位置：
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
}

.btns {
    display: flex;
    justify-content: center;
}
</style>