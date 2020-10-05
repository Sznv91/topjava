package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsPopulation;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "update":
                break;
            case "delete":
                break;
            case "new":
                break;
            default:
                MealsPopulation population = new MealsPopulation();
                List<MealTo> mealToList = MealsUtil.filteredByStreams(population.getMealList(), LocalTime.MIN, LocalTime.MAX, 2000);
                request.setAttribute("mealToList", mealToList);
                request.setAttribute("action", "view");
                request.getRequestDispatcher("meals.jsp").forward(request, response);
                break;
        }
    }
}
