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
  entityManagerFactoryRef = "spbupopEntityManagerFactory",
  transactionManagerRef = "spbupopTransactionManager",
  basePackages = { "com.hitrsutpay.sa.backend.acq.spbupop.dao" }
)
public class SPBUPOPDBConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "spbupop.datasource")
	public DataSourceProperties spbupopDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "spbupopDataSource")
	@ConfigurationProperties(prefix = "spbupop.datasource.hikari")
	public DataSource dataSource() {
		return spbupopDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean(name = "spbupopEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean spbupopEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("spbupopDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.hitrsutpay.sa.backend.acq.spbupop.domain").persistenceUnit("spbupop")
				.build();
	}

	@Bean(name = "spbupopTransactionManager")
	public PlatformTransactionManager spbupopTransactionManager(
			@Qualifier("spbupopEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}


	@Bean("spbupopJdbcTemplate")
	public JdbcTemplate spbupopJdbcTemplate() {
		return new JdbcTemplate(this.dataSource());
	}  
	
}

