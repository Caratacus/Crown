package org.crown.common.mybatisplus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.crown.common.http.RequestKit;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;


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
     * 创建者ID
     */
    private final String createUid = "createUid";

    /**
     * 修改者ID
     */
    private final String updateUid = "updateUid";

    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName(createTime, LocalDateTime.now(), metaObject, FieldFill.INSERT);
        setFieldValByName(createUid, currentUid(), metaObject, FieldFill.INSERT);
        setFieldValByName(updateTime, LocalDateTime.now(), metaObject, FieldFill.INSERT);
        setFieldValByName(updateUid, currentUid(), metaObject, FieldFill.INSERT);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName(updateTime, LocalDateTime.now(), metaObject, FieldFill.UPDATE);
        setFieldValByName(updateUid, currentUid(), metaObject, FieldFill.UPDATE);
    }

    /**
     * 弃用mp的填充策略,自己实现
     *
     * @param fieldName
     * @param fieldVal
     * @param metaObject
     * @param fieldFill
     * @return
     */
    public MetaObjectHandler setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject, FieldFill fieldFill) {
        if (Objects.nonNull(fieldVal)) {
            if (metaObject.hasSetter(fieldName) && metaObject.hasGetter(fieldName) && isFill(fieldName, metaObject, fieldFill)) {
                metaObject.setValue(fieldName, fieldVal);
            } else if (metaObject.hasGetter(Constants.ENTITY)) {
                Object et = metaObject.getValue(Constants.ENTITY);
                if (et != null) {
                    MetaObject etMeta = SystemMetaObject.forObject(et);
                    if (etMeta.hasSetter(fieldName) && isFill(fieldName, etMeta, fieldFill)) {
                        etMeta.setValue(fieldName, fieldVal);
                    }
                }
            }
        }
        return this;
    }

    /**
     * 获取当前用户ID
     */
    private Integer currentUid() {
        Integer uid = null;
        try {
            uid = RequestKit.currentUid();
        } catch (Exception ignored) {
        }
        return uid;
    }

    /**
     * 是否填充
     *
     * @param fieldName  字段名
     * @param metaObject
     * @param fieldFill  填充策略
     * @return
     */
    private boolean isFill(String fieldName, MetaObject metaObject, FieldFill fieldFill) {
        TableInfo tableInfo = metaObject.hasGetter(OptimisticLockerInterceptor.MP_OPTLOCK_ET_ORIGINAL) ? TableInfoHelper.getTableInfo(metaObject.getValue(OptimisticLockerInterceptor.MP_OPTLOCK_ET_ORIGINAL).getClass()) : TableInfoHelper.getTableInfo(metaObject.getOriginalObject().getClass());
        if (Objects.nonNull(tableInfo)) {
            Optional<TableFieldInfo> first = tableInfo.getFieldList().stream().filter(e -> e.getProperty().equals(fieldName)).findFirst();
            if (first.isPresent()) {
                FieldFill fill = first.get().getFieldFill();
                return fill.equals(fieldFill) || FieldFill.INSERT_UPDATE.equals(fill);
            }
        }
        return false;
    }


}