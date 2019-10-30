/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.common.mybatisplus;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.crown.common.utils.security.ShiroUtils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * 通用填充类 适用于mybatis plus
 *
 * @author Caratacus
 */
public class CommonMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建时间
     */
    private final String createTime = "createTime";
    /**
     * 修改时间
     */
    private final String updateTime = "updateTime";
    /**
     * 创建者
     */
    private final String createBy = "createBy";
    /**
     * 修改者
     */
    private final String updateBy = "createBy";
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private final String deleted = "deleted";

    @Override
    public void insertFill(MetaObject metaObject) {
        setInsertFieldValByName(deleted, false, metaObject);
        setInsertFieldValByName(createTime, new Date(), metaObject);
        setInsertFieldValByName(createBy, currentLoginName(), metaObject);
        setInsertFieldValByName(updateTime, new Date(), metaObject);
        setInsertFieldValByName(updateBy, currentLoginName(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setUpdateFieldValByName(updateTime, new Date(), metaObject);
        setUpdateFieldValByName(updateBy, currentLoginName(), metaObject);
    }

    /*  *
     * 获取当前用户名
     */
    private String currentLoginName() {
        String loginName = "";
        try {
            loginName = ShiroUtils.getLoginName();
        } catch (Exception ignored) {
        }
        return loginName;
    }

}
