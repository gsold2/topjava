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

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        log.info("create");
        checkNew(meal);
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public Meal update(Meal meal, int id) {
        log.info("update");
        assureIdConsistent(meal, id);
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
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getBetweenDatesOrDefault(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getBetweenDates");
        int userId = SecurityUtil.authUserId();
        int caloriesPerDay = SecurityUtil.authUserCaloriesPerDay();
        List<Meal> list = service.getBetweenDates(userId, (startDate == null ? LocalDate.MIN : startDate), (endDate == null ? LocalDate.MAX : endDate));
        return MealsUtil.getFilteredTos(list, caloriesPerDay, (startTime == null ? LocalTime.MIN : startTime), (endTime == null ? LocalTime.MAX : endTime));
    }
}