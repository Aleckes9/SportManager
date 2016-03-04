/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.spring.configuration;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.vendor.Database;

/**
 * A configuration for 'Default' profile.
 * 
 * @author DINB - TA
 */
@Configuration
@Profile(value = "default")
public class DefaultProfileConfiguration {

	 /**
     * Creates a resource bundle.
     */
    protected static ResourceBundle SportManagerAppResourceBundle = PropertyResourceBundle.getBundle("ProjetSportManager-config");


	/**
	 * Builds a JNDI datasource.
	 * @return the datasource.
	 */
	@Bean(destroyMethod="")
	public DataSource dataSource() {
		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("jdbc/ProjetSportManager");
        jndiObjectFactoryBean.setResourceRef(true);
        jndiObjectFactoryBean.setExpectedType(DataSource.class);
        try {
            jndiObjectFactoryBean.afterPropertiesSet();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return (DataSource) jndiObjectFactoryBean.getObject();
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
		return Boolean.FALSE;
	}

	/**
	 * Returns the Show SQL flag.
	 * @return the Show SQL flag.
	 */
	@Bean
	public Boolean showSql() {
		return Boolean.valueOf(SportManagerAppResourceBundle.getString("hibernate.showSql"));
	}
}

