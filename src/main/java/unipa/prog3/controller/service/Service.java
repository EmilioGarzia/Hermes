package unipa.prog3.controller.service;

import unipa.prog3.model.entity.Entity;

import java.util.Vector;
import java.util.function.Predicate;

public interface Service<T extends Entity> {
    String generateID();
    void insert(T t);
    Vector<T> select(Predicate<T> condition);
    void update(T t);

    T entityFromString(String s);
    String entityToString(T t);
}
