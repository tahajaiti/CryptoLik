package repository.impl;

import db.DBConnection;
import mapper.DB.DBMapper;
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
    protected abstract DBMapper<T> getMapper();
    protected abstract String getUpdateQuery();
    protected abstract String getInsertQuery();

    @Override
    public T save(T entity) {
        String sql = getInsertQuery();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            getMapper().toInsertStmt(stmt, entity);
            stmt.executeUpdate();

            return entity;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving entity in " + getTableName(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> findById(int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(getMapper().fromResult(rs));
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
                list.add(getMapper().fromResult(rs));
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

            getMapper().toUpdateStmt(stmt, entity);
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
