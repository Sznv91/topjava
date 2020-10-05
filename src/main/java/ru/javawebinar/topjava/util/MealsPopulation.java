package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class MealsPopulation {

    public List<Meal> getMealList() {
        Meal meal1 = new Meal(LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак", 500);
        Meal meal2 = new Meal(LocalDateTime.of(2020, 1, 30, 13, 0), "Обед", 1000);
        Meal meal3 = new Meal(LocalDateTime.of(2020, 1, 30, 20, 0), "Ужин", 500);

        Meal meal4 = new Meal(LocalDateTime.of(2020, 1, 31, 0, 0), "Еда на граничное значение", 100);
        Meal meal5 = new Meal(LocalDateTime.of(2020, 1, 31, 10, 0), "Завтрак", 1000);
        Meal meal6 = new Meal(LocalDateTime.of(2020, 1, 31, 13, 0), "Обед", 500);
        Meal meal7 = new Meal(LocalDateTime.of(2020, 1, 31, 20, 0), "Ужин", 410);

        return Arrays.asList(meal1, meal2, meal3, meal4, meal5, meal6, meal7);
    }
}
