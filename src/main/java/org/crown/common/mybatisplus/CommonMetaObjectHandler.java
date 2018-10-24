package org.crown.common.mybatisplus;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.annotation.FieldFill;
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

    @Override
    public void insertFill(MetaObject metaObject) {
        fillOfInsertOrUpdate(metaObject, FieldFill.INSERT);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        fillOfInsertOrUpdate(metaObject, FieldFill.INSERT_UPDATE);
    }

    /**
     * 填充方法
     *
     * @param metaObject
     * @param fieldFill
     */
    private void fillOfInsertOrUpdate(MetaObject metaObject, FieldFill fieldFill) {
        switch (fieldFill) {
            case INSERT:
                setFieldValByName(createTime, LocalDateTime.now(), metaObject);
                setFieldValByName(updateTime, LocalDateTime.now(), metaObject);
                break;
            case UPDATE:
                setFieldValByName(updateTime, LocalDateTime.now(), metaObject);
                break;
            default:
        }

    }

}