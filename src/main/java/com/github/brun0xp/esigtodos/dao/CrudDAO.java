package com.github.brun0xp.esigtodos.dao;

import java.util.List;

public interface CrudDAO<T> {

    void create(T entity);

    List<T> read();

    void update(T entity);

    void delete(T entity);
}
