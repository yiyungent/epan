package com.stu.yun.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.stu.yun.model.UserInfo;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    public static String createToken(UserInfo user, String secret) {
        //登录成功后生成JWT
        //JWT的header部分,该map可以是空的,因为有默认值{"alg":HS256,"typ":"JWT"}
        Map<String, Object> map = new HashMap<>();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, 30);
        String token = JWT.create()
                .withHeader(map)//添加头部
                .withClaim("userid", user.getId())//添加payload
                .withClaim("username", user.getUserName())
//                .withClaim("email",userDB.getEmail())
                .withExpiresAt(instance.getTime())//设置过期时间
                .sign(Algorithm.HMAC256(secret));//设置签名 密钥

        return token;
    }

    public static UserInfo verify(String token, String secret) {
        UserInfo user = null;
        //创建验证对象,这里使用的加密算法和密钥必须与生成TOKEN时的相同否则无法验证
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            //验证JWT
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            //获取JWT中的数据,注意数据类型一定要与添加进去的数据类型一致,否则取不到数据
            Integer userId = decodedJWT.getClaim("userid").asInt();
            String userName = decodedJWT.getClaim("username").asString();
            Date expire = decodedJWT.getExpiresAt();

            user = new UserInfo();
            user.setId(userId);
            user.setUserName(userName);
        } catch (Exception ex) {
            user = null;
        }

        return user;
    }

}
