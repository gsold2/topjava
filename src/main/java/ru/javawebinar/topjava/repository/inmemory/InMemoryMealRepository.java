package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "1_1_Завтрак", 500));
        save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "1_2_Обед", 1000));
        save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "1_3_Ужин", 500));
        save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "1_4_Еда на граничное значение", 100));
        save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "1_5_Завтрак", 1000));
        save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "1_6_Обед", 500));
        save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "1_7_Ужин", 410));
        save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "2_1_Завтрак", 500));
        save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "2_2_Обед", 1000));
        save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "2_3_Ужин", 500));
        save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "2_4_Еда на граничное значение", 100));
        save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "2_5_Завтрак", 1000));
        save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "2_6_Обед", 500));
        save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "2_7_Ужин", 410));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {} userId=" + userId, meal);
        if (repository.get(userId) == null) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> map = new ConcurrentHashMap<>();
            repository.put(userId, map);
            return map.computeIfAbsent(meal.getId(), newMeal -> meal);
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        return repository.get(userId).compute(meal.getId(), (id, Meal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete {} userId=" + userId, id);
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null && (mealMap.remove(id) != null);
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get {} userId=" + userId, id);
        Map<Integer, Meal> mealMap = repository.get(userId);
        return mealMap != null ? mealMap.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("List userId={}", userId);
        return filterByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenDates(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getBetweenDates {} userId=", userId);
        return filterByPredicate(userId, meal -> (meal.getDate().compareTo(startDate) >= 0) && (meal.getDate().compareTo(endDate) <= 0));
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> mealMap = repository.get(userId);
        if (mealMap != null) {
            return mealMap.values().stream()
                    .filter(filter)
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

