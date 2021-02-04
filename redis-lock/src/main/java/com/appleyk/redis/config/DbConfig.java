package com.appleyk.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>数据源配置类</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 8:31 2021/2/4
 */
@Configuration
@EnableJpaRepositories("com.appleyk.redis.repo")
@EnableTransactionManagement
public class DbConfig {


	@Autowired
	private DataSource dataSource;

	/**
	 * 配置事务工厂
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dataSource){
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		// 设置数据源
		entityManagerFactory.setDataSource(dataSource);
		// 设置适配器
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		// 加载实体类
		entityManagerFactory.setPackagesToScan("com.appleyk.redis.entity");
		Map<String,String> propertiesMap = new HashMap<>();
		// mysql8数据库方言
		propertiesMap.put("hibernate.dialect","org.hibernate.dialect.MySQL8Dialect");
		// 是否打印执行的sql(默认关闭，如果出错的话，可以放开查看sql输出)
		propertiesMap.put("hibernate.show_sql","false");
		// 如果表结构发生改变，自动更新
		propertiesMap.put("hibernate.hbm2ddl.auto","update");
		entityManagerFactory.setJpaPropertyMap(propertiesMap);
		return entityManagerFactory;
	}

	/**
	 * 配置事务管理器
	 */
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory factory){
		return new JpaTransactionManager(factory);
	}
}