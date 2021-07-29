/**
 * 
 */
package com.cmb.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author waliu.faleye
 *
 */

@Configuration
@Component
//@ComponentScan("com.cmb.services.DatabaseService")
public class DataSourceBean {
	
	 @ConfigurationProperties(prefix = "spring.datasource")
	    @Bean
	    @Primary
	    public DataSource getDataSource() {
	        return DataSourceBuilder
	                .create()
	                .build();
	    }
	 
	 @ConfigurationProperties(prefix = "spring.oracledatasource")
	    @Bean(name = "oracleDatasource")
	    //@Primary
	    public DataSource getOracleDataSource() {
	        return DataSourceBuilder
	                .create()
	                .build();
	    }
	 
	 /*@Bean
		public LayoutDialect layoutDialect() {
			return new LayoutDialect();
		}*/

}
