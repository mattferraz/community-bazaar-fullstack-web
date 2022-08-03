package com.tads.mhsf.bazaar.repository;

import com.tads.mhsf.bazaar.dao.ConnectionManager;
import com.tads.mhsf.bazaar.entity.Product;
import com.tads.mhsf.bazaar.exception.RepositoryException;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository implements CrudRepository<Product, Integer> {

    private final ProductsBatchRepository productsBatchRepository;

    public ProductRepository(ProductsBatchRepository productsBatchRepository) {
        this.productsBatchRepository = productsBatchRepository;
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO product(id_products_batch, name, brand, category, description) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            constructStatement(pstm, product);
            pstm.execute();
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) product.setId(generatedKeys.getInt(1));
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
        return product;
    }

    @Override
    public Optional<Product> findById(Integer id) {
        String sql = "SELECT * FROM product WHERE id=?";
        Product product = null;
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                product = constructProductFromQuery(result);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return Optional.ofNullable(product);
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE product SET id_products_batch=?, name=?, brand=?, category=?, description=? WHERE id=?";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            constructStatement(pstm, product);
            pstm.setInt(6, product.getId());
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM product WHERE id=?";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet result = pstm.executeQuery();
            while (result.next()) {
                Product product = constructProductFromQuery(result);
                products.add(product);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return products;
    }

    public List<Product> findAllProductsFromBatchById(int productsBatchId) {
        String sql = "SELECT * FROM product WHERE id_products_batch=?";
        List<Product> productsFromBatch = new ArrayList<>();
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setInt(1, productsBatchId);
            ResultSet result  = pstm.executeQuery();
            while (result.next()) {
                Product product = constructProductFromQuery(result);
                productsFromBatch.add(product);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return productsFromBatch;
    }

    public void deleteAllProductsFromProductsBatchId(int productsBatchId) {
        String sql = "DELETE FROM product WHERE id_products_batch=?";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setInt(1, productsBatchId);
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }

    private Product constructProductFromQuery(ResultSet result) throws SQLException {
        return new Product(
                result.getInt("id"),
                result.getString("name"),
                result.getString("brand"),
                result.getString("category"),
                result.getString("description"),
                productsBatchRepository
                        .findById(result.getInt("id_products_batch"))
                        .orElseThrow(SQLException::new)
        );
    }

    private void constructStatement(PreparedStatement pstm, Product product) throws SQLException {
        pstm.setInt(1, product.getProductsBatch().getId());
        pstm.setString(2, product.getName());
        pstm.setString(3, product.getBrand());
        pstm.setString(4, product.getCategory());
        pstm.setString(5, product.getDescription());
    }
}
