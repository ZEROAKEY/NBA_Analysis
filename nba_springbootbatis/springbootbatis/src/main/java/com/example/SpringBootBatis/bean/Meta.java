package com.example.SpringBootBatis.bean;

import com.example.SpringBootBatis.enums.msgEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Meta {
    private int status;
    private String msg;
    public Meta(msgEnum msgEnum){
        this.status=msgEnum.getStatus();
        this.msg=msgEnum.getMsg();
    }

    /**
     *
     * @param msgEnum 自定义信息
     * @param msg
     */
    public Meta(msgEnum msgEnum, String msg){
        this.status=msgEnum.getStatus();
        this.msg=msg;
    }
}
