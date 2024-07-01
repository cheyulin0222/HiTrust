package com.hitrsutpay.sa.backend.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  entityManagerFactoryRef = "spbEntityManagerFactory",
  transactionManagerRef = "spbTransactionManager",
  basePackages = { "com.hitrsutpay.sa.backend.acq.spb.dao" }
)
public class SPBDBConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "spb.datasource")
	public DataSourceProperties spbDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "spbDataSource")
	@ConfigurationProperties(prefix = "spb.datasource.hikari")
	public DataSource dataSource() {
		return spbDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean(name = "spbEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean spbEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("spbDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.hitrsutpay.sa.backend.acq.spb.domain").persistenceUnit("spb")
				.build();
	}

	@Bean(name = "spbTransactionManager")
	public PlatformTransactionManager spbTransactionManager(
			@Qualifier("spbEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}


	@Bean("spbJdbcTemplate")
	public JdbcTemplate spbJdbcTemplate() {
		return new JdbcTemplate(this.dataSource());
	}  
	
}

