package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {

  Connection connection = null;
  EmbeddedDatabase dataSource = null;
  ProductsRepository productsRepository = null;
  final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(new Product(0L, "gleb", 1), new Product(1L, "gleb1", 2), new Product(2L, "gleb2", 3));
  final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(0L, "gleb", 1);
  final Product EXPECTED_UPDATED_PRODUCT = new Product(2L, "laptev", 2);

  @BeforeEach
  public void init() throws SQLException {
    dataSource = getDataSource();
    connection = dataSource.getConnection();
    productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
  }

  private static EmbeddedDatabase getDataSource() {
    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    return builder
      .setType(EmbeddedDatabaseType.HSQL)
      .addScript("shema.sql")
      .addScript("data.sql")
      .build();
  }

  @Test
  public void findAll() throws SQLException {
    List<Product> all = productsRepository.findAll();
    for (Product product: all) {
      Assert.assertTrue(EXPECTED_FIND_ALL_PRODUCTS.contains(product));
    }
    dataSource.shutdown();
  }

  @Test
  public void findById() throws SQLException {
    Optional<Product> byId = productsRepository.findById(0L);
    assert byId.isPresent();
    Assert.assertEquals(byId.get(), EXPECTED_FIND_BY_ID_PRODUCT);
    dataSource.shutdown();
  }

  @Test
  public void save() throws SQLException {
    Product product = new Product("gleb5", 5);
    productsRepository.save(product);
    Optional<Product> byId = productsRepository.findById(product.getId());
    assert byId.isPresent();
    Assert.assertEquals(byId.get(), product);
    dataSource.shutdown();
  }

  @Test
  public void update() throws SQLException {
    productsRepository.update(EXPECTED_UPDATED_PRODUCT);
    Optional<Product> byId = productsRepository.findById(EXPECTED_UPDATED_PRODUCT.getId());
    assert byId.isPresent();
    Assert.assertEquals(byId.get().getId(), EXPECTED_UPDATED_PRODUCT.getId());
    Assert.assertEquals(byId.get().getName(), EXPECTED_UPDATED_PRODUCT.getName());
    Assert.assertEquals(byId.get().getPrice(), EXPECTED_UPDATED_PRODUCT.getPrice());
    dataSource.shutdown();
  }

  @Test
  public void delete() throws SQLException {
    productsRepository.delete(2L);
    Optional<Product> byId = productsRepository.findById(2L);
    assert !byId.isPresent();
    dataSource.shutdown();
  }
}
