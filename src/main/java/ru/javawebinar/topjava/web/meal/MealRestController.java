package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        log.info("create");
        if (!meal.isNew()) {
            throw new IllegalArgumentException(meal + " must be new (id=null)");
        }
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public Meal update(Meal meal) {
        log.info("update");
        if (meal.isNew()) {
            throw new IllegalArgumentException(meal + " must be exist (id!=null)");
        }
        return service.update(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete");
        service.delete(SecurityUtil.authUserId(), id);
    }

    public Meal get(int id) {
        log.info("get");
        return service.get(SecurityUtil.authUserId(), id);
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserId());
    }

    public List<MealTo> getBetweenDates(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getBetweenDates");
        int userId = SecurityUtil.authUserId();
        int caloriesPerDay = SecurityUtil.authUserCaloriesPerDay();
        List<Meal> list = service.getBetweenDates(userId, changeLocalDateIfNull(startDate, "min"), changeLocalDateIfNull(endDate, "max"));
        return MealsUtil.getFilteredTos(list, caloriesPerDay, changeLocalTimeIfNull(startTime, "min"), changeLocalTimeIfNull(endTime, "max"));
    }

    private LocalDate changeLocalDateIfNull(LocalDate date, String extremeValue) {
        switch (extremeValue) {
            case "max":
                return date == null ? LocalDate.MAX : date;
            case "min":
                return date == null ? LocalDate.MIN : date;
        }
        return date;
    }

    private LocalTime changeLocalTimeIfNull(LocalTime time, String extremeValue) {
        switch (extremeValue) {
            case "max":
                return time == null ? LocalTime.MAX : time;
            case "min":
                return time == null ? LocalTime.MIN : time;
        }
        return time;
    }
}