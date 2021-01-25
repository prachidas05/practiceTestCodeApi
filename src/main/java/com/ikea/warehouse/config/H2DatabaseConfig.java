package com.ikea.warehouse.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * The Configuration Class. Application Database Configuration
 *
 * @author Prachi Das
 */
@Configuration
@EnableTransactionManagement
public class H2DatabaseConfig {

	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String password;
	/**
	 * Bean definition
	 *
	 * @return DataSource
	 */

	public DataSource dataSource() {

		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(driverClassName);
		dataSourceBuilder.url(datasourceUrl);
		dataSourceBuilder.username(userName);
		dataSourceBuilder.password(password);
		return dataSourceBuilder.build();

	}


	@Bean(name = "sessionFactory")
	public SessionFactory sessionFactory() {
		final LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource());
		sessionBuilder.addProperties(getHibernateProperties());
		sessionBuilder.scanPackages("com.ikea.warehouse");
		return sessionBuilder.buildSessionFactory();
	}

	/**
	 * Gets the transaction manager.
	 *
	 * @return the transaction manager
	 */
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager() {
		final HibernateTransactionManager transManager = new HibernateTransactionManager(sessionFactory());
		transManager.setNestedTransactionAllowed(true);
		return transManager;
	}

	/**
	 * Gets Hibernate properties
	 * @return propeties
	 */
	public Properties getHibernateProperties() {
		final Properties properties = new Properties();
		properties.put("hibernate.show_sql", true);
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.id.new_generator_mappings", true);
		properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		return properties;
	}

}
