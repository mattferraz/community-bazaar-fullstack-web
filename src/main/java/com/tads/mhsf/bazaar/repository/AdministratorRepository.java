package com.tads.mhsf.bazaar.repository;

import com.tads.mhsf.bazaar.dao.ConnectionManager;
import com.tads.mhsf.bazaar.entity.Administrator;
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
public class AdministratorRepository implements CrudRepository<Administrator, Integer> {
    @Override
    public Administrator save(Administrator administrator) {
        String sql = "INSERT INTO administrator(name, email, password) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, administrator.getName());
            pstm.setString(2, administrator.getEmail());
            pstm.setString(3, administrator.getPassword());
            pstm.execute();
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) administrator.setId(generatedKeys.getInt(1));
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return administrator;
    }

    @Override
    public Optional<Administrator> findById(Integer id) {
        String sql = "SELECT * FROM administrator WHERE id=?";
        Administrator administrator = null;
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, id);
            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                administrator = constructAdministratorFromQuery(result);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
        return Optional.ofNullable(administrator);
    }

    @Override
    public void update(Administrator administrator) {
        String sql = "UPDATE administrator SET name=?, email=?, password=? WHERE id=?";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, administrator.getName());
            pstm.setString(2, administrator.getEmail());
            pstm.setString(3, administrator.getPassword());
            pstm.setInt(4, administrator.getId());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM administrator WHERE id=?";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, id);
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

    }

    @Override
    public List<Administrator> findAll() {
        String sql = "SELECT * FROM administrator";
        List<Administrator> administrators = new ArrayList<>();
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet result = pstm.executeQuery();
            while (result.next()) {
                Administrator administrator = constructAdministratorFromQuery(result);
                administrators.add(administrator);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return administrators;
    }

    private Administrator constructAdministratorFromQuery(ResultSet result) throws SQLException {
        return new Administrator(
                result.getInt("id"),
                result.getString("name"),
                result.getString("email"),
                result.getString("password")
        );
    }
}
