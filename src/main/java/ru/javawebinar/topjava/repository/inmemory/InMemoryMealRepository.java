package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        int userId = SecurityUtil.authUserId();
        int count = 1;
        Map<Integer, Meal> map = new HashMap<>();
        for (Meal meal : MealsUtil.meals) {
            meal.setId(count);
            map.put(count, meal);
            count++;
        }
        repository.put(userId, map);
    }


    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save {}", userId, meal);
        if (!repository.containsKey(userId)) {
            return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            return repository.get(userId).put(meal.getId(), meal);
        }
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete {}", userId, id);
        return repository.containsKey(userId) && (repository.get(userId).remove(id) != null);
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get {}", userId, id);
        return repository.containsKey(userId) ? repository.get(userId).get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("get {}", userId);
        return getSortedStream(userId).collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getBetweenDates(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getBetweenDates {}", userId);
        return getSortedStream(userId)
                .filter(m ->
                        (m.getDate().compareTo(startDate) >= 0)
                                & (m.getDate().compareTo(endDate) <= 0)
                )
                .collect(Collectors.toList());
    }

    private Stream<Meal> getSortedStream(int userId) {
        return repository.get(userId).values().stream()
                .sorted((m1, m2) -> {
                    if (!m1.getDateTime().equals(m2.getDateTime())) {
                        return m1.getDateTime().compareTo(m2.getDateTime()) * (-1);
                    } else {
                        return 0;
                    }
                });
    }
}

