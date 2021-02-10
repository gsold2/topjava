package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealStorage;
import ru.javawebinar.topjava.dao.Storage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int caloriesPerDay = 2000;
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MealStorage();
        storage.create(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        storage.create(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        storage.create(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        storage.create(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        storage.create(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        storage.create(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        storage.create(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("request to meals");
        String action = request.getParameter("action");

        if (action == null) {
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
            request.setAttribute("mealsTo", mealsTo);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        switch (action) {
            case "delete":
                storage.delete(id);
                response.sendRedirect("meals");
                break;
            case "update":
                Meal meal = storage.get(id);
                request.setAttribute("id", id);
                request.setAttribute("dateTime", meal.getDateTime());
                request.setAttribute("description", meal.getDescription());
                request.setAttribute("calories", meal.getCalories());
                request.getRequestDispatcher("/updatemeal.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("response from meals");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        switch (action) {
            case "create":
                storage.create(dateTime, description, calories);
                break;
            case "update":
                int id = Integer.parseInt(request.getParameter("id"));
                storage.update(new Meal(id, dateTime, description, calories));
                break;
        }
        response.sendRedirect("meals");
    }
}
