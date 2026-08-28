package model.dao;

import model.entities.usuario;

import java.util.List;

public interface usuarioDao {
    void insert(usuario personas);
    int updateById(usuario personas);
    int deleteById(Integer id);

    usuario findById(Integer id);

    List<usuario> findAll();
}
