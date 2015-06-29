package com.madhouse.fmp.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@ComponentScan(basePackages = "com.madhouse.fmp", excludeFilters = { @Filter(Configuration.class) })
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class MainConfig {

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
    
    @Resource
	private Environment env;
	
	@Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();

        dataSource.setDriverClass(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setJdbcUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
//        dataSource.setIdleConnectionTestPeriod(6);
//        dataSource.setIdleMaxAge(24);
        dataSource.setMaxConnectionsPerPartition(5);
        dataSource.setMinConnectionsPerPartition(1);
        dataSource.setPartitionCount(4);
        dataSource.setAcquireIncrement(5);
//        dataSource.setStatementCacheSize(100);
//        dataSource.setReleaseHelperThreads(3);

        return dataSource;
    }
	
	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean session = new SqlSessionFactoryBean();
		
		ClassPathResource configLocation = new ClassPathResource("mybatis.xml");
		session.setConfigLocation(configLocation);
		session.setDataSource(dataSource());
		
		return session.getObject();
	}
	
	@Bean(name="sqlSession")
	public SqlSessionTemplate SqlSessionTemplate() throws Exception {
		SqlSessionTemplate sqlSession = new SqlSessionTemplate(sqlSessionFactoryBean());
		return sqlSession;
	}
	
//	<!-- 事务配置 -->
//	<bean id="transactionManager"
//		class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
//		<property name="dataSource" ref="dataSource" />
//	</bean>
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
}
