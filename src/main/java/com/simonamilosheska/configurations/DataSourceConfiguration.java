package com.simonamilosheska.configurations;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfiguration {

  @Value("${url}")
  private String URL;
  @Value("${user}")
  private String USER;
  @Value("${password}")
  private String PASSWORD;
  @Value("${cache_prepared_statements}")
  private String CACHE_PREPARED_STATEMENTS;
  @Value("${prepared_statements_cache_size}")
  private String PREPARED_STATEMENT_CACHE_SIZE;
  @Value("${prepared_statements_cache_sql_limit}")
  private String PREPARED_STATEMENT_CACHE_SQL_LIMIT;

  private final HikariConfig config = new HikariConfig();

  @Bean
  public HikariDataSource hikariDataSource() {
    config.setJdbcUrl(URL);
    config.setUsername(USER);
    config.setPassword(PASSWORD);
    config.addDataSourceProperty("cachePrepStmts", CACHE_PREPARED_STATEMENTS);
    config.addDataSourceProperty("prepStmtCacheSize", PREPARED_STATEMENT_CACHE_SIZE);
    config.addDataSourceProperty("prepStmtCacheSqlLimit", PREPARED_STATEMENT_CACHE_SQL_LIMIT);
    return new HikariDataSource(config);
  }
}