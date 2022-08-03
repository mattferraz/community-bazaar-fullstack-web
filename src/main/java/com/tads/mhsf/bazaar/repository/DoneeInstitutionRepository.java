package com.tads.mhsf.bazaar.repository;

import com.tads.mhsf.bazaar.dao.ConnectionManager;
import com.tads.mhsf.bazaar.entity.DoneeInstitution;
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
public class DoneeInstitutionRepository implements CrudRepository<DoneeInstitution, Integer> {
    @Override
    public DoneeInstitution save(DoneeInstitution doneeInstitution) {
        String sql = "INSERT INTO donee_institution(name, address, phone_number, description) VALUES (?, ?, ?, ?) RETURNING id";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, doneeInstitution.getName());
            pstm.setString(2, doneeInstitution.getAddress());
            pstm.setString(3, doneeInstitution.getPhoneNumber());
            pstm.setString(4, doneeInstitution.getDescription());
            pstm.execute();
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) {
                doneeInstitution.setId(generatedKeys.getInt(1));
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return doneeInstitution;
    }

    @Override
    public Optional<DoneeInstitution> findById(Integer id) {
        String sql = "SELECT * FROM donee_institution WHERE id=?";
        DoneeInstitution doneeInstitution = null;
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                doneeInstitution = constructDoneeInstitutionFromQuery(result);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return Optional.ofNullable(doneeInstitution);
    }

    @Override
    public void update(DoneeInstitution doneeInstitution) {
        String sql = "UPDATE donee_institution SET name=?, address=?, phone_number=?, description=? WHERE id=?";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setString(1, doneeInstitution.getName());
            pstm.setString(2, doneeInstitution.getAddress());
            pstm.setString(3, doneeInstitution.getPhoneNumber());
            pstm.setString(4, doneeInstitution.getDescription());
            pstm.setInt(5, doneeInstitution.getId());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM donee_institution WHERE id=?";
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
    public List<DoneeInstitution> findAll() {
        String sql = "SELECT * FROM donee_institution";
        List<DoneeInstitution> doneeInstitutions = new ArrayList<>();
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet result = pstm.executeQuery();
            while (result.next()) {
                DoneeInstitution doneeInstitution = constructDoneeInstitutionFromQuery(result);
                doneeInstitutions.add(doneeInstitution);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return doneeInstitutions;
    }

    private DoneeInstitution constructDoneeInstitutionFromQuery(ResultSet result) throws SQLException {
        return new DoneeInstitution(
                result.getInt("id"),
                result.getString("name"),
                result.getString("address"),
                result.getString("phone_number"),
                result.getString("description")
        );
    }
}
