package com.mm.boot.multidb;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "customerEntityManager", transactionManagerRef = "customerTransactionManager", basePackages = {
		"com.mm.boot.multidb.repository.customer" })
public class CustomerDbConfig {

	@Bean(name = "customerEntityManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			@Qualifier("postgresdb") DataSource barDataSource) {
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(barDataSource);
		em.setPackagesToScan(new String[] { "com.mm.boot.multidb.model.customer" });
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalJpaProperties());
		em.setPersistenceUnitName("customers");

		return em;
	}

	Properties additionalJpaProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.show_sql", "false");

		return properties;
	}

	@Bean(name = "postgresdb")
	public DataSource dataSource() {
		return DataSourceBuilder.create().url("jdbc:postgresql://localhost:5432/springbootdb")
				.driverClassName("org.postgresql.Driver").username("postgres").password("root").build();
	}

	@Bean(name = "customerTransactionManager")
	public JpaTransactionManager transactionManager(@Qualifier("customerEntityManager") EntityManagerFactory customerEntityManager) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(customerEntityManager);

		return transactionManager;
	}
}