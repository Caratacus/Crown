package org.crown.common.kit;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.crown.common.api.ApiAssert;
import org.crown.common.api.model.JWTToken;
import org.crown.common.emuns.ErrorCodeEnum;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * JWT token 生成工具类
 * </p>
 *
 * @author Caratacus
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public abstract class JWTTokenUtils {

    public static final String _ID = "id";
    public static final String _USERNAME = "un";
    private static final String SECRET = "1s6U65P4bAay14bMDgHWgtqaTHNTMZPZNMDJu3k";
    private static final long expire = 7 * 24 * 60 * 60 * 1000;

    /**
     * 生成token
     *
     * @param uid
     * @param loginName
     * @return
     */
    public static String generate(Integer uid, String loginName) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(_ID, uid);
        claims.put(_USERNAME, loginName);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 解析Claims
     *
     * @param token
     * @return
     */
    public static Claims getClaim(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug("validate is token error ", e);
        }
        return claims;
    }

    /**
     * 解析token为JWTToken对象
     *
     * @param token
     * @return
     * @see JWTToken
     */
    public static JWTToken parser(String token) {
        Claims claims = getClaim(token);
        ApiAssert.notNull(ErrorCodeEnum.UNAUTHORIZED, claims);
        JWTToken jwtToken = new JWTToken();
        jwtToken.setId(TypeUtils.castToInt(claims.get(_ID)));
        jwtToken.setUsername(claims.get(_USERNAME, String.class));
        return jwtToken;
    }

}
