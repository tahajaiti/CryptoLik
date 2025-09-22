package repository.impl;

import db.DBConnection;
import repository.interfaces.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class JdbcRepository<T>  implements Repository<T> {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    protected final DBConnection dbConnection;

    public JdbcRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    protected abstract String getTableName();
    protected abstract T mapToEntity(ResultSet rs) throws SQLException;
    protected abstract void setInsertParams(PreparedStatement stmt, T entity) throws SQLException;
    protected abstract void setUpdateParams(PreparedStatement stmt, T entity) throws SQLException;
    protected abstract String getUpdateQuery();
    protected abstract String getInsertQuery();

    @Override
    public void save(T entity) {
        String sql = getInsertQuery();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setInsertParams(stmt, entity);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving entity in " + getTableName(), e);
        }
    }

    @Override
    public Optional<T> findById(int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToEntity(rs));
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error finding entity by ID in " + getTableName(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapToEntity(rs));
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching all entities in " + getTableName(), e);
        }
        return list;
    }

    @Override
    public void update(T entity) {
        String sql = getUpdateQuery();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setUpdateParams(stmt, entity);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating entity in " + getTableName(), e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting entity in " + getTableName(), e);
        }
    }
}
