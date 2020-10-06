package ru.javawebinar.topjava.storage.TestMemoryStorage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MemoryStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsPopulation;

import java.time.LocalDateTime;

public class Test {

    public static void main(String[] args) {
        Storage storage = new MemoryStorage();
        MealsPopulation population = new MealsPopulation();
        storage = population.FillMemoryStorage(storage);

        Meal meal3 = storage.get(3);
        Meal meal3_changed = new Meal(meal3.getId(), LocalDateTime.now(),"Измененная еда", 148);

        System.out.println(storage.update(meal3_changed).getDescription());
    }
}
