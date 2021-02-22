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
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
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
        assertThat(service.get(MEAL_ID, USER_ID)).usingRecursiveComparison().isEqualTo(MealTestData.getUpdated());
    }

    @Test
    public void updateNotFoundUserId() {
        assertThrows(NotFoundException.class, () -> service.update(MealTestData.getUpdated(), UserTestData.ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        assertThat(created).usingRecursiveComparison().isEqualTo(newMeal);
        assertThat(service.get(newId, USER_ID)).usingRecursiveComparison().isEqualTo(newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DuplicateKeyException.class, () -> service.create(new Meal(LocalDateTime.of(2015, Month.JANUARY, 19, 13, 23, 54), "DuplicateDateTime", 1000), USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, USER_ID));
    }

    @Test
    public void deletedNotFoundMealId() {
        assertThrows(NotFoundException.class, () -> service.delete(SOMEONE_ELSE_MEAL_ID, USER_ID));
    }

    @Test
    public void get() {
        assertThat(service.get(MEAL_ID, USER_ID)).usingRecursiveComparison().isEqualTo(meal);
    }

    @Test
    public void getNotFoundMealId() {
        assertThrows(NotFoundException.class, () -> service.get(SOMEONE_ELSE_MEAL_ID, USER_ID));
    }

    @Test
    public void getAll() {
        assertThat(service.getAll(USER_ID)).usingRecursiveComparison().isEqualTo(MealTestData.mealList());
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate startDate = LocalDate.of(2015, Month.JANUARY, 19);
        LocalDate endDate = LocalDate.of(2015, Month.JANUARY, 19);
        assertThat(service.getBetweenInclusive(startDate, endDate, USER_ID)).usingRecursiveComparison().isEqualTo(MealTestData.mealBetweenInclusiveList());
    }
}