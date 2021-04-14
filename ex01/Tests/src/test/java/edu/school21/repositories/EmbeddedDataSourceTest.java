package edu.school21.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.SQLException;

public class EmbeddedDataSourceTest {

  Connection connection = null;

  @BeforeEach
  public void init() throws SQLException {
    EmbeddedDatabase dataSource = getDataSource();
    connection = dataSource.getConnection();
  }

  private static EmbeddedDatabase getDataSource() throws SQLException {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    return builder
      .setType(EmbeddedDatabaseType.HSQL)
      .addScript("shema.sql")
      .addScript("data.sql")
      .build();
  }

  @Test
  public void checkConnection() {
    Assert.notNull(connection, "The object you enter return null");
  }
}
