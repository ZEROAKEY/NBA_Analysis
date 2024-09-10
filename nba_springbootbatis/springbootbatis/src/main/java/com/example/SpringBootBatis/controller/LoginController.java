package com.example.SpringBootBatis.controller;

import com.example.SpringBootBatis.bean.Meta;
import com.example.SpringBootBatis.bean.ResponseResult;
import com.example.SpringBootBatis.bean.SecondaryData.LoginData;
import com.example.SpringBootBatis.bean.SecondaryData.MenuData;
import com.example.SpringBootBatis.bean.dataBase.UserBean;
import com.example.SpringBootBatis.enums.msgEnum;
import com.example.SpringBootBatis.service.UserService;
import com.example.SpringBootBatis.util.JwtUtil;
import com.example.SpringBootBatis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class LoginController {
    @Autowired(required = false)
    private UserService userService;

    @PostMapping("login")
    /**
     * 发送json username和password
     * @RequestBody
     * 注解@RequestBody接收的参数是来自requestBody中，即请求体。一般用于处理非 Content-Type: application/x-www-form-urlencoded编码格式的数据，
     * 比如：application/json、application/xml等类型的数据。
     * 就application/json类型的数据而言，使用注解@RequestBody可以将body里面所有的json数据传到后端，后端再进行解析。
     * GET请求中，因为没有HttpEntity，所以@RequestBody并不适用。
     * POST请求中，通过HttpEntity传递的参数，必须要在请求头中声明数据的类型Content-Type，SpringMVC通过使用
     * HandlerAdapter 配置的HttpMessageConverters来解析HttpEntity中的数据，然后绑定到相应的bean上。
     * @RequestParam用来处理 Content-Type 为 application/x-www-form-urlencoded 编码的内容，Content-Type默认为该属性。@RequestParam也可用于其它类型的请求，例如：POST、DELETE等请求。
     *
     * 在LoginData里写了自定义方法，根据username什么什么的生成一个token再包装回来
     */
    public ResponseResult login(@RequestBody Map<String,Object> LoginForm) throws Exception {
        String username= (String) LoginForm.get("username");
        String password= (String) LoginForm.get("password");
        UserBean userBean=userService.loginIn(username,password);
        ResponseResult responseResult;
        if(userBean==null){
            responseResult=ResultUtil.error(msgEnum.FALSE);
        }else{
            //在ResultUtil中的方法中的方法打包成规定格式的回应结果
            responseResult= ResultUtil.loginSuccess(userBean);
        }
        return responseResult;
    }
    /**
     *
     private String id;
     private String username;
     private String password;
     private String rid;
     private String mobile;
     private String email;
     * @param LoginForm
     * @return
     * @throws Exception
     */
    @PostMapping("login/register")
    public ResponseResult register(@RequestBody Map<String,Object> LoginForm) throws Exception {
        UserBean userBean1=new UserBean();
            String username = (String) LoginForm.get("username");
            userBean1.setUsername(username);
        if(LoginForm.containsKey("password")){
            String password = (String) LoginForm.get("password");
            userBean1.setPassword(password);
        }
        if(LoginForm.containsKey("rid")) {
            String rid = (String) LoginForm.get("rid");
            userBean1.setRid(rid);
        }
        if(LoginForm.containsKey("mobile")) {
            String mobile = (String) LoginForm.get("mobile");
            userBean1.setMobile(mobile);
        }
        if(LoginForm.containsKey("email")) {
            String email = (String) LoginForm.get("email");
            userBean1.setEmail(email);
        }
        int i=userService.addUser(userBean1);
        ResponseResult responseResult=new ResponseResult();
        if(i==0){
            responseResult=ResultUtil.error(msgEnum.FALSE);
        }else{
            //在ResultUtil中的方法中的方法打包成规定格式的回应结果
            responseResult.setData(userService.queryUserByName(username));
            responseResult.setMeta(new Meta(msgEnum.OK));
        }
        return responseResult;


    }
    @GetMapping("/menus")
    public ResponseResult menus(){
        List<MenuData> empty = new ArrayList<>();
        List<MenuData> tmp1 = new ArrayList<>();
        List<MenuData> tmp2 = new ArrayList<>();
        List<MenuData> tmp3 = new ArrayList<>();
        List<MenuData> list = new ArrayList<>();

        MenuData menuData200=new MenuData("200", "用户列表", "users",empty);
        tmp1.add(menuData200);
        MenuData menuData100=new MenuData("100", "用户管理", "users",tmp1);
        list.add(menuData100);

        MenuData menuData201=new MenuData("201", "球队详情", "teamsInfo",empty);
        tmp2.add(menuData201);
        MenuData menuData101=new MenuData("101", "球队管理", "teamsInfo",tmp2);
        list.add(menuData101);

        MenuData menuData202=new MenuData("202", "球员数据", "players",empty);
        tmp3.add(menuData202);
        MenuData menuData203=new MenuData("203", "球队数据", "teams",empty);
        tmp3.add(menuData203);
        MenuData menuData204=new MenuData("204", "胜率预测", "winrate",empty);
        tmp3.add(menuData204);
        MenuData menuData102=new MenuData("102", "数据统计", "teams",tmp3);
        list.add(menuData102);


        ResponseResult responseResult=new ResponseResult();
        responseResult.setData(list);
        responseResult.setMeta(new Meta(msgEnum.OK,"获取菜单列表成功"));
        return responseResult;
    }


}

