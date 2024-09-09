package com.example.SpringBootBatis.util;

import com.example.SpringBootBatis.bean.SecondaryData.FullData;
import com.example.SpringBootBatis.bean.SecondaryData.LoginData;
import com.example.SpringBootBatis.bean.Meta;
import com.example.SpringBootBatis.bean.ResponseResult;
import com.example.SpringBootBatis.bean.SecondaryData.PageData;
import com.example.SpringBootBatis.bean.dataBase.UserBean;
import com.example.SpringBootBatis.bean.dataBase.WinRateBean;
import com.example.SpringBootBatis.enums.msgEnum;

import java.util.List;

/**
 * 仅要求返回单个userBean登录的结果返回
 */
public class ResultUtil {
    public static ResponseResult loginSuccess(UserBean userBean){
        ResponseResult responseResult =new ResponseResult();
        responseResult.setData(new LoginData(userBean));//loginData自定义方法生成token
        responseResult.setMeta(new Meta(msgEnum.OK));
        return responseResult;
    }
    public static ResponseResult success(){

        return loginSuccess(null);
    }
    public static ResponseResult error(msgEnum msgEnum){
        ResponseResult responseResult =new ResponseResult();
        responseResult.setMeta(new Meta(msgEnum));
        System.out.println(responseResult.getMeta());
        return responseResult;
    }

    /**
     * 按页数返回查询结果
     * @param PageData
     * @param pageNum
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T>ResponseResult querySuccessPage(List<T> PageData, int pageNum, int pageSize){
        int totalPage=PageData.size()% pageSize ==0?
                (PageData.size()/ pageSize):
                (PageData.size()/ pageSize +1);

        List<T> usersSecond=
                PageData.subList(pageSize *(pageNum -1),
                pageNum ==totalPage?
                        PageData.size(): pageSize * pageNum);//计算需要的数据量 1-3=1和2
        ResponseResult responseResult =new ResponseResult();
        responseResult.setData(new PageData<T>(usersSecond,pageNum,totalPage,PageData.size()));
        responseResult.setMeta(new Meta(msgEnum.OK));
        return responseResult;
    }

    /**
     * 不需要分页的查询（如按队查询的选手信息
     * @param FullData
     * @param <T>
     * @return
     */
    public static <T>ResponseResult querySuccessFull(T FullData){
        ResponseResult responseResult =new ResponseResult();
        responseResult.setData(FullData);
        responseResult.setMeta(new Meta(msgEnum.OK));
        return responseResult;
    }

}
