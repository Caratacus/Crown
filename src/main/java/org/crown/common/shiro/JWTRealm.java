package org.crown.common.shiro;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.crown.common.utils.JWTUtils;
import org.crown.common.utils.TypeUtils;
import org.crown.framework.emuns.ErrorCodeEnum;
import org.crown.framework.utils.ApiAssert;
import org.crown.service.IResourceService;
import org.crown.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * JWT Realm 适用于shiro
 *
 * @author Caratacus
 */
@Service
public class JWTRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private IResourceService resourceService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Integer uid = JWTUtils.getUid(principals.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roleIds = userService.getRoleIds(uid).stream().map(TypeUtils::castToString).collect(Collectors.toSet());
        simpleAuthorizationInfo.addRoles(roleIds);
        simpleAuthorizationInfo.addStringPermissions(resourceService.getUserPerms(uid));
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getPrincipal();
        // 判断Token是否过期
        ApiAssert.isFalse(ErrorCodeEnum.UNAUTHORIZED, JWTUtils.isExpired(token));
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
