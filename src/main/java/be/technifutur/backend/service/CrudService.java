package be.technifutur.backend.service;

import java.util.List;

public interface CrudService<T, ID> {
    // Create
    ID add(T entity);
    // Read
    List<T> getAll();
    T getOne(ID id);
    // Update
    void update(ID id, T entity);
    // Delete
    void delete(ID id);
}
