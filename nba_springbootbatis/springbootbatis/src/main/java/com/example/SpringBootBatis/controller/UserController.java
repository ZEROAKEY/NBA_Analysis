package com.example.SpringBootBatis.controller;

import com.example.SpringBootBatis.bean.Meta;
import com.example.SpringBootBatis.bean.ResponseResult;
import com.example.SpringBootBatis.bean.dataBase.UserBean;
import com.example.SpringBootBatis.enums.msgEnum;
import com.example.SpringBootBatis.service.UserService;
import com.example.SpringBootBatis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired(required = false)
    private UserService userService;

    @GetMapping("/search")
    /**
     * - 请求路径：users
     * - 请求方法：get
     * - 请求参数
     *
     * | 参数名   | 参数说明     | 备注     |
     * | -------- | ------------ | -------- |
     * | query    | 查询参数     | 可以为空 |
     * | pageNum  | 当前页码     | 不能为空 |
     * | pageSize | 每页显示条数 | 不能为空 |
     * - 响应参数
     *
     * | 参数名    | 参数说明     | 备注 |
     * | --------- | ------------ | ---- |
     * | totalPage | 总记录数     |      |
     * | pageNum   | 当前页码     |      |
     * | users     | 用户数据集合 |      |
     */
    //@RequestParam用来处理 Content-Type 为 application/x-www-form-urlencoded 编码的内容，Content-Type默认为该属性。@RequestParam也可用于其它类型的请求，例如：POST、DELETE等请求。
    public ResponseResult getUsers(@RequestParam("query") String query,
                                @RequestParam("pageNum") String pageNum,
                                @RequestParam("pageSize") String pageSize) throws Exception {
        String username= query;
        List<UserBean> users=userService.queryUsersByName(username);
        return ResultUtil.querySuccessPage(users,Integer.parseInt(pageNum),Integer.parseInt(pageSize));
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Integer id) throws Exception {
        int i=userService.dropUser(String.valueOf(id));
        ResponseResult responseResult=new ResponseResult();
        responseResult.setData(userService.queryUserById(String.valueOf(id)));
        if(i==0){
            responseResult=ResultUtil.error(msgEnum.FALSE);
        }else{
            responseResult.setMeta(new Meta(msgEnum.OK));
        }
        return responseResult;
    }
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable("id") Integer id) throws Exception {
        UserBean userBean=userService.queryUserById(String.valueOf(id));
        ResponseResult responseResult=new ResponseResult();
        if(userBean==null){
            responseResult=ResultUtil.error(msgEnum.FALSE);
        }else{
            responseResult.setData(userBean);
            responseResult.setMeta(new Meta(msgEnum.OK));
        }
        return responseResult;
    }

    @PutMapping("/{id}")
    public ResponseResult update(@RequestBody Map<String,Object> LoginForm,
                                 @PathVariable(required = false,value = "id") Integer id) throws Exception {
        UserBean userBean1=new UserBean();
        userBean1.setMobile((String) LoginForm.get("mobile"));
        userBean1.setEmail((String) LoginForm.get("email"));
        System.out.println(id);
        userBean1.setId(String.valueOf(id));
        int i=userService.modifyUser(userBean1);
        ResponseResult responseResult=new ResponseResult();
        if(i==0){
            responseResult=ResultUtil.error(msgEnum.FALSE);
        }else{
            //在ResultUtil中的方法中的方法打包成规定格式的回应结果
            responseResult.setMeta(new Meta(msgEnum.OK));
        }
        return responseResult;

    }
}
