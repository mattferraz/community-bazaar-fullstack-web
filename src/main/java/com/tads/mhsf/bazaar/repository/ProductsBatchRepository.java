package com.tads.mhsf.bazaar.repository;

import com.tads.mhsf.bazaar.dao.ConnectionManager;
import com.tads.mhsf.bazaar.entity.ProductsBatch;
import com.tads.mhsf.bazaar.exception.RepositoryException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductsBatchRepository implements CrudRepository<ProductsBatch, Integer> {

    private final SupervisoryOrganRepository supervisoryOrganRepository;
    private final DoneeInstitutionRepository doneeInstitutionRepository;

    public ProductsBatchRepository(SupervisoryOrganRepository supervisoryOrganRepository,
                                   DoneeInstitutionRepository doneeInstitutionRepository) {
        this.supervisoryOrganRepository = supervisoryOrganRepository;
        this.doneeInstitutionRepository = doneeInstitutionRepository;
    }

    @Override
    public ProductsBatch save(ProductsBatch productsBatch) {
        String sql = "INSERT INTO products_batch(id_supervisory_organ, id_donee_institution, delivery_date, note) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            constructStatement(pstm, productsBatch);
            pstm.execute();
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) productsBatch.setId(generatedKeys.getInt(1));
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return productsBatch;
    }

    @Override
    public Optional<ProductsBatch> findById(Integer id) {
        String sql = "SELECT * FROM products_batch WHERE id=?";
        ProductsBatch productsBatch = null;
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                productsBatch = constructProductsBatchFromQuery(result);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return Optional.ofNullable(productsBatch);
    }

    @Override
    public void update(ProductsBatch productsBatch) {
        String sql = "UPDATE products_batch SET id_supervisory_organ=?, id_donee_institution=?, delivery_date=?, note=? WHERE id=?";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            constructStatement(pstm, productsBatch);
            pstm.setInt(5, productsBatch.getId());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM products_batch WHERE id=?";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<ProductsBatch> findAll() {
        String sql = "SELECT * FROM products_batch";
        List<ProductsBatch> productsBatches = new ArrayList<>();
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet result = pstm.executeQuery();
            while (result.next()) {
                ProductsBatch productsBatch = constructProductsBatchFromQuery(result);
                productsBatches.add(productsBatch);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return productsBatches;
    }

    private ProductsBatch constructProductsBatchFromQuery(ResultSet result) throws SQLException {
        return new ProductsBatch(
                result.getInt("id"),
                result.getString("note"),
                result.getTimestamp("delivery_date").toLocalDateTime(),
                supervisoryOrganRepository.findById(result.getInt("id_supervisory_organ")).orElseThrow(SQLException::new),
                doneeInstitutionRepository.findById(result.getInt("id_donee_institution")).orElseThrow(SQLException::new)
        );
    }

    private void constructStatement(PreparedStatement pstm, ProductsBatch productsBatch) throws SQLException {
        pstm.setInt(1, productsBatch.getSupervisoryOrgan().getId());
        pstm.setInt(2, productsBatch.getDoneeInstitution().getId());
        pstm.setTimestamp(3, Timestamp.valueOf(productsBatch.getDeliveryDate()));
        pstm.setString(4, productsBatch.getNote());
    }

}
