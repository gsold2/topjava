package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface Storage {

    void update(Meal meal);

    void create(LocalDateTime dateTime, String description, int calories);

    Meal get(int id);

    void delete(int id);

    List<Meal> getAll();
}
