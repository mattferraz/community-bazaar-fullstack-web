package com.tads.mhsf.bazaar.repository;

import com.tads.mhsf.bazaar.dao.ConnectionManager;
import com.tads.mhsf.bazaar.entity.BusinessHour;
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
public class BusinessHoursRepository implements CrudRepository<BusinessHour, Integer> {

    private final DoneeInstitutionRepository doneeInstitutionRepository;

    public BusinessHoursRepository(DoneeInstitutionRepository doneeInstitutionRepository) {
        this.doneeInstitutionRepository = doneeInstitutionRepository;
    }

    @Override
    public BusinessHour save(BusinessHour businessHour) {
        String sql = "INSERT INTO business_hours(id_donee_institution, weekday, open_time, close_time) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, businessHour.getDoneeInstitution().getId());
            pstm.setInt(2, businessHour.getWeekday());
            pstm.setInt(3, businessHour.getOpenTime());
            pstm.setInt(4, businessHour.getCloseTime());
            pstm.execute();
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) businessHour.setId(generatedKeys.getInt(1));
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return businessHour;
    }

    @Override
    public Optional<BusinessHour> findById(Integer id) {
        String sql = "SELECT * FROM business_hours WHERE id=?";
        BusinessHour businessHour = null;
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                businessHour = constructBusinessHourFromResult(result);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return Optional.ofNullable(businessHour);
    }

    @Override
    public void update(BusinessHour businessHour) {
        String sql = "UPDATE business_hours SET id_donee_institution=?, weekday=?, open_time=?, close_time=? WHERE id=?";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setInt(1, businessHour.getDoneeInstitution().getId());
            pstm.setInt(2, businessHour.getWeekday());
            pstm.setInt(3, businessHour.getOpenTime());
            pstm.setInt(4, businessHour.getCloseTime());
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM business_hours WHERE id=?";
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }
    }

    public void deleteAllBusinessHoursFromDoneeInstitutionId(Integer id) {
        String sql = "DELETE FROM business_hours WHERE id_donee_institution=?";
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
    public List<BusinessHour> findAll() {
        String sql = "SELECT * FROM business_hours";
        List<BusinessHour> businessHours = new ArrayList<>();
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            ResultSet result = pstm.executeQuery();
            while (result.next()) {
                BusinessHour businessHour = constructBusinessHourFromResult(result);
                businessHours.add(businessHour);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return businessHours;
    }

    public List<BusinessHour> findAllBusinessHoursFromDoneeInstitutionId(int doneeInstitutionID) {
        String sql = "SELECT * FROM business_hours WHERE id_donee_institution=?";
        List<BusinessHour> businessHours = new ArrayList<>();
        try {
            PreparedStatement pstm = ConnectionManager.getConnection().prepareStatement(sql);
            pstm.setInt(1, doneeInstitutionID);
            ResultSet result = pstm.executeQuery();
            while (result.next()) {
                BusinessHour businessHour = constructBusinessHourFromResult(result);
                businessHours.add(businessHour);
            }
            pstm.close();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage(), e.getCause());
        }

        return businessHours;
    }

    private BusinessHour constructBusinessHourFromResult(ResultSet result) throws SQLException {
        return new BusinessHour(
                result.getInt("id"),
                result.getInt("weekday"),
                result.getInt("open_time"),
                result.getInt("close_time"),
                doneeInstitutionRepository
                        .findById(result.getInt("id_donee_institution"))
                        .orElseThrow(SQLException::new)
        );
    }
}
