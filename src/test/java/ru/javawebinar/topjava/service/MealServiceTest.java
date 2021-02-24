package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app-jdbs.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-app-common.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/initDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), MealTestData.getUpdated());
    }

    @Test
    public void updateAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.update(MealTestData.getUpdated(), ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DuplicateKeyException.class, () -> service.create(new Meal(mealUser12.getDateTime(), "DuplicateDateTime", 1000), USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID));
    }

    @Test
    public void deletedNotExistMealId() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_EXIST_MEAL_ID, USER_ID));
    }

    @Test
    public void deleteAlienMealId() {
        service.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, ADMIN_ID));
    }

    @Test
    public void get() {
        assertMatch(service.get(MEAL_ID, USER_ID), mealUser11);
    }

    @Test
    public void getNotExistMealId() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXIST_MEAL_ID, USER_ID));
    }

    @Test
    public void getAlienMealId() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), mealList);
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate startDate = LocalDate.of(2015, Month.JANUARY, 19);
        LocalDate endDate = LocalDate.of(2015, Month.JANUARY, 19);
        assertMatch(service.getBetweenInclusive(startDate, endDate, USER_ID), mealBetweenInclusiveList);
    }

    @Test
    public void getBetweenNull() {
        assertThat(service.getBetweenInclusive(null, null, USER_ID)).usingRecursiveComparison().isEqualTo(mealList);
    }
}