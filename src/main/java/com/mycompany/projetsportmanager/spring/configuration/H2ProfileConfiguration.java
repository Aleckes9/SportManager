/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.configuration;

import javax.sql.DataSource;

import org.h2.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.Database;


/**
 * A configuration for 'H2 local' profile.
 * 
 * @author DINB - TA
 */
@Configuration
@Profile({"local_H2"})
public class H2ProfileConfiguration {

	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(H2ProfileConfiguration.class); 


	/**
	 * Builds a JNDI datasource.
	 * @return the datasource.
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(Driver.class.getName());
//		String h2Url = MessageFormat.format("jdbc:h2:file:{0}ProjetSportManager;MODE=Oracle", System.getProperty("java.io.tmpdir"));
		String h2Url = "jdbc:h2:file:c:\\temp\\ProjetSportManager;MODE=Oracle";
		logger.info("Using H2 with URL : {}", h2Url);
		dataSource.setUrl(h2Url);
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}

	/**
	 * Returns the JPA dialect to use.
	 * @return the JPA dialect to use.
	 */
	@Bean
	public Database jpaDialect() {
		return Database.H2;
	}

	/**
	 * Returns the Generate DDL flag.
	 * @return the Generate DDL flag.
	 */
	@Bean
	public Boolean generateDdl() {
		return Boolean.TRUE;
	}

	/**
	 * Returns the Show SQL flag.
	 * @return the Show SQL flag.
	 */
	@Bean
	public Boolean showSql() {
		return Boolean.TRUE;
	}

}

