package org.crown.common.mybatisplus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.Delete;
import com.baomidou.mybatisplus.core.injector.methods.DeleteById;
import com.baomidou.mybatisplus.core.injector.methods.Insert;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.core.injector.methods.SelectCount;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.core.injector.methods.SelectObjs;
import com.baomidou.mybatisplus.core.injector.methods.SelectPage;
import com.baomidou.mybatisplus.core.injector.methods.Update;
import com.baomidou.mybatisplus.core.injector.methods.UpdateById;


/**
 * <p>
 * MybatisPlusSql注入器
 * </p>
 *
 * @author Caratacus
 */
public class MybatisPlusSqlInjector extends AbstractSqlInjector {

    @Override
    public boolean isInjectSqlRunner() {
        return false;
    }

    @Override
    public List<AbstractMethod> getMethodList() {
        return Stream.of(
                new Insert(),
                new Delete(),
                new DeleteById(),
                new Update(),
                new UpdateById(),
                new SelectById(),
                new SelectCount(),
                new SelectObjs(),
                new SelectList(),
                new SelectPage()
        ).collect(Collectors.toList());
    }

}
