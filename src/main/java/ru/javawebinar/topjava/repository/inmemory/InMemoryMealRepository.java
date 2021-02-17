package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
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

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {}", userId);
        if (!repository.containsKey(userId)) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> map = new ConcurrentHashMap<Integer, Meal>() {{
                put(meal.getId(), meal);
            }};
            return repository.computeIfAbsent(userId, k -> map).get(meal.getId());
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            return repository.get(userId).compute(meal.getId(), (id, newMeal) -> meal);
        }
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete {}", userId);
        return repository.containsKey(userId) && (repository.get(userId).remove(id) != null);
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get {}", userId);
        return repository.get(userId) != null ? repository.get(userId).get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("get {}", userId);
        return filterByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenDates(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getBetweenDates {}", userId);
        return filterByPredicate(userId, meal -> (meal.getDate().compareTo(startDate) >= 0) & (meal.getDate().compareTo(endDate) <= 0));
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        List<Meal> list = null;
        if (repository.containsKey(userId)) {
            list = repository.get(userId).values().stream()
                    .filter(filter)
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
        return list == null ? Collections.emptyList() : list;
    }
}

