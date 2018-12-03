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
import org.crown.common.utils.JWTTokenUtils;
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

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Integer uid = JWTTokenUtils.getUid(principals.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roleIds = userService.getRoleIds(uid).stream().map(TypeUtils::castToString).collect(Collectors.toSet());
        simpleAuthorizationInfo.addRoles(roleIds);
        simpleAuthorizationInfo.addStringPermissions(resourceService.getUserPerms(uid));
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getPrincipal();
        // 解密获得username，用于和数据库进行对比
        ApiAssert.isFalse(ErrorCodeEnum.UNAUTHORIZED, JWTTokenUtils.isExpired(token));
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
