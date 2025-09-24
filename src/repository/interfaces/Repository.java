package repository.interfaces;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    T save(T entity);
    Optional<T> findById(int id);
    List<T> findAll();
    void update(T entity);
    void delete(int id);
}
