package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final int userId = SecurityUtil.authUserId();
    private final int caloriesPerDay = SecurityUtil.authUserCaloriesPerDay();

    @Autowired
    private MealService service;

    public Meal create(Meal meal) throws NotFoundException {
        log.info("create");
        return service.create(userId, meal);
    }

    public Meal update(Meal meal) throws NotFoundException {
        log.info("update");
        return service.update(userId, meal);
    }

    public void delete(int id) throws NotFoundException {
        log.info("delete");
        service.delete(userId, id);
    }

    public Meal get(int id) throws NotFoundException {
        log.info("get");
        return service.get(userId, id);
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(userId), caloriesPerDay);
    }

    public List<MealTo> getBetweenDates(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getBetweenDates");
        if ((startDate != null & endDate != null) & (startTime != null & endTime != null)) {
            List<Meal> list = service.getBetweenDates(userId, startDate, endDate);
            return MealsUtil.getFilteredTos(list, caloriesPerDay, startTime, endTime);
        } else if ((startDate != null & endDate != null) & (startTime == null & endTime == null)) {
            return MealsUtil.getTos(service.getBetweenDates(userId, startDate, endDate), caloriesPerDay);
        }
        return getAll();
    }
}