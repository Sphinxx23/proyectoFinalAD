package app.dao;

import java.util.List;

public interface DAO<T> {
    boolean guardar(T entidad);
    T buscarPorId(int id);
    List<T> listarTodos();
    boolean eliminar(int id);
}

