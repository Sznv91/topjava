package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) { // null if not found
        Meal result = repository.get(id);
        return result.getUserId() == userId ? result : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
//        return repository.values().stream().sorted((meal1, meal2) -> meal2.getDate().compareTo(meal1.getDate())).sorted(Collections.reverseOrder()).collect(Collectors.toList());
        List<Meal> result = repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId).sorted(Comparator.comparing(Meal::getDate)).collect(Collectors.toList());
        Collections.reverse(result);
        return result;
    }
}

