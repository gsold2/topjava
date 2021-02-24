package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;
    public static int mealIdCounter = MEAL_ID;
    public static final int NOT_EXIST_MEAL_ID = 7;

    public static final Meal mealUser11 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.JANUARY, 19, 9, 0), "User_1_1", 510);
    public static final Meal mealUser12 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.JANUARY, 19, 13, 0), "User_1_2", 1000);
    public static final Meal mealUser13 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.JANUARY, 19, 20, 0), "User_1_3", 1500);
    public static final Meal mealUser21 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.FEBRUARY, 19, 9, 0), "User_2_1", 500);
    public static final Meal mealUser22 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.FEBRUARY, 19, 13, 0), "User_2_2", 500);
    public static final Meal mealUser23 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.FEBRUARY, 19, 20, 0), "User_2_3", 500);

    public static final Meal mealAdmin11 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.JANUARY, 19, 9, 30), "ADMIN_1_1", 510);
    public static final Meal mealAdmin12 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.JANUARY, 19, 13, 30), "ADMIN_1_2", 1000);
    public static final Meal mealAdmin13 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.JANUARY, 19, 20, 30), "ADMIN_1_3", 1500);
    public static final Meal mealAdmin21 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.FEBRUARY, 19, 9, 30), "ADMIN_2_1", 500);
    public static final Meal mealAdmin22 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.FEBRUARY, 19, 13, 30), "ADMIN_2_2", 500);
    public static final Meal mealAdmin23 = new Meal(mealIdCounter++, LocalDateTime.of(2015, Month.FEBRUARY, 19, 20, 30), "ADMIN_2_3", 500);

    public static final List<Meal> mealList = Arrays.asList(mealUser23, mealUser22, mealUser21, mealUser13, mealUser12, mealUser11);

    public static final List<Meal> mealBetweenInclusiveList = Arrays.asList(mealUser13, mealUser12, mealUser11);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JUNE, 19, 13, 0), "User_1_new", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(mealUser11);
        updated.setDateTime(LocalDateTime.of(2025, Month.JUNE, 19, 13, 0));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
