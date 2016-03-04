/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.configuration;

import java.util.Arrays;

import javax.sql.DataSource;

import org.dozer.DozerBeanMapper;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * A base configuration bootstrapping the application context.
 * 
 * @author DINB - TA
 */
@Configuration
@Import({ DefaultProfileConfiguration.class, H2ProfileConfiguration.class,
		LocalBdProfileConfiguration.class})
@ComponentScan(
	    basePackages = {"com.mycompany.projetsportmanager.service"},
	        excludeFilters = @Filter(
	            type = FilterType.ANNOTATION,
	                value = Configuration.class))
@EnableTransactionManagement
@EnableJpaRepositories(transactionManagerRef = "myTxManager", basePackages = "com.mycompany.projetsportmanager.repository", entityManagerFactoryRef = "myEmf")
public class BaseConfiguration {
	
	/**
	 * The datasource provided by other configurations.
	 */
	@Autowired
	private DataSource dataSource;

	/**
	 * The dialect provided by other configurations.
	 */
	@Autowired
	@Qualifier("jpaDialect")
	private Database jpaDialect;

	/**
	 * The flag generateDdl provided by other configurations.
	 */
	@Autowired
	@Qualifier("generateDdl")
	private Boolean generateDdl; 

	/**
	 * The flag showSql provided by other configurations.
	 */
	@Autowired
	@Qualifier("showSql")
	private Boolean showSql;

	/**
	 * Gets the bean mapper.
	 * @return the bean mapper.
	 */
	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		return new DozerBeanMapper(Arrays.asList("dozer-bean-mappings.xml"));
	}

	/**
	 * Builds the persistence unit manager.
	 * @param dataSource the datasource.
	 * @return the persistence unit manager.
	 */
	@Bean
	public DefaultPersistenceUnitManager persistenceUnitManager() {
		DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
		defaultPersistenceUnitManager.setPersistenceXmlLocation("classpath*:/META-INF/persistence.xml");
		defaultPersistenceUnitManager.setDefaultDataSource(dataSource);
		return defaultPersistenceUnitManager;
	}
	
	/**
	 * Builds the persistence unit manager.
	 * @param dataSource the datasource.
	 * @return the persistence unit manager.
	 */
	@Bean
	public HibernateJpaVendorAdapter jpaAdapter() {
		HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
		jpaAdapter.setDatabase(jpaDialect);
		jpaAdapter.setGenerateDdl(generateDdl);
		jpaAdapter.setShowSql(showSql);
		return jpaAdapter;
	}
	
	/**
	 * Builds the persistence unit manager.
	 * @param dataSource the datasource.
	 * @return the persistence unit manager.
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean myEmf() {
		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setDataSource(dataSource);
		localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManager());
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter());
		//localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		localContainerEntityManagerFactoryBean.setPersistenceProvider(jpaAdapter().getPersistenceProvider());
		if(!generateDdl) {
			localContainerEntityManagerFactoryBean.getJpaPropertyMap().put(Environment.HBM2DDL_AUTO, "validate");
		}
		return localContainerEntityManagerFactoryBean;
	}
	
	/**
	 * Builds the persistence unit manager.
	 * @param dataSource the datasource.
	 * @return the persistence unit manager.
	 */
	@Bean
	public PlatformTransactionManager myTxManager() {
		return new JpaTransactionManager(myEmf().getObject());
	}
}

