package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapStorage implements Storage {
    private final ConcurrentMap<Integer, Meal> storage = new ConcurrentHashMap<>();
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(LocalDateTime dateTime, String description, int calories, int id) {
        storage.replace(id, new Meal(dateTime, description, calories, id));
    }

    @Override
    public void save(LocalDateTime dateTime, String description, int calories) {
        int id = COUNTER.getAndIncrement();
        storage.put(id, new Meal(dateTime, description, calories, id));
    }

    @Override
    public Meal get(int id) {
        if (storage.containsKey(id)) {
            return storage.get(id);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
