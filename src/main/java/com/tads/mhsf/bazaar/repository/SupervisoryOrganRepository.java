package com.tads.mhsf.bazaar.repository;

import com.tads.mhsf.bazaar.dao.ConnectionManager;
import com.tads.mhsf.bazaar.entity.SupervisoryOrgan;
import com.tads.mhsf.bazaar.exception.RepositoryException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SupervisoryOrganRepository implements CrudRepository<SupervisoryOrgan, Integer> {
    @Override
    public SupervisoryOrgan save(SupervisoryOrgan supervisoryOrgan) {
        String sql = "INSERT INTO supervisory_organ(name, description) VALUES (?, ?)";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, supervisoryOrgan.getName());
            pstm.setString(2, supervisoryOrgan.getDescription());
            pstm.execute();
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) supervisoryOrgan.setId(generatedKeys.getInt(1));
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return supervisoryOrgan;
    }

    @Override
    public Optional<SupervisoryOrgan> findById(Integer id) {
        String sql = "SELECT * FROM supervisory_organ WHERE id=?";
        SupervisoryOrgan supervisoryOrgan = null;
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                supervisoryOrgan = constructSupervisoryOrganFromQuery(result);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return Optional.ofNullable(supervisoryOrgan);
    }

    @Override
    public void update(SupervisoryOrgan supervisoryOrgan) {
        String sql = "UPDATE supervisory_organ SET name=?, description=? WHERE id=?";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setString(1, supervisoryOrgan.getName());
            pstm.setString(2, supervisoryOrgan.getDescription());
            pstm.setInt(3, supervisoryOrgan.getId());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            String sql = "DELETE FROM supervisory_organ WHERE id=?";
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<SupervisoryOrgan> findAll() {
        String sql = "SELECT * FROM supervisory_organ";
        List<SupervisoryOrgan> supervisoryOrgans = new ArrayList<>();
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet result = pstm.executeQuery();
            while (result.next()) {
                SupervisoryOrgan supervisoryOrgan = constructSupervisoryOrganFromQuery(result);
                supervisoryOrgans.add(supervisoryOrgan);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return supervisoryOrgans;
    }

    private SupervisoryOrgan constructSupervisoryOrganFromQuery(ResultSet result) throws SQLException {
        return new SupervisoryOrgan(
                result.getInt("id"),
                result.getString("name"),
                result.getString("description")
        );
    }
}
