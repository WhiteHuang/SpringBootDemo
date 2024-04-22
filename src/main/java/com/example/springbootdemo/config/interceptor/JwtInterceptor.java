package com.example.springbootdemo.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.springbootdemo.common.Constants;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.exception.ServiceException;
import com.example.springbootdemo.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * token 拦截器，简单写法
 *
 */
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"没有token，请重新登录");
        }
        String userId;
        try {
            userId= JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException e){
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
        }

        User user = userService.getById(userId);
        if(user==null){
            throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try{
            jwtVerifier.verify(token);
        }catch (JWTVerificationException jwtVerificationException){
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);

    }
}
