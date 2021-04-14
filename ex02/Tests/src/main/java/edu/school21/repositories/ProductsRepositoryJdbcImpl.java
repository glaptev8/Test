package edu.school21.repositories;

import edu.school21.models.Product;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

  EmbeddedDatabase dataSource;

  public ProductsRepositoryJdbcImpl(EmbeddedDatabase dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public List<Product> findAll() throws SQLException {
    String findAll = "SELECT * FROM PRODUCT";
    PreparedStatement ps = dataSource.getConnection().prepareStatement(findAll);
    ResultSet resultSet = ps.executeQuery();
    List<Product> products = new ArrayList<>();
    while (resultSet.next()) {
      Long id = resultSet.getLong("id");
      String name = resultSet.getString("name");
      int price = resultSet.getInt("price");
      products.add(new Product(id, name, price));
    }
    return products;
  }

  @Override
  public Optional<Product> findById(Long id) throws SQLException {
    String findById = "SELECT * FROM PRODUCT WHERE id = ?";
    PreparedStatement ps = dataSource.getConnection().prepareStatement(findById);
    ps.setLong(1, id);
    ResultSet resultSet = ps.executeQuery();
    if (resultSet.next()) {
      Long idProduct = resultSet.getLong("id");
      String name = resultSet.getString("name");
      int price = resultSet.getInt("price");
      return Optional.of(new Product(idProduct, name, price));
    }
    return Optional.empty();
  }

  @Override
  public void update(Product product) throws SQLException {
    String update = "UPDATE PRODUCT SET name = ?, price = ? WHERE id = ?";
    PreparedStatement ps = dataSource.getConnection().prepareStatement(update);
    ps.setString(1, product.getName());
    ps.setInt(2, product.getPrice());
    ps.setLong(3, product.getId());
    ps.executeUpdate();
  }

  @Override
  public void save(Product product) throws SQLException {
    String save = "INSERT INTO PRODUCT (id, name, price) VALUES (default, ?, ?);";
    PreparedStatement ps = dataSource.getConnection().prepareStatement(save, Statement.RETURN_GENERATED_KEYS);
    ps.setString(1, product.getName());
    ps.setInt(2, product.getPrice());
    ps.executeUpdate();
    ResultSet generatedKeys = ps.getGeneratedKeys();
    if (generatedKeys.next()) {
      product.setId(generatedKeys.getLong(1));
    }
    else {
      throw new SQLException("Creating product failed, no ID obtained.");
    }
  }

  @Override
  public void delete(Long id) throws SQLException {
    String delete = "DELETE FROM PRODUCT WHERE id = ?";
    PreparedStatement ps = dataSource.getConnection().prepareStatement(delete);
    ps.setLong(1, id);
    ps.executeUpdate();
  }
}
