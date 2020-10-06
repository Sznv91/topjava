package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    public boolean delete(int id);

    public Meal update(Meal meal);

    public Meal save(Meal meal);

    public Meal get(int id);

    public List<Meal> getAll();

}
