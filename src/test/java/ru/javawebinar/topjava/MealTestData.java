package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MealTestData {
    public static final int MEAL_ID = 2;
    public static final int SOMEONE_ELSE_MEAL_ID = 7;

    public static final Meal meal = new Meal(MEAL_ID, LocalDateTime.of(2015, Month.JANUARY, 19, 13, 23, 54), "User_1 обед", 1000);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JUNE, 19, 13, 23, 54), "User_1 обед_new", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal);
        updated.setDateTime(LocalDateTime.of(2025, Month.JUNE, 19, 13, 23, 54));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        return updated;
    }

    public static List<Meal> mealList() {
        List<Meal> list = new ArrayList<>();
        list.add(new Meal(1, LocalDateTime.of(2015, Month.JANUARY, 19, 9, 23, 54), "User_1 завтрак", 510));
        list.add(new Meal(2, LocalDateTime.of(2015, Month.JANUARY, 19, 13, 23, 54), "User_1 обед", 1000));
        list.add(new Meal(3, LocalDateTime.of(2015, Month.JANUARY, 19, 10, 23, 54), "User_1 ужин", 1500));
        list.add(new Meal(4, LocalDateTime.of(2015, Month.FEBRUARY, 19, 9, 23, 54), "User_2 завтрак", 500));
        list.add(new Meal(5, LocalDateTime.of(2015, Month.FEBRUARY, 19, 13, 23, 54), "User_2 обед", 500));
        list.add(new Meal(6, LocalDateTime.of(2015, Month.FEBRUARY, 19, 10, 23, 54), "User_2 ужин", 500));
        list.sort(Comparator.comparing(Meal::getDateTime).reversed());
        return list;
    }

    public static List<Meal> mealBetweenInclusiveList() {
        List<Meal> list = new ArrayList<>();
        list.add(new Meal(1, LocalDateTime.of(2015, Month.JANUARY, 19, 9, 23, 54), "User_1 завтрак", 510));
        list.add(new Meal(2, LocalDateTime.of(2015, Month.JANUARY, 19, 13, 23, 54), "User_1 обед", 1000));
        list.add(new Meal(3, LocalDateTime.of(2015, Month.JANUARY, 19, 10, 23, 54), "User_1 ужин", 1500));
        list.sort(Comparator.comparing(Meal::getDateTime).reversed());
        return list;
    }
}
