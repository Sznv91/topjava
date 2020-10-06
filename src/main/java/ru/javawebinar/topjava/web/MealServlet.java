package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MemoryStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsPopulation;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {

    Storage storage;
    DateTimeFormatter formatter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        storage = new MemoryStorage();
        MealsPopulation population = new MealsPopulation();
        storage = population.FillMemoryStorage(storage);

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        super.init(config);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html");
        String description = request.getParameter("description");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        int calories = Integer.parseInt(request.getParameter("calories"));

        switch (request.getParameter("action")) {
            case "update":
                int id = Integer.parseInt(request.getParameter("id"));
                Meal meal = new Meal(id, dateTime, description, calories);
                storage.update(meal);
                break;
            case "new":
                meal = new Meal(dateTime, description, calories);
                storage.save(meal);
                break;
        }

        response.sendRedirect("meals");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        request.setAttribute("formatter", formatter);
        switch (action) {
            case "update":
                request.setAttribute("action", "update");
                request.setAttribute("meal", storage.get(Integer.parseInt(request.getParameter("id"))));
                request.getRequestDispatcher("newEdit.jsp").forward(request, response);
                break;
            case "delete":
                storage.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("meals");
                break;
            case "new":
                request.setAttribute("action", "new");
                Meal meal = new Meal(LocalDateTime.now(), "Описание", 1);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("newEdit.jsp").forward(request, response);
                break;
            default:
                List<MealTo> mealToList = MealsUtil.filteredByStreams(storage.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
                request.setAttribute("mealToList", mealToList);
                request.setAttribute("action", "view");
                request.getRequestDispatcher("meals.jsp").forward(request, response);
                break;
        }
    }
}
