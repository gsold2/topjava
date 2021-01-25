package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                        "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                        "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                        "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                        "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                        "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                        "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                        "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0),
                LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0),
                LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime,
                                                            LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> calorieCounterPerDay = new HashMap<>();
        List<UserMeal> mealTimeFromTheRange = new ArrayList<>();
        LocalDate date;
        LocalTime mealTime;
        for (UserMeal userMeal : meals) {
            date = userMeal.getDateTime().toLocalDate();
            calorieCounterPerDay.put(date,
                    calorieCounterPerDay.getOrDefault(date, 0) + userMeal.getCalories());
            mealTime = userMeal.getDateTime().toLocalTime();
            if (TimeUtil.isBetweenHalfOpen(mealTime, startTime, endTime)) {
                mealTimeFromTheRange.add(userMeal);
            }
        }

        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        boolean caloriesExcess;
        for (UserMeal userMeal : mealTimeFromTheRange) {
            caloriesExcess = calorieCounterPerDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay;
            userMealWithExcessList.add(new UserMealWithExcess(userMeal.getDateTime(),
                    userMeal.getDescription(), userMeal.getCalories(), caloriesExcess));
        }

        return Collections.unmodifiableList(userMealWithExcessList);
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime,
                                                             LocalTime endTime, int caloriesPerDay) {
        List<UserMeal> mealTimeFromTheRange = new ArrayList<>();
        Map<LocalDate, Integer> calorieCounterPerDay = meals.stream()
                .peek(userMeal -> {
                    LocalTime mealTime = userMeal.getDateTime().toLocalTime();
                    if (TimeUtil.isBetweenHalfOpen(mealTime, startTime, endTime)) {
                        mealTimeFromTheRange.add(userMeal);
                    }
                })
                .collect(Collectors.groupingBy(
                        userMeal -> userMeal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        return mealTimeFromTheRange.stream()
                .map(userMeal ->
                        new UserMealWithExcess(userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                calorieCounterPerDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        Collections::unmodifiableList));
    }
}
