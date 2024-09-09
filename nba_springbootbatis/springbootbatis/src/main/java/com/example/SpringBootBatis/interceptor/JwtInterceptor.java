package com.example.SpringBootBatis.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.SpringBootBatis.bean.Meta;
import com.example.SpringBootBatis.enums.msgEnum;
import com.example.SpringBootBatis.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


//拦截器
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Meta meta =new Meta(msgEnum.FORBIDDEN);
        try {
            JwtUtil.verify(token);//验证令牌
            return true;//放行请求
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            meta.setMsg("无效签名!");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            meta.setMsg("token过期!");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            meta.setMsg("token算法不一致!");
        } catch (Exception e) {
            e.printStackTrace();
            meta.setMsg("token无效!");
        }
        //转为json  jackson 然后直接返回数据
        String json = new ObjectMapper().writeValueAsString(meta);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json); //直接返回数据
        return false;
    }
}
