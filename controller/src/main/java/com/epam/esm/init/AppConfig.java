package com.epam.esm.init;

import com.epam.esm.repository.connectionpool.ConnectionPool;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = {"com.epam.esm"})
public class AppConfig {

  @Bean
  public ConnectionPool connectionPool() {
    return new ConnectionPool();
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(connectionPool());
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public DataSourceTransactionManager manager() {
    return new DataSourceTransactionManager(connectionPool());
  }
}
