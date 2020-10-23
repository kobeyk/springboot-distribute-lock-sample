package com.appleyk.db.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 *
 *    自动代码生成器（myabtisplus逆向工程），本项目中controller、service、dao均是自动生成的
 *    注意： 由于代码已经生成，但是配置里面设置的重新生成会覆盖之前的文件，因此，博主已经把它改成false了
 *    .setFileOverride(true)  ====》 .setFileOverride(false)
 *
 * </p>
 *
 * @author Appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/Appleyk
 * @date created on 14:01 2020/10/20
 */
public class TCodeGenerator {

    /**项目存储位置*/
    public static String PROJECT_GENERATE_DISK = System.getProperty("user.dir")+"/db-lock/src/main/java";
    /**数据库地址*/
    public static String DB_URL = "jdbc:postgresql://localhost:5432/distribute_lock_db?stringtype=unspecified";
    /**数据库驱动名*/
    public static String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    /**数据库类型*/
    public static String DB_TYPE = DbType.POSTGRE_SQL.name();
    /**数据库用户*/
    public static String USER = "postgres";
    /**数据库密码*/
    public static String PASSWORD = "postgres";
    /**参与逆向工程的表名**/
    public static String[] INCLUDE_TABLE_NAMES = new String[]{"t_commodity_stock","t_resource_lock"};
    /**不参与逆向工程的表名**/
    public static String[] EXCLUDE_TABLE_NAMES = new String[]{"spatial_ref_sys"};
    /**创建人*/
    public static String AUTHOR = "Appleyk";
    /**表字段生成对应的实体类时，字段属性是否需要强制带上注解*/
    public static boolean ENABLE_TABLE_FIELD_ANNOTATION = false;
    /**表id，用哪一种生成策略，如下面用的是分布式全局唯一ID，是不是分布式暂时没考就哈*/
    public static IdType TABLE_ID_TYPE = IdType.ID_WORKER;
    /**是否去掉生成实体的属性名前缀,如表有t_table1,m_table2，则这个值可以为 = new String[]{"t","m"}*/
    public static String[] FIELD_PREFIX = null;

    /**
     * 全局配置
     * @return
     */
    private static GlobalConfig GlobalGenerate(){
        GlobalConfig config = new GlobalConfig();
        /*不需要ActiveRecord特性的请改为false*/
        config.setActiveRecord(false)
                .setIdType(TABLE_ID_TYPE)
                /*是否启用二级缓存*/
                .setEnableCache(false)
                /*设置作者*/
                .setAuthor(AUTHOR)
                /*生成完之后，不弹窗，告知我在生成在哪个目录了*/
                .setOpen(false)
                /*XML 设置映射结果 ResultMap*/
                .setBaseResultMap(true)
                /*XML 设置表列 ColumnList*/
                .setBaseColumnList(true)
                /*设置生产的文件（包）在哪，一般是相对于本项目而言*/
                .setOutputDir(PROJECT_GENERATE_DISK)
                /*每次生成，是否覆盖之前的文件（慎重考虑啊,别自己撸了半天代码， 一不小run错了，就悲剧了）*/
                .setFileOverride(false)
                /*自定义文件命名，注意 %s 会自动填充表实体属性！*/
                .setControllerName("%sController")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setMapperName("%sMapper")
                .setXmlName("%sMapper");
        return config;
    }

    /**
     * 数据源配置
     * @return
     */
    private static DataSourceConfig DaoSourceGenerate(){
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        DbType type=null;
        if("oracle".equals(DB_TYPE)){
            type=DbType.ORACLE;
        }else if("sql_server".equals(DB_TYPE)){
            type=DbType.SQL_SERVER;
        }else if("mysql".equals(DB_TYPE)){
            type=DbType.MYSQL;
        }else if("postgre_sql".equals(DB_TYPE)){
            type=DbType.POSTGRE_SQL;
        }
        /*设置数据库类型*/
        dataSourceConfig.setDbType(type)
                /*设置数据库驱动（反射，基于类名，找到对应的类）*/
                .setDriverName(TCodeGenerator.DRIVER_CLASS_NAME)
                /*设置数据库连接地址，地址包含库ip、端口、库名等*/
                .setUrl(DB_URL)
                /*设置数据库用户名*/
                .setUsername(TCodeGenerator.USER)
                /*设置数据库密码*/
                .setPassword(TCodeGenerator.PASSWORD);
        return dataSourceConfig;
    }

