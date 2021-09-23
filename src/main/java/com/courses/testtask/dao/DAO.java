package com.courses.testtask.dao;

public interface DAO <Entity, Key> {
    boolean create(Entity dto);
    Entity read(Key key);
    Entity update(Entity dto);
    boolean delete(Key key);

}
