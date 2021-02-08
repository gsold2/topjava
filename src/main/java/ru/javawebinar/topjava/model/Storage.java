package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.List;

public interface Storage {

    void clear();

    void update(LocalDateTime dateTime, String description, int calories, int id);

    void save(LocalDateTime dateTime, String description, int calories);

    Meal get(int id);

    void delete(int id);

    List<Meal> getAllMeals();

    int size();
}
