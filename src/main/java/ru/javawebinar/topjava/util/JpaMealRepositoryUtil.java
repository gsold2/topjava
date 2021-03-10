package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import javax.persistence.EntityManager;
import java.util.function.BiFunction;

public class JpaMealRepositoryUtil {

    public static Meal getMeal(Meal meal, int userId, EntityManager em, BiFunction<Integer, Integer, Meal> function) {
        meal.setUser(em.getReference(User.class, userId));
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else if (function.apply(meal.id(), userId) == null) {
            return null;
        }
        return em.merge(meal);
    }
}
