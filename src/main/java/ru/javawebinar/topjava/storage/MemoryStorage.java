package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.ExistInStorageException;
import ru.javawebinar.topjava.util.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryStorage implements Storage {

    Map<Integer, Meal> storage = new HashMap<>();
    int count = 0;

    @Override
    public boolean delete(int id) {
        if (storage.containsKey(id)) {
            storage.remove(id);
            return true;
        } else {
            try {
                throw new NotFoundException("Can't delete. Meal id: " + id + " not found in storage");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Meal update(Meal meal) {
        if (storage.containsKey(meal.getId())){
            storage.remove(meal.getId());
            storage.put(meal.getId(),meal);
            return meal;
        } else {
            try {
                throw new NotFoundException("Can't update, meal id: " + meal.getId() + " not found in storage");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Meal save(Meal meal) {
        Meal toSave = meal;
        if (meal.getId() != 0) {
            if (!storage.containsKey(meal.getId())) {
                storage.put(meal.getId(), meal);
            } else {
                try {
                    throw new ExistInStorageException("Meal id: " + meal.getId() + " already exist in storage");
                } catch (ExistInStorageException e) {
                    e.printStackTrace();
                }
            }
        } else {
            count++;
            toSave = new Meal(count, meal.getDateTime(), meal.getDescription(), meal.getCalories());
            storage.put(toSave.getId(), toSave);
        }
        return toSave;
    }

    @Override
    public Meal get(int id) {
        Meal result = storage.get(id);
        if (result != null) {
            return result;
        } else {
            try {
                throw new NotFoundException("Meal id " + id + " not found in storage");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }
}
