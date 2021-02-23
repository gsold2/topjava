package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static int mealId = START_SEQ + 2;
    public static int mealIdCounter = mealId;
    public static final int NOT_EXIST_MEAL_ID = 7;

    public static final Meal meal_user_1_1 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.JANUARY, 19, 9, 0), "User_1_1", 510);
    public static final Meal meal_user_1_2 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.JANUARY, 19, 13, 0), "User_1_2", 1000);
    public static final Meal meal_user_1_3 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.JANUARY, 19, 20, 0), "User_1_3", 1500);
    public static final Meal meal_user_2_1 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.FEBRUARY, 19, 9, 0), "User_2_1", 500);
    public static final Meal meal_user_2_2 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.FEBRUARY, 19, 13, 0), "User_2_2", 500);
    public static final Meal meal_user_2_3 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.FEBRUARY, 19, 20, 0), "User_2_3", 500);

    public static final Meal meal_admin_1_1 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.JANUARY, 19, 9, 30), "ADMIN_1_1", 510);
    public static final Meal meal_admin_1_2 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.JANUARY, 19, 13, 30), "ADMIN_1_2", 1000);
    public static final Meal meal_admin_1_3 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.JANUARY, 19, 20, 30), "ADMIN_1_3", 1500);
    public static final Meal meal_admin_2_1 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.FEBRUARY, 19, 9, 30), "ADMIN_2_1", 500);
    public static final Meal meal_admin_2_2 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.FEBRUARY, 19, 13, 30), "ADMIN_2_2", 500);
    public static final Meal meal_admin_2_3 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.FEBRUARY, 19, 20, 30), "ADMIN_2_3", 500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JUNE, 19, 13, 0), "User_1_new", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal_user_1_1);
        updated.setDateTime(LocalDateTime.of(2025, Month.JUNE, 19, 13, 0));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        return updated;
    }

    public static List<Meal> mealList() {
        List<Meal> list = new ArrayList<>();
        list.add(meal_user_2_3);
        list.add(meal_user_2_2);
        list.add(meal_user_2_1);
        list.add(meal_user_1_3);
        list.add(meal_user_1_2);
        list.add(meal_user_1_1);
        return list;
    }

    public static List<Meal> mealBetweenInclusiveList() {
        List<Meal> list = new ArrayList<>();
        list.add(meal_user_1_3);
        list.add(meal_user_1_2);
        list.add(meal_user_1_1);
        return list;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
