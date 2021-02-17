package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName_0", "email_test_0@mail.ru", "password_0", Role.ADMIN));

            InMemoryUserRepository inMemoryUserRepository = appCtx.getBean(InMemoryUserRepository.class);
            inMemoryUserRepository.save(new User(null, "userName_1", "email_test_1@mail.ru", "password_1", Role.USER));
            inMemoryUserRepository.save(new User(null, "userName_2", "email_test_2@mail.ru", "password_2", Role.USER));
            System.out.println(inMemoryUserRepository.getByEmail("email_test_1@mail.ru"));
            System.out.println("println inMemoryUserRepository.getAll()");
            System.out.println(inMemoryUserRepository.getAll());

            InMemoryMealRepository inMemoryMealRepository = appCtx.getBean(InMemoryMealRepository.class);
            inMemoryMealRepository.save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "1_1_Завтрак", 500));
            inMemoryMealRepository.save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "1_2_Обед", 1000));
            inMemoryMealRepository.save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "1_3_Ужин", 500));
            inMemoryMealRepository.save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "1_4_Еда на граничное значение", 100));
            inMemoryMealRepository.save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "1_5_Завтрак", 1000));
            inMemoryMealRepository.save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "1_6_Обед", 500));
            inMemoryMealRepository.save(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "1_7_Ужин", 410));
            inMemoryMealRepository.save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "2_1_Завтрак", 500));
            inMemoryMealRepository.save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "2_2_Обед", 1000));
            inMemoryMealRepository.save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "2_3_Ужин", 500));
            inMemoryMealRepository.save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "2_4_Еда на граничное значение", 100));
            inMemoryMealRepository.save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "2_5_Завтрак", 1000));
            inMemoryMealRepository.save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "2_6_Обед", 500));
            inMemoryMealRepository.save(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "2_7_Ужин", 410));
            System.out.println("println inMemoryMealRepository.get(userId, id)");
            System.out.println(inMemoryMealRepository.get(1, 1));
            System.out.println(inMemoryMealRepository.get(2, 8));
            System.out.println(inMemoryMealRepository.getAll(2));
            System.out.println(inMemoryMealRepository.getAll(3));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            System.out.println(mealRestController.get(1));
            mealRestController.update(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "1_1_Завтрак_update", 500));
            System.out.println(mealRestController.get(1));
            mealRestController.getBetweenDates(LocalDate.of(2020, Month.JANUARY, 30), LocalDate.of(2020, Month.JANUARY, 30), LocalTime.of(9, 0), LocalTime.of(14, 0)).forEach(System.out::println);
            mealRestController.getAll().forEach(System.out::println);
        }
    }
}
