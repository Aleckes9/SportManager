/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.Database;

/**
 * A configuration for 'Local BD' profile.
 * 
 * @author DINB - TA
 */
@Configuration
@Profile("local_BD")
public class LocalBdProfileConfiguration {

	/**
	 * Builds a JNDI datasource.
	 * @return the datasource.
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		//dataSource.setDriverClassName(oracle.jdbc.OracleDriver.class.getName());
		dataSource.setUrl("jdbc:oracle:thin:@{host}:{port}:{sid}");
		dataSource.setUsername("{user}");
		dataSource.setPassword("{password}");
		return dataSource;
	}

	/**
	 * Returns the JPA dialect to use.
	 * @return the JPA dialect to use.
	 */
	@Bean
	public Database jpaDialect() {
		return Database.ORACLE;
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

