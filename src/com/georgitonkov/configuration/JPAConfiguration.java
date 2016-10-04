package com.georgitonkov.configuration;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource(value = { "classpath:config.properties" })
@EnableTransactionManagement
public class JPAConfiguration {
	private static final String MODEL_PACKAGE = "com.georgitonkov.model";
	@Autowired
	Environment env;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(props("db.driver"));
		ds.setUrl(props("db.url") + "?characterEncoding=" + props("db.encoding") + "&useSSL=" + props("db.useSSL"));
		ds.setUsername(props("db.username"));
		ds.setPassword(props("db.password"));
		return ds;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean ();
		factory.setDataSource(dataSource());
		factory.setPackagesToScan(new String[]{MODEL_PACKAGE});
		factory.setJpaVendorAdapter(jpaVendorAdapter());
		factory.setJpaProperties(jpaProperties());
		return factory;
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}
	
	@Bean
	public Properties jpaProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", props("hibernate.dialect"));
		properties.setProperty("hibernate.hbm2ddl.auto", props("hibernate.hbm2ddl.auto"));
		properties.setProperty("hibernate.showSQL", props("hibernate.showSQL"));
		properties.setProperty("hibernate.formatSQL", props("hibernate.formatSQL"));
		return properties;
	}
	
	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager (EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
	private String props(String prop) {
		return env.getProperty(prop);
	}
}
