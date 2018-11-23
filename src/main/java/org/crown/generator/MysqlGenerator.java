/*
 * Copyright (c) 2018-2019 Caratacus, (caratacus@qq.com).
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
package org.crown.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * Mysql代码生成器
 * </p>
 *
 * @author Caratacus
 */
public class MysqlGenerator {

    /**
     * <p>
     * MySQL generator
     * </p>
     */
    public static void generator() {

        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("createTime", FieldFill.INSERT));
        tableFillList.add(new TableFill("updateTime", FieldFill.INSERT_UPDATE));

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                // 全局配置
                new GlobalConfig()
                        .setOutputDir("/develop/code/")//输出目录
                        .setFileOverride(true)// 是否覆盖文件
                        .setActiveRecord(false)// 开启 activeRecord 模式
                        .setEnableCache(false)// XML 二级缓存
                        .setBaseResultMap(false)// XML ResultMap
                        .setBaseColumnList(false)// XML columList
                        .setKotlin(false) //是否生成 kotlin 代码
                        .setAuthor("Caratacus") //作者
                        //自定义文件命名，注意 %s 会自动填充表实体属性！
                        .setEntityName("%s")
                        .setMapperName("%sMapper")
                        .setXmlName("%sMapper")
                        .setServiceName("I%sService")
                        .setServiceImplName("%sServiceImpl")
                        .setControllerName("%sController")
        ).setDataSource(
                // 数据源配置
                new DataSourceConfig()
                        .setDbType(DbType.MYSQL)// 数据库类型
                        .setTypeConvert(new MySqlTypeConvert() {
                            @Override
                            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                                if (fieldType.toLowerCase().contains("bit")) {
                                    return DbColumnType.BOOLEAN;
                                }
                                if (fieldType.toLowerCase().contains("tinyint")) {
                                    return DbColumnType.BOOLEAN;
                                }
                                if (fieldType.toLowerCase().contains("date")) {
                                    return DbColumnType.LOCAL_DATE;
                                }
                                if (fieldType.toLowerCase().contains("time")) {
                                    return DbColumnType.LOCAL_TIME;
                                }
                                if (fieldType.toLowerCase().contains("datetime")) {
                                    return DbColumnType.LOCAL_DATE_TIME;
                                }
                                return super.processTypeConvert(globalConfig, fieldType);
                            }
                        })
                        .setDriverName("com.mysql.cj.jdbc.Driver")
                        .setUsername("root")
                        .setPassword("521")
                        .setUrl("jdbc:mysql://127.0.0.1:3306/crown?characterEncoding=utf8")
        ).setStrategy(
                // 策略配置
                new StrategyConfig()
                        .setCapitalMode(false)// 全局大写命名
                        .setTablePrefix("sys_")// 去除前缀
                        .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                        //.setInclude(new String[] { "user" }) // 需要生成的表
                        //自定义实体父类
                        .setSuperEntityClass("org.crown.common.framework.model.BaseModel")
                        // 自定义实体，公共字段
                        .setSuperEntityColumns("id")
                        .setTableFillList(tableFillList)
                        // 自定义 mapper 父类
                        .setSuperMapperClass("org.crown.common.framework.mapper.BaseMapper")
                        // 自定义 controller 父类
                        .setSuperControllerClass("org.crown.common.framework.controller.SuperController")
                        // 自定义 service 实现类父类
                        .setSuperServiceImplClass("org.crown.common.framework.service.impl.BaseServiceImpl")
                        // 自定义 service 接口父类
                        .setSuperServiceClass("org.crown.common.framework.service.BaseService")
                        // 【实体】是否生成字段常量（默认 false）
                        .setEntityColumnConstant(true)
                        // 【实体】是否为构建者模型（默认 false）
                        .setEntityBuilderModel(false)
                        // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                        .setEntityLombokModel(true)
                        // Boolean类型字段是否移除is前缀处理
                        .setEntityBooleanColumnRemoveIsPrefix(true)
                        .setRestControllerStyle(false)
                // .setControllerMappingHyphenStyle(true)
        ).setPackageInfo(
                // 包配置
                new PackageConfig()
                        .setParent("org.crown")
                        .setController("controller")
                        .setEntity("model.entity")
                        .setMapper("mapper")
                        .setService("service")
                        .setServiceImpl("service.impl")
        ).setCfg(
                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                        Map<String, Object> map = new HashMap<>();
                        this.setMap(map);
                    }
                }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig(
                        "/templates/mapper.xml.vm") {
                    // 自定义输出文件目录
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        return "/develop/code/xml/" + tableInfo.getEntityName() + ".xml";
                    }
                }))
        ).setTemplate(
                // 关闭默认 xml 生成，调整生成 至 根目录
                new TemplateConfig().setXml(null)
                // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                // .setController("...");
                // .setEntity("...");
                // .setMapper("...");
                // .setXml("...");
                // .setService("...");
                // .setServiceImpl("...");
        );
        mpg.execute();
    }

}
