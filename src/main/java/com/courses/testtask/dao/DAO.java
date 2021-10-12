package com.courses.testtask.dao;

public interface DAO <Entity, Key> {
    Entity create(Entity dto);
    Entity read(Key key);
    Entity update(Entity dto, String id);
    boolean delete(Key key);

}