    /**
     * 策略配置
     * @return
     */
    private static StrategyConfig StrategyGenerate() {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setVersionFieldName("version")
                /*全局大写命名 ORACLE 注意*/
                .setCapitalMode(true)
                /*生成RestController*/
                .setRestControllerStyle(true)
                /*是否使用Lombok省略getter、setter*/
                .setEntityLombokModel(false)
//                .setDbColumnUnderline(true)
                /*表名生成策略 -- 驼峰*/
                .setNaming(NamingStrategy.underline_to_camel)
                .setEntityTableFieldAnnotationEnable(ENABLE_TABLE_FIELD_ANNOTATION)
                /* 生成指定的xxxController、、xxService等是否去掉数据库表名的前缀，如t_user -> user -> UserController*/
                .setFieldPrefix(FIELD_PREFIX)
                /*设置哪些表参与逆向工程，多个表名传数组*/
                .setInclude(INCLUDE_TABLE_NAMES)
                /*设置哪些表不参与逆向工程，多个表名传数组；注意，不能和Include一起使用*/
//                .setExclude(EXCLUDE_TABLE_NAMES)
                /*此处可以修改为您的表前缀*/
                .setTablePrefix(null)
                /*自定义实体，公共字段*/
                .setSuperEntityColumns(null)
                /*自定义 mapper 父类*/
                .setSuperMapperClass(null)
                /*自定义 service 父类*/
                .setSuperServiceClass(null)
                /*自定义 serviceImpl 父类*/
                .setSuperServiceImplClass(null)
                /*自定义 controller 父类*/
                .setSuperControllerClass(null)
                /*【实体】是否生成字段常量（默认 false）public static final String ID = "test_id";*/
                .setEntityColumnConstant(false)//
                /*【实体】是否为构建者模型（默认 false）public User setName(String name) {this.name = name; return this;}*/
                .setEntityBuilderModel(false)
                /*【实体】是否为lombok模型（默认 false）*/
                .setEntityLombokModel(false)
                /*Boolean类型字段是否移除is前缀处理*/
                .setEntityBooleanColumnRemoveIsPrefix(true);
        return strategyConfig;
    }

    /**
     * 自定义模板配置
     * @return
     */
    private static TemplateConfig TemplateGenerate(){
        TemplateConfig templateConfig = new TemplateConfig()
                .setController("/templates/controller.java")
                .setMapper("/templates/mapper.java")
                .setXml("/templates/mapper.xml")
                .setService("/templates/service.java")
                .setServiceImpl("/templates/serviceImpl.java");
        return templateConfig;
    }

    public static PackageConfig PackageGenerate(){
        PackageConfig pc = new PackageConfig()
                .setParent("com.appleyk.db")
                .setController("controller")
                .setService("service")
                .setServiceImpl("service.impl")
                .setEntity("dao.entity")
                .setMapper("dao.mapper")
                .setXml("dao.mapper");
        return pc;
    }

    public void generateByTablesWithInjectConfig() {
        //全局配置
        GlobalConfig config = TCodeGenerator.GlobalGenerate();
        //配置数据源
        DataSourceConfig dataSourceConfig= TCodeGenerator.DaoSourceGenerate();
        //配置策略
        StrategyConfig strategyConfig = TCodeGenerator.StrategyGenerate();
        //配置包
        PackageConfig packageConfig= TCodeGenerator.PackageGenerate();
        //生成代码
        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .execute();
    }

    public static void main(String[] args) {
        TCodeGenerator generatorServiceEntity=new TCodeGenerator();
        generatorServiceEntity.generateByTablesWithInjectConfig();
    }

}
