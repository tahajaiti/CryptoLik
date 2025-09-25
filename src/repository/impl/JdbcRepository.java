<<<<<<< Updated upstream
=======
package repository.impl;

import db.DBConnection;
import exceptions.FailedToSaveException;
import exceptions.NotFoundException;
import mapper.db.DBMapper;
import repository.interfaces.Repository;
import util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public abstract class JdbcRepository<T> implements Repository<T> {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    protected final DBConnection dbConnection;

    protected JdbcRepository(DBConnection dbConnection) {
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
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            getMapper().toInsertStmt(stmt, entity);
            int affected = stmt.executeUpdate();
            
            if (affected == 0) {
                throw new SQLException("Creating entity failed, no rows affected.");
            }

            try (ResultSet genKeys = stmt.getGeneratedKeys()) {
                if (genKeys.next()) {
                    entity = getMapper().setId(entity, genKeys.getInt(1));
                } else {
                    throw new SQLException("Creating entity failed, no ID obtained.");
                }
            }

            return entity;
        } catch (SQLException e) {
            Log.error(getClass(), "Error saving entity in " + getTableName(), e);
            throw new FailedToSaveException("Failed to save entity" + getTableName(), e);
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
            Log.error(getClass(), "Error finding entity by ID in " + getTableName());
            throw new NotFoundException("Entity with ID " + id + " not found in " + getTableName());
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
            Log.error(getClass(), "Error finding all entities in " + getTableName(), e);
            throw new NotFoundException("No entities found in " + getTableName());
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
            Log.error(getClass(), "Error updating entity by ID in " + getTableName());
            throw new FailedToSaveException("Failed to update entity in " + getTableName(), e);
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
            Log.error(getClass(), "Error deleting entity in " + getTableName(), e);
            throw new FailedToSaveException("Failed to delete entity in " + getTableName(), e);
        }
    }
}
>>>>>>> Stashed changes
