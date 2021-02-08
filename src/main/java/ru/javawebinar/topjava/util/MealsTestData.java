package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.MapStorage;
import ru.javawebinar.topjava.model.Storage;

import java.time.LocalDateTime;
import java.time.Month;

public class MealsTestData {
    public static final Storage mapStorage = new MapStorage();

    static {
        mapStorage.save(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        mapStorage.save(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        mapStorage.save(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        mapStorage.save(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        mapStorage.save(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        mapStorage.save(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        mapStorage.save(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    }
}
